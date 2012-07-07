package me.dcow.pearltrees;

import java.net.MalformedURLException;
import java.net.URL;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.vocabulary.DC_11;

/**
 * A PagePearl is a Pearl that contains the URL of one Page, Article
 * or Post available on the web.
 * 
 * In the greater PearlTree graph, all the PagePearls are leaves.  It
 * is possible that another PearlTree is also a leaf if it has no
 * pages, however, you should avoid doing this in the first place in
 * order to maintain a well structured tree.  That is not to say it 
 * can't happen by mistake or intent in certain cases.
 * 
 * @author David
 *
 */
public class PagePearl extends Pearl {

	protected PagePearl(RDFNode ptNode) {
		super(ptNode);
	}
	
	
	/**
	 * Get the URL of the page described by this PagePearl.
	 * @return String representation of the URL
	 */
	public String getPageURLstr() {
		return pData.getProperty(DC_11.identifier).getString();
	}

	/**
	 * Return the Java.net.URL of the Page or null if there url 
	 * is malformed.
	 * @return URL representation of the URL.
	 */
	public URL getPageURL() {
		URL url = null;
		try {
			url = new URL(getPageURLstr());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	protected void accept(PearlHandler ph) {
		ph.onPearl(this);
	}
}
