## Corelyzer

Corelyzer is a scalable, extensible visualization tool developed to enhance the
study of geological cores. The strength of Corelyzer is the ability to display
large sets of core imagery, with multi-sensor logs, annotations and plugin-supported
visuals alongside core imagery.

Corelyzer consists of two pieces: the SceneGraph library (C++) and the Corelyzer 
application itself (Java).

Prebuilt Corelyzer application packages for Windows and Mac can be downloaded [here](https://github.com/corewall/corelyzer/releases).


### Building Corelyzer
The following is intended for Linux users and others interested in building Corelyzer and/or SceneGraph from scratch on supported platforms.

#### Requirements
- Java Development Kit (JDK) version 11. We recommend the [OpenJDK distribution](https://adoptopenjdk.net).
- The SceneGraph native library. Prebuilt versions are provided for Mac (libscenegraph.jnilib) and Windows (scenegraph.dll) in scenegraph/dist. To build SceneGraph from scratch, follow the instructions in [scenegraph/README.md](https://github.com/corewall/corelyzer/tree/master/scenegraph).

#### Building on macOS/OSX

##### Build and run from the command line

Use the provided Gradle wrapper:

    ./gradlew clean build create-working-dir

To launch Corelyzer:

    cd working_dir
    java -cp "../app/dist/*" -Djava.library.path=../scenegraph/dist corelyzer.ui.splashscreen.SplashScreenMain

##### Generate macOS/OSX .app bundle

To generate a complete .app bundle:
- Place a Java 11 runtime in the `packages/java_runtime` dir (official builds use the [OpenJDK JRE](https://adoptopenjdk.net/archive.html).)
- Update the `javaRuntimeFile` var on line 15 of `build.gradle` with your runtime's name

Then, run

    ./gradlew clean packageMac

to generate an .app bundle in the `dist/mac` directory.

#### Building on Windows

Use the provided Gradle wrapper:

    ./gradlew clean packageWin

This will compile the Java components and create a directory containing the Corelyzer executable, required resources and DLLs. Like Mac, it uses prebuilt versions of the SceneGraph native library and associated SceneGraph JAR. The generated package will be placed in the `dist/win` directory.

#### Building and Running on Linux

Corelyzer builds and runs on Ubuntu LTS 18.04.4 "Bionic Beaver" and 20.04.1 "Focal Fossa". It **should** work in other distributions and versions. Add an Issue if you run into trouble and we'll try to help.

**NOTE**: Corelyzer is known to crash when launched on Ubuntu virtual machines with kernel version 5.4+. This can be resolved by disabling hardware acceleration in the VM.

No pre-built SceneGraph library is provided on Linux. To build, follow the instructions in [scenegraph/README.md](https://github.com/corewall/corelyzer/tree/master/scenegraph).

Once SceneGraph is built, build Corelyzer with:
    
    ./gradlew clean packageLinux

This will build Java components and copy SceneGraph and other required resources to `working_dir`.

To run Corelyzer:

    cd working_dir
    java -cp "../app/dist/*" -Djava.library.path=/home/lcdev/proj/corewall/corelyzer/scenegraph/dist:/usr/lib/x86_64-linux-gnu/ corelyzer.ui.splashscreen.SplashScreenMain

Adjust `java.library.path` to reflect your configuration if needed.

Corelyzer should launch. Have fun!




Copyright (c) 2020, CSDF (http://csdco.umn.edu)
