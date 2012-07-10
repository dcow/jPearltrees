package me.dcow.pearltrees;

import com.hp.hpl.jena.rdf.model.StmtIterator;

class PearlItrStmt implements PearlIterator {

	StmtIterator sit;
	
	protected PearlItrStmt(StmtIterator stmtIt) {
		sit = stmtIt;
	}
	
	
	@Override
	public boolean hasNext() {
		return sit.hasNext();
	}

	@Override
	public Pearl next() {
		return Clam.makePearl(sit.nextStatement().getSubject());
	}
	

	@Override
	public Pearl nextPearl() {
		return next();
	}

	@Override
	public void remove() {
		sit.removeNext();
	}


	
	
}
