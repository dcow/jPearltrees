package me.dcow.pearltrees;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.DC_11;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

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
		}
		
	}
	
	/**
	 * Create folders where rootPearls exist and files where pagePearls or
	 * aliasPearls exist.
	 * @param path Absolute or Relative path.
	 */
	public void writeToFileSystem(String path, PearlTree pt, boolean recursive) {
		for (Pearl pearl : pt.getTreePearls()) {
			pearl.accept(getFileSystemPearlHandler(path, recursive));
		}
	}
	
	/**
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
	
	
	/**
	 * Write out the entire Pearltree rooted at rootNode in its directory like structure.
	 * The Pearltree is written with the name of each pearl on a new line.  The name is preceeded
	 * by its depth number of instances of fieldDelimiter.  Properties of each Pearl are then 
	 * printed after a fieldDelimiter after the name and are separated by more fieldDelimiters.
	 * @param out OutputStream to write the Pearltree to.
	 * @param rootNode 
	 * @param fieldDelimiter String to place between fields.
	 */
	public static void printTree(OutputStream out, Pearl rootNode, String fieldDelimiter) {
		PrintStream outps = new PrintStream(out);
		if (rootNode != null)
			_printTree(outps, rootNode, "", fieldDelimiter, "", false);
	}
	
	// Recursive helper procedure for the printTree function..
	private static void _printTree(PrintStream outps, Pearl rootNode, 
							String levelPrefix, String fieldDelimiter, String entryMarker, boolean expanded) {
		// Print the rootPearl out..
		Statement rootNodeTitle = rootNode.toRDF().asResource().getProperty(DC_11.title);
		outps.println(levelPrefix + ((rootNodeTitle != null) ? 
				rootNodeTitle.getObject().toString() : ""));
		
		// Iterate over all the Pearls that are a child of the RootPearl..
		Model m = rootNode.toRDF().getModel();
		StmtIterator sit = m.listStatements(null, PT.parentTree, rootNode.toRDF());
		Resource childPearl;
		while (sit.hasNext()) {
			// Save the reference..
			childPearl = sit.nextStatement().getSubject();
			
			// Check if the pearl is a refPearl, if so, recursively follow:
			if (childPearl.hasProperty(RDF.type, PT.RefPearl)) {
				Statement newRoot;
				if ((newRoot = childPearl.getProperty(RDFS.seeAlso)) != null ) {
					// It is a reference or alias Pearl.
					// Follow reference Pearls recursively..
					_printTree(outps, Clam.makePearl(newRoot.getObject()), 
							fieldDelimiter + levelPrefix, fieldDelimiter, entryMarker, expanded);
				}
			} else {
				// Write the pearl to  
				// It is a PagePearl or AliasPearl, get and Print the properties..
				writePearl(outps, childPearl, levelPrefix, fieldDelimiter, entryMarker, expanded);
			}
		}
	}
	
	// Write a pearl Resource to the specified output stream..
	private static void writePearl(PrintStream outps, Resource pearl, 
			String levelPrefix, String fieldDelimiter, String entryMarker, boolean expanded) {
		
		Statement title, identifier, inTreeSince;
		title 		= pearl.getProperty(DC_11.title);
		identifier  = (pearl.hasProperty(RDF.type, PT.AliasPearl)) ? 
				pearl.getProperty(RDFS.seeAlso) : pearl.getProperty(DC_11.identifier);
		inTreeSince = pearl.getProperty(PT.inTreeSince);
		
		// Print the property values..
		if (expanded) {
			outps.println(levelPrefix + fieldDelimiter + entryMarker + statementToPropObj(title));
			outps.println(levelPrefix + fieldDelimiter + entryMarker + statementToPropObj(identifier));
			outps.println(levelPrefix + fieldDelimiter + entryMarker + statementToPropObj(inTreeSince));
			outps.println();
		} else {
			outps.print(levelPrefix);
			outps.print(fieldDelimiter + statementToPropObj(title));
			outps.print(fieldDelimiter + statementToPropObj(identifier));
			outps.print(fieldDelimiter + statementToPropObj(inTreeSince));
			outps.println();
		}	
	}
	
	
	// Given a statement, return the predicate and the object..
	private static String statementToPropObj(Statement s) {
		return (s != null) ? 
				s.getPredicate().getLocalName() + " : " + s.getObject().toString() : "";
	}
	
	/**
	 * Print the entire Pearltree in it its directory-like structure in an easy to view
	 * format.
	 * @param out
	 */
	public static void writePearlTree(OutputStream out, Pearl root) {
		PrintStream outps = new PrintStream(out);
		_printTree(outps, root, "", "\t","* ", true);
	}
	
	/**
	 * Write the Jena model representation of the Pearltrees data to the given 
	 * OutputStream.
	 * @param out
	 */
	public static void writeRAW(OutputStream out, Pearl p) {
		p.toRDF().getModel().write(out);
	}
	
	/**
	 * Write the Jena model representation of the RDF data to standard out.
	 */
	public static void writeRAW(Pearl p) {
		writeRAW(System.out, p);
	}
	
	

	/* * * 
	 * Clams make Pearls, right?..
	 */
	protected static class Clam {
		protected static Pearl makePearl(RDFNode resNode) {
			Pearl p = null;
			
			// Construct the proper concrete type of Pearl..
			if (resNode != null) {
				Resource r = resNode.asResource();
				if      (r.hasProperty(RDF.type, PT.RootPearl))
					p = new RootPearl(resNode);
				else if (r.hasProperty(RDF.type, PT.PagePearl)) 
					p = new PagePearl(resNode);
				else if (r.hasProperty(RDF.type, PT.RefPearl))
					p = new RefPearl(resNode);
				else if (r.hasProperty(RDF.type, PT.AliasPearl))
					p = new AliasPearl(resNode);
			}
			return p;
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

		public void onNote(Note note) {}
	}
	
	
	
	public static FileSystemPearlHandler getFileSystemPearlHandler(String path, boolean recursive) {
		return recursive ? new RecursiveFileSystemPearlHandler(path) : new FileSystemPearlHandler(path);
	}
	
	public static FileSystemPearlHandler getFileSystemPearlHandler(boolean recursive) {
		return getFileSystemPearlHandler(new String(), recursive);
	}
	
	/**
	 * TODO: Document..
	 * @author David
	 *
	 */
	public static class FileSystemPearlHandler implements PearlHandler {

		private File currentDir;
		
		public FileSystemPearlHandler() {
			this(new String());
		}
		public FileSystemPearlHandler(String outputDirectory) {
			currentDir = new File(outputDirectory);
			currentDir.mkdirs();
			
		}
		
		@Override
		public void onPearl(RootPearl rootPearl) {
			String dirName = rootPearl.getTitle();
			File dir = new File(currentDir, dirName);
			dir.mkdir();
			currentDir = dir;
		}

		@Override
		public void onPearl(PagePearl pagePearl) {
			String fileName = pagePearl.getTitle();
			File pFile = new File(currentDir, fileName);
			OutputStream o = null;
			try {
				o = new FileOutputStream(pFile);
				o.write(pagePearl.getPageURLstr().getBytes());
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		@Override
		public void onPearl(AliasPearl aliasPearl) {
			String fileName = aliasPearl.getTitle();
			File pFile = new File(currentDir, fileName);
			OutputStream o = null;
			try {
				o = new FileOutputStream(pFile);
				o.write(aliasPearl.getPearlURI().getBytes());
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
		}

		@Override
		public void onPearl(RefPearl refPearl) {
			String fileName = refPearl.getTitle();
			File pFile = new File(currentDir, fileName);
			OutputStream o = null;
			try {
				o = new FileOutputStream(pFile);
				o.write(refPearl.getPearlURI().getBytes());
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
		}

		@Override
		public void onNote(Note note) {
			PrintWriter p;
			try {
				p = new PrintWriter(new FileOutputStream(note.getParentPearl().toString(), true));
				p.println();
				p.println(note.getCreator());
				p.println(note.getDate());
				p.println(note.getText());
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}// FileSystemPearlHandler
	
	/**
	 * TODO: Document..
	 * @author David
	 *
	 */
	public static class RecursiveFileSystemPearlHandler extends FileSystemPearlHandler {
		
		public RecursiveFileSystemPearlHandler(String outputDirectory) {
			super(outputDirectory);
		}
		
		public RecursiveFileSystemPearlHandler() {
			super(new String());
		}
		
		@Override
		public void onPearl(RefPearl refPearl) {
			RecursiveFileSystemPearlHandler rfsph = 
					new RecursiveFileSystemPearlHandler(
							super.currentDir.getParentFile().getPath());
			
			for (Pearl p : refPearl.getPearlTree().getTreePearls()) {
				p.accept(rfsph);
			}	
		}	
	} // RecursiveFilesystemPearlHandler
	
}// Pearltrees
