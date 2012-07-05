package me.dcow.pearltrees;

/**
 * PearlHandler is passed to the callback functions of the traversal
 * methods.
 * @author David
 *
 */
public interface PearlHandler {
	public void onRootPearl (Pearl rootPearl );
	public void onPagePearl (Pearl pagePearl );
	public void onRefPearl  (Pearl refPearl  );
	public void onAliasPearl(Pearl aliasPearl);
}
