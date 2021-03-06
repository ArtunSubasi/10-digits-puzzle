# 10 Digits Puzzle

[![Release](https://github.com/ArtunSubasi/10-digits-puzzle/workflows/Release/badge.svg)](https://github.com/ArtunSubasi/10-digits-puzzle/actions?query=workflow%3ARelease)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)
[![Commitizen friendly](https://img.shields.io/badge/commitizen-friendly-brightgreen.svg)](http://commitizen.github.io/cz-cli/)

### [🎮 Run on Browser 🎮](https://artunsubasi.github.io/10-digits-puzzle/)

This repo contains:
* a domain model of the 10 digits puzzle also known as the tricky numbers puzzle, implemented in Kotlin:
https://www.myminifactory.com/object/3d-print-10-digits-puzzle-tricky-number-puzzle-72142
* an algorithm which solves the puzzle using backtracking
* a multiplatform live renderer using the KorGe game engine

## Domain Model
![Alt text](./docs/domain-model.svg)

The `PuzzleArea` represents a rectangle area using `PuzzleRow`s of `Tile`s.
Each `Tile` contains four `segment`s.
The `segment` occupation uses a `Char` which is then used by the GUI to choose the color.

Each `PuzzlePiece` is represented using the smallest possible `PuzzleArea`.
For example, the piece 3 is represented by a 3x5 `PuzzleArea` whose `segment`s are
set according to the shape of the `PuzzlePiece`. The whole playable area also
uses a 11x9 `PuzzleArea`.

`PuzzleState` captures the state of the puzzle using
- the current `PuzzleArea`
- currently `availablePieces`
- currently `usedPieces`
- and all the `Move`s that the player has made preserving order

A `Move` represents a `PuzzlePiece` which was possibly rotated, mirrored (flipped) 
and placed on a certain `coordinate` on the `PuzzleArea`.
A `Move` on a `PuzzleState` leads to a new `PuzzleState`.

The `PuzzleSolver` is initialized with a single `PuzzleState` to iterate over possible next
`PuzzleState`s to find a solution.

All classes are immutable except for the `PuzzleSolver`.
The `PuzzleSolver` contains a mutable list of possible `PuzzleState` to check.
The `PuzzleSolver` updates the states to check by calculating and applying all possible `Move`s
based on the next `PuzzleState` to check.

## Development

[🎮 Run development version 🎮](https://artunsubasi.github.io/10-digits-puzzle/dev) (`develop` branch)

The rest of the README file is based on the KorGe template at GitHub: <https://github.com/korlibs/korge-hello-world>

You can open this project in IntelliJ IDEA by opening the folder or the `build.gradle.kts` file.

For Windows, change all the `./gradlew` for `gradlew.bat`.

You should use Gradle 5.5 or greater and Java 8 or greater.

## Compiling for the JVM (Desktop)

Inside IntelliJ you can go to the `src/commonMain/kotlin/main.kt` file and press the green ▶️ icon
that appears to the left of the `suspend fun main()` line.

Using gradle tasks on the terminal:

```bash
./gradlew runJvm                    # Runs the program
./gradlew packageJvmFatJar          # Creates a FAT Jar with the program
./gradlew packageJvmFatJarProguard  # Creates a FAT Jar with the program and applies Proguard to reduce the size
```

Fat JARs are stored in the `/build/libs` folder.

## Compiling for the Web

Using gradle tasks on the terminal:

```bash
./gradlew jsWeb                     # Outputs to /build/web
./gradlew jsWebMin                  # Outputs to /build/web-min (applying Dead Code Elimination)
./gradlew jsWebMinWebpack           # Outputs to /build/web-min-webpack (minimizing and grouping into a single bundle.js file)
./gradlew runJs                     # Outputs to /build/web, creates a small http server and opens a browser
```

You can use any HTTP server to serve the files in your browser.
For example using: `npm -g install http-server` and then executing `hs build/web`.

You can also use `./gradlew -t jsWeb` to continuously building the JS sources and running `hs build/web` in another terminal.
Here you can find a `testJs.sh` script doing exactly this for convenience.

You can run your tests using Node.JS by calling `jsTest` or in a headless chrome with `jsTestChrome`.

## Compiling for Native Desktop (Windows, Linux and macOS)

Using gradle tasks on the terminal:

```bash
./gradlew linkMainDebugExecutableMacosX64         # Outputs to /build/bin/macosX64/mainDebugExecutable/main.kexe
./gradlew linkMainDebugExecutableLinuxX64         # Outputs to /build/bin/linuxX64/mainDebugExecutable/main.kexe
./gradlew linkMainDebugExecutableMingwX64         # Outputs to /build/bin/mingwX64/mainDebugExecutable/main.exe
```

Note that windows executables doesn't have icons bundled.
You can use [ResourceHacker](http://www.angusj.com/resourcehacker/) to add an icon to the executable for the moment.
Later this will be done automatically.

### Cross-Compiling for Linux/Windows

If you have docker installed, you can generate native executables for linux and windows
using the cross-compiling gradle wrappers:

```bash
./gradlew_linux linkMainDebugExecutableLinuxX64   # Outputs to /build/web
./gradlew_win   linkMainDebugExecutableMingwX64   # Outputs to /build/web
```

### Generating MacOS `.app`

```bash
./gradlew packageMacosX64AppDebug             # Outputs to /build/unnamed-debug.app
```

You can change `Debug` for `Release` in all the tasks to generate Release executables.

You can use the `strip` tool from your toolchain (or in the case of windows found in the ``~/.konan` toolchain)
to further reduce Debug and Release executables size by removing debug information (in some cases this will shrink the EXE size by 50%).

In windows this exe is at: `%USERPROFILE%\.konan\dependencies\msys2-mingw-w64-x86_64-gcc-7.3.0-clang-llvm-lld-6.0.1\bin\strip.exe`.

### Linux notes

Since linux doesn't provide standard multimedia libraries out of the box,
you will need to have installed the following packages: `freeglut3-dev` and `libopenal-dev`.

In ubuntu you can use `apt-get`: `sudo apt-get -y install freeglut3-dev libopenal-dev`.

## Compiling for Android

You will need to have installed the Android SDK in the default path for your operating system
or to provide the `ANDROID_SDK` environment variable. The easiest way is to install Android Studio.

Using gradle tasks on the terminal:

### Native Android (JVM)

```bash
./gradlew installAndroidDebug             # Installs an APK in all the connected devices
./gradlew runAndroidEmulatorDebug         # Runs the application in an emulator
```

Triggering these tasks, it generates a separate android project into `build/platforms/android`.
You can open it in `Android Studio` for debugging and additional tasks. The KorGE plugin just
delegates gradle tasks to that gradle project.

### Apache Cordova (JS)

```bash
./gradlew compileCordovaAndroid           # Just compiles cordova from Android
./gradlew runCordovaAndroid               # Runs the application (dce'd, minimized and webpacked) in an Android device
./gradlew runCordovaAndroidNoMinimized    # Runs the application in Android without minimizing (so you can use `chrome://inspect` to debug the application easier)
```



## Compiling for iOS

You will need XCode and to download the iOS SDKs using Xcode.

Using gradle tasks on the terminal:

### Native iOS (Kotlin/Native) + Objective-C

Note that the necessary bridges are built using Objective-C instead of Swift, so the application
won't include Swift's runtime.

```bash
./gradlew iosBuildSimulatorDebug          # Creates an APP file
./gradlew iosInstallSimulatorDebug        # Installs an APP file in the simulator
./gradlew iosRunSimulatorDebug            # Runs the APP in the simulator

```

These tasks generate a xcode project in `build/platforms/ios`, so you can also open the project
with XCode and do additional tasks there.

It uses [XCodeGen](https://github.com/yonaskolb/XcodeGen) for the project generation
and [ios-deploy](https://github.com/ios-control/ios-deploy) for deploying to real devices.

### Apache Cordova (JS)

```bash
./gradlew compileCordovaIos               # Just compiles cordova from iOS
./gradlew runCordovaIos                   # Runs the application (dce'd, minimized and webpacked) in an iOS device
./gradlew runCordovaIosNoMinimized        # Runs the application in iOS without minimizing (so you can use Safari on macOS to debug the application easier)
```

