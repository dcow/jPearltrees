/*
 * Copyright 2012 David Cowden

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.dcow.pearltrees;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;

import me.dcow.pearltrees.TextPearlHandler.RecursiveTextPearlHandler;
import me.dcow.pearltrees.vocabulary.PT;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.util.FileManager;

/**
 * Pearltrees is an adapter class for the Jena Model in conjunction
 * with Pearltrees data.  Pearltrees allows you to interface with the 
 * data you export from your Pearltree in a familiar Pearltrees fashion.
 * In other words, given Pearltrees RDF/XML, this Pearltrees
 * implementation will construct a PearlTree that represents the data in
 * its natural tree structure.
 * 
 * Given a PearlTree , all its Pearls can be visited by retrieving an iterator 
 * over the associated Pearls.  To traverse the whole Tree rooted at the
 * PearlTree, any time a RefPearl is encountered, the associated PearlTree can be
 * retrieved and the structure recursively followed.
 * 
 * Traversal can be done by hand as mentioned, however, Pearltrees provides 
 * an automated traversal method and an interface for implementing the callbacks  
 * run on each visit to a Pearl.  Pearltrees also provides some other convenience 
 * methods for traversing and working with data from a Pearltrees export.
 * 
 * @author David
 *
 */
public class Pearltrees {
	
	protected Pearltrees() {}
	
	/**
	 * Construct a pearlTree from the specified RDF/XML InputStream source.
	 * @param pearlTreeXML InputSteam RDF/XML source.
	 */
	public static PearlTree buildPearlTrees(InputStream pearlTreeXML) {
		// Parse RDF..
		  // Create an empty model.
		Model m = ModelFactory.createDefaultModel();
		  // Read in the RDF data from the specified XML file.
		m.read(pearlTreeXML, null);	
		  // Obtain the node describing the root of the Pearltree.
		Property rootProp = m.createProperty(PT.PearlTreeNS, "rootTree");
		// Build a new PearlTree rooted at the root of the users Pearltree.
		return new PearlTree(m.listObjectsOfProperty(rootProp).nextNode());
	}
	
	/**
	 * Construct a pearlTree from the RDF/XML located at filenameOrURI.
	 * @param filenameOrURI location of the RDF/XML data.
	 */
	public static PearlTree buildPearlTrees(String filenameOrURI) {
		// Open the XML RDF file..
		return buildPearlTrees(FileManager.get().open(filenameOrURI));
	}
	
	/**
	 * Traverse the PearlTree ph using the callbacks defined by a PearlHandler
	 * implementation.  This traversal is an in order traversal. That is, each
	 * rootPearl is visited then all the Page,Alias, and Ref Pearls.  If notes
	 * are attached to a Pearl, they will be visited in a random order at the 
	 * time that the Pearl they describe is visited.
	 * @param pt PearlTree to traverse
	 * @param ph PearlHandler defining callbacks for each node/pearl.
	 */
	public static void traversePearlTree(PearlTree pt, PearlHandler ph) {
		for (Pearl p : pt.getTreePearls()) {
			p.accept(ph);
		}
	}
	
	/**
	 * Traverse the PearlTree ph as in traversePearlTree and also follow 
	 * any RefPearls recursively -- calling traversePearlTree on them.
	 * @param pt root PearlTree to begin climbing traversal from.
	 * @param ph PearlHandler defining callbacks for each node/pearl.
	 */
	public static void climbPearlTree(PearlTree pt, PearlHandler ph) {
		for (Pearl pearl : pt.getTreePearls()) {
			pearl.accept(ph);
			if (pearl instanceof RefPearl)
				climbPearlTree( ((RefPearl) pearl).getPearlTree(), ph);
		}
	}
	

	/**
	 * The DefaultHandler doesn't do anything for any of the Pearls.
	 * You it's provided so you can subclass it and only override
	 * methods you want to use.
	 * @return
	 */
	public static PearlHandler getDefaultPearlHandler() {
		return new DefaultPearlHandler();
	}
	
	/**
	 * DefaultHandler doesn't do anything.  
	 * @author David
	 *
	 */
	public static class DefaultPearlHandler implements PearlHandler {
		
		public void onPearl(RootPearl rootPearl) {}
		public void onPearl(PagePearl pagePearl) {}
		public void onPearl(RefPearl refPearl) {}
		public void onPearl(AliasPearl aliasPearl) {}
	}

	
	public static TextPearlHandler getTextPearlHandler(OutputStream out, boolean recursive) {
		PrintStream outps = new PrintStream(out);
		return recursive ? new RecursiveTextPearlHandler(outps) : new TextPearlHandler(outps);
	}
	
	public static PearlHandler getCSVTextPearlHandler(OutputStream out, boolean recursive) {
		PrintStream outPS = new PrintStream(out);
		return recursive ? 
			new RecursiveTextPearlHandler(outPS, new String(), ",", new String(), 
			new String(), new String(), new String()) 
		:
			new TextPearlHandler(outPS, new String(), ",", new String(), 
			new String(), new String(), new String());
	}
	
	
	// ############### RDF/XML ################
	
	/*
	 * Write the Jena model representation of the Pearltrees data to the given 
	 * OutputStream.
	 * @param out
	 */
	public static void writeRAW(OutputStream out, Pearl p) {
		p.toRDF().getModel().write(out);
	}
	
	/*
	 * Write the Jena model representation of the RDF data to standard out.
	 */
	public static void writeRAW(Pearl p) {
		writeRAW(System.out, p);
	}
	
	/*
	 * Print the namespaces used in the RDF/XML description of the pearlTree to
	 * OutputStream out.
	 * @param out Stream to write to.
	 */
	protected static void listNameSpaces(OutputStream out, PearlTree pt) {
		Pearl p = pt.getRootPearl();
		// Check to make sure the PearlTree has a root..
		if (p == null)
			return;
		Model m = p.toRDF().getModel();
		// Get the map so we can print the values AND the keys.
		Map<String, String> preMap = m.getNsPrefixMap();
		Iterator<String> it = preMap.keySet().iterator();
		String key;
		PrintStream outps = new PrintStream(out);
		while (it.hasNext()) {
			key = it.next();
			outps.println(String.format("%10s : %s", key, preMap.get(key)));
		}
	}
	
	
}// Pearltrees
