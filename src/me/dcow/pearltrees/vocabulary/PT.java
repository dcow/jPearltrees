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

package me.dcow.pearltrees.vocabulary;

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
	
	////// Strings:
	//// Primary Classes..
	public 	static 	final 	String 	sTree		= "Tree";
	public 	static 	final 	String 	sPearl		= "Pearl";
	public 	static 	final 	String 	sPagePearl	= "PagePearl";
	
	//// Secondary Classes..
	public 	static 	final 	String 	sAliasPearl	= "AliasPearl";
	public 	static 	final 	String 	sRefPearl	= "RefPearl";
	public 	static 	final 	String 	sRootPearl	= "RootPearl";
	public 	static 	final 	String 	sNote		= "Note";
	
	//// Class Members..
	// Pearl
	public  static  final	String	sInTreeSince	= "inTreeSinceDate";
	public  static  final 	String  sParentTree 	= "parentTree";
	public	static	final	String 	sLeftPos		= "leftPos";
	public	static	final	String 	sRightPos		= "rightPos";
	
	//// Note
	public	static	final	String	sParentPearl	= "parentPearl";
	public	static	final	String	sNoteText		= "noteText";

	
	
	////// Properties:
	//// Primary Classes..
	public 	static 		Property	Tree 		= null;
	public 	static 		Property	Pearl 		= null;
	public 	static 		Property	PagePearl 	= null;
	
	//// Secondary Classes..
	public 	static 		Property	AliasPearl	= null;
	public 	static 		Property	RefPearl	= null;
	public 	static 		Property	RootPearl	= null;
	public 	static 		Property	Note 		= null;

	//// Class Members..
	// Pearl
	public  static		Property	inTreeSince = null;
	public  static		Property	parentTree 	= null;
	public  static		Property	leftPos 	= null;
	public  static		Property	rightPos 	= null;
	
	// Note
	public	static		Property	parentPearl	= null;
	public	static		Property	noteText	= null;

	
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
			parentPearl = new PropertyImpl(PearlTreeNS, sParentPearl);
			leftPos		= new PropertyImpl(PearlTreeNS, sLeftPos);
			rightPos	= new PropertyImpl(PearlTreeNS, sRightPos);
			noteText	= new PropertyImpl(PearlTreeNS, sNoteText);
			
		} finally {}
	}
}

