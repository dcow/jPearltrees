# jPearltrees Java Library #

Author: David Cowden

Date:   03 July, 2012

## Purpose ##

jPearltrees is a Java library that provides an interface for 
handling the _RDF/XML_ data exported from a 
[Pearltrees](http://www.pearltrees.com) account.

_Pearltrees_ is an individualy managed data-curation system with
support for teams and collaboration.  Pearltrees lets you organize
categorical data into trees and provides convenient methods
for further data curation.

As the owner of all the data in your Pearltrees account, you
can export your entire collection in an _RDF/XML_ format.
jPearltrees adapts the _RDF/XML_ structure back into the natural 
tree structure and provides methods for accessing the data
containted within a *PearlTree*.


## Usage ##

jPearltrees is still in development.  To use it right now, you
need (at least) the [Jena 2.7.2](https://repository.apache.org/
content/repositories/releases/org/apache/jena/jena-core/) 
libraries on your classpath.  jPearltrees
is backed by an Apache Jena RDF _Model_ class.  

Beyond setup, usage is simple:

```java
// Construct a Pearltree..
Pearltree pt = Pearltrees.buildPearlTrees("pearltrees_export.rdf")
 
// Print a pretty version folling using a handler that follows 
// RefPearls recursively..
Pearltrees.traversePearlTree(pt, 
    Pearltrees.getTextPearlHandler(System.out, true));
```

__Default TextPearlHandler output:__
<pre>
# dcow's drop zone
    # Education
        # Evolving_Critique
            * Science education myths that keep us from fixing the system
            * Codecademy: Next Frontier In Digital Education Movement
            * Public schools: Choose your own misadventure
        @ Gamify Your Lessons
        # GamificationofEdu
            * How to teach history (and lots more) with Minecraft
            * 3 Social Learning Trends to Watch in 2012 : Innovation
            * Summit on Science, Entertainment, and Education - Will Wright on Vimeo
        # MinorityandWomenEdu
            * Study aims to learn why some black men succeed in college
            * Female Geeks Flex Their Skills At Ladies-Only Hackathon
            * Why women should pick science & math degrees over liberal arts
        # SoftwareEdu
            * Mentorship From Silicon Valley Techies Encourages High School Girls To Dream Bigger
            * Computer Programming for All: A New Standard of Literacy
            * How Codecademy got so hot, so fast
            * Developer's Night | a tidal force
            * Absolutely amazing: 6th grade iPhone app developer speaks at TEDx
            * Apple's plan to get its products in schools? Educate the educators.
            * Empirical Software Engineering
    # Immersive_VR_Gaming_LifeLearn_Social
        # Immersive Learning
        # face recognition
            * FREE Face Recognition API, Face Recognition Software Apps for the masses – face.com
            * Google's Artificial Brain Learns to Find Cat Videos | Wired Science
            * Apple Patent Describes a More Secure Face-Recognition System | Gadget Lab
        # immersivesmartdevices
            * Microsoft: We're Still Building HomeOS
            * Smart bed makes itself after you roll out of the sack | Digital Trends
            * This Android Alarm Only Deactivates When You are Out of Bed
            * New GE refrigerators will recognize when to stop filling containers with water
            * Wallpaper That Blocks Wi-Fi - Ideas Market
        # NewFormsofInteraction
            * Xbox 'Power Glove' Offers Precise, Kinect-Like Gesture Control | Gadget Lab
            * Microsoft Demos Super Fast Touchscreen Display
            * Leap Motion
            * The Story of 'Pah!', The Voice-Controlled Mobile Game
            * Mattel plans to make ‘Back to the Future’ hover boards
            * Apple introduces us to the Wild World of Coded Magnets
            # Kinect
                * Kinect-based mood analysis could bolster Microsoft's advertising efforts | Digital Trends
                * Kinect-enabled Kreek interface uses a flexible surface for added depth
                * Kinect used for holographic 3D video conferencing
                * Whole Foods Debuts Kinect-Powered Shopping Carts [VIDEO]
                * Use Kinect to teach anatomy? It's a 'Mirracle'! | Health Tech
                * Kinect for Windows adds seated skeletal tracking
                * More Mind-Blowing Real-World Kinect Interaction From Microsoft Research
            * Synaptics Demonstrates Windows 8 Trackpad Gestures On Video
            * A Brief Rant on the Future of Interaction Design
            * Stop and Start Your Music With Hand Gestures
        # e-books
            * Should College Students Be Forced To Buy E-Books?
            * How My Book Became A (Self-Published) Best Seller
            * Publishers Hustle to Make E-Books More Immersive | Underwire
            * Are Enhanced E-Book Apps Worth It?
            * Enhanced e-books: Truly moving literature
            * 5 Things to Consider Before Self-Publishing Your Book
            * New atlas app invites children to take the world for a spin
        @ Gamify Your Lessons
        # sensormonitoring
            * Smart shirt recognizes your exercise movements, vibrates to correct body form | Digital Trends
            * BMW Developing Handwriting Recognition, Gesture Controls | Autopia
            * The Shoe of Tomorrow Knows How Hard You Hustle | Playbook
            * Police Investigate High School Coach's Handling of Player Concussions
            * TinyDB: A Declarative Database for Sensor Networks
        # AdvanceLightPhotography
>            ...
</pre>

__More usage:__

```java
// Print a subtree..
PearlIterator pit = pt.listTreePearls();
while (pit.hasNext()) {
   	// Access Pearl data
    Pearl p = pit.nextPearl
    System.out.println( p.getTitle() + 
                        " : " + 
                        p.getEntryDate());

    NoteIterator nit = p.getNotes();
    ...
}

// Use your own handler..
PearlHandler ph = new PearlHandler() {
    public onPearl(RootPearl rootPearl) {
        // Do Something with rootPearl..
    }
    public onPearl(PagePearl pagePearl) {
        // Do something with pagePearl..
    }
    public onPearl(AliasPearl aliasPearl) {
        // Do Something with aliasPearl..
    }
    public onPearl(RefPearl refPearl) {
        // Do something with refPearl..
    }
}
    
// Each Pearl passes itself to the handler's callback..
List<Pearl> treePearls = pt.getTreePearls();
for(Pearl pearl : treePearls) {
    pearl.accept(ph);    
}

```
    
-------------------

## More coming.. ##
