# jlooch-neo

Based on the work by [Brad Garton](http://sites.music.columbia.edu/brad/jlooch/). A copy of the back-end of the original JLooch (with minor modifications for the new front-end) and a Java Swing/AWT front-end.

This application creates ambient, droning sounds which are great as background noise or to help you relax.

# Requirements

You will need Java installed to build and run this application. Works with `openjdk 11.0.13` or newer.

# How to Build / Run

To build `jlooch-neo`, use your local Gradle installation or use the provided Gradle Wrapper:

`gradle clean build`

or

`./gradlew clean build`

To run the application, run it as a Java jar:

`java -jar build/libs/jlooch-neo.jar`

## Legacy UI

The code also includes the original UI. To run the application with that, select the jlooch Class or set `Main-Class` to `jlooch.jlooch` in `build.gradle`.

# Known Bugs

The label text on the annoyingly-small toggle buttons appears to be misaligned.