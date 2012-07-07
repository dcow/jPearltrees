package me.dcow.pearltrees;

import me.dcow.pearltrees.Pearltrees.Clam;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.vocabulary.DC_11;

public class Note extends PTNode {
	
	protected Note(RDFNode ptNode) {
		super(ptNode);
	}
	
	public String getCreator() {
		return pData.getProperty(DC_11.creator).getString();
		// TODO: change so that we lookup the creator and return their
		// name from the soic profile.
	}
	
	public String getDate() {
		return pData.getProperty(DC_11.date).getString();
	}

	public String getText() {
		return pData.getProperty(PT.noteText).getString();
	}

	public Pearl getParentPearl() {
		return Clam.makePearl(pData.getPropertyResourceValue(PT.parentPearl));
	}	
	
	protected void accept(PearlHandler nh) {
		nh.onNote(this);
	}
}

