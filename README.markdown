# jPearltrees Java Library #

Author: David Cowden
Date:   03 July, 2012

## Purpose ##

jPearltrees is a Java library for manipulating the _RDF/XML_ data
exported from [Pearltrees](http://www.pearltrees.com)

_Pearltrees_ is an individualy managed data-curation system with
support for teams and collaboration.  Pearltrees lets you
curate your data into trees provided your data can be accessed
with a URL.  

jPearltrees adapts the _RDF/XML_ structure into a natural tree
structure for easier human visualization and comprehension.


## Usage ##

jPearltrees is still in development.  To use it right now, you
need (at least) the [Jena 2.7.2](https://repository.apache.org/
content/repositories/releases/org/apache/jena/jena-core/) 
libraries on your classpath.  jPearltrees
is backed by an Apache Jena RDF _Model_ class.  

Beyond setup, usage is simple:

    // Construct a Pearltree
    Pearltree pt = new Pearltree("fileNameOrURI");
 
    // Print a pretty version..
    pt.writePrealTree(System.out);


> dcow
>	MobileDevices_Tablets_iPad/Android_Social
>		* title : The #1 Social Networking App Isn't Facebook, It's Voxer
>		* identifier : http://thenextweb.com/apps/2011/12/08/the-1-social...
>		* inTreeSinceDate : 2012-01-07T18:48:07
>
>		NewMarketsIntroducedPlatforms
>			* title : Apple Toys With 3D iPhone Interface [VIDEO]
>			* identifier : http://mashable.com/2012/01/13/apple-patent-3d-interface/
>			* inTreeSinceDate : 2012-01-15T01:13:37
>
>			* title : The Technology Behind Virgin Atlantic's Mid-Flight Cellp...
>			* identifier : http://www.wired.com/gadgetlab/2012/05/how-virgin...
>			* inTreeSinceDate : 2012-06-02T22:08:40
>            ...


    // Write tree structure to file system..
    pt.writeToFileSystem("absoluteOrRelativePath");

    // Print a subtree..
    RootPearl root = pt.getRoot()
    Pearls children = root.getChildren();
    for (Pearl child : children) {
    	Pearltree.printPearl(System.out, child);

    	// Access Pearl data
        String name = child.getName();
        String url  = child.getURL();
        String date = child.getDateInserted();
        ...
    }

    
-------------------

## More coming.. ##
