package me.dcow.pearltrees;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;


/* Class containing the PearlTree RDF vocabulary.
 * The official description file is available at:
 *  -- <http://www.pearltrees.com/rdf/0.1/> --
 */
public class PT {
		
		// This is the Pearltrees namespace used to grab the Pearltreee resources when
	// reading a Pearltrees RDF/XML file..
	public static final String PearlTreeNS 	= "http://www.pearltrees.com/rdf/0.1/#";
	
	//// Strings:
	// Primary Classes..
	public 	static 	final 	String 	sTree		= "Tree";
	public 	static 	final 	String 	sPearl		= "Pearl";
	public 	static 	final 	String 	sPagePearl	= "PagePearl";
	
	// Secondary Classes..
	public 	static 	final 	String 	sAliasPearl	= "AliasPearl";
	public 	static 	final 	String 	sRefPearl	= "RefPearl";
	public 	static 	final 	String 	sRootPearl	= "RootPearl";
	public 	static 	final 	String 	sNote		= "Note";
	
	// Class Members..
	public  static  final	String	sInTreeSince	= "inTreeSinceDate";
	public  static  final 	String  sParentTree 	= "parentTree";
	public	static	final	String 	sLeftPos		= "leftPos";
	public	static	final	String 	sRightPos		= "rightPos";
	
	//// Properties:
	// Primary Classes..
	public 	static 		Property	Tree 		= null;
	public 	static 		Property	Pearl 		= null;
	public 	static 		Property	PagePearl 	= null;
	
	// Secondary Classes..
	public 	static 		Property	AliasPearl	= null;
	public 	static 		Property	RefPearl	= null;
	public 	static 		Property	RootPearl	= null;
	public 	static 		Property	Note 		= null;

	// Class Members..
	public  static		Property	inTreeSince = null;
	public  static		Property	parentTree 	= null;
	public  static		Property	leftPos 	= null;
	public  static		Property	rightPos 	= null;
	
	static {
		try {
			Tree 		= new PropertyImpl(PearlTreeNS, sTree);
			Pearl 		= new PropertyImpl(PearlTreeNS, sPearl);
			PagePearl 	= new PropertyImpl(PearlTreeNS, sPagePearl);
			AliasPearl	= new PropertyImpl(PearlTreeNS, sAliasPearl);
			RefPearl	= new PropertyImpl(PearlTreeNS, sRefPearl);
			RootPearl	= new PropertyImpl(PearlTreeNS, sRootPearl);
			Note		= new PropertyImpl(PearlTreeNS, sNote);
			inTreeSince = new PropertyImpl(PearlTreeNS, sInTreeSince);
			parentTree	= new PropertyImpl(PearlTreeNS, sParentTree);
			leftPos		= new PropertyImpl(PearlTreeNS, sLeftPos);
			rightPos	= new PropertyImpl(PearlTreeNS, sRightPos);
			
		} finally {}
	}
}

