package me.dcow.pearltrees;

/**
 * PearlHandler is passed to the callback functions of the traversal
 * methods. 
 * 
 * @author David
 *
 */
public interface PearlHandler {
	public void onPearl(RootPearl 	rootPearl );
	public void onPearl(PagePearl 	pagePearl );
	public void onPearl(AliasPearl 	aliasPearl);
	public void onPearl(RefPearl 	refPearl  );
	public void onNote (Note		note	  );
}
