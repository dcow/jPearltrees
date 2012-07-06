package me.dcow.pearltrees;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.vocabulary.DC_11;
import com.hp.hpl.jena.vocabulary.RDF;

public class Pearl extends PTNode {
	
	protected Pearl(RDFNode ptNode) {
		super(ptNode);
	}
	
	public RDFNode toRDF() {
		return pData;	
	}
	
	protected String getURI() {
		return pData.getURI();
	}
	
	public String getTitle() {
		return pData.getProperty(DC_11.title).getString();
	}
	
	public String getEntryDate() {
		return pData.getProperty(PT.inTreeSince).getString();
	}
	
	public int getLeftPos() {
		return pData.getProperty(PT.leftPos).getInt();
	}
	
	public int getRightPos() {
		return pData.getProperty(PT.rightPos).getInt();
	}
	
	public String getType() {
		return pData.getProperty(RDF.type).getString();
	}
	
	public NoteIterator getNotes() {
		return new NoteItrRes(pData.getModel().listResourcesWithProperty(PT.Tree, getURI()));
	}
	
	
	@Override
	public String toString() {
		return pData.toString();
	}
}
