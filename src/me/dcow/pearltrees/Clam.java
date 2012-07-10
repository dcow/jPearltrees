package me.dcow.pearltrees;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/* * * 
 * Clams make Pearls, right?..
 * Clam is a factory for Pearls.  Given the RDF resource of a Pearl,
 * the makePearl method will return a Pearl of the appropriate type.
 */
public class Clam {
	public static Pearl makePearl(RDFNode resNode) {
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
	
	public static Note makeNote(RDFNode note) {
		return new Note(note);
	}
}