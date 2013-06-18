# Gradle plug-in for Strapdown.js

> IN DEVELOPMENT

The plug-in for [Gradle](http://www.gradle.org/) producing HTML files for use along with [Strapdown.js](http://strapdownjs.com/).

## How to use

A snippet of gradle build script below will make usage clear.

    buildscript {
        TODO add link to bintray
    }

    strapdownjs {
        // parameters as required for Copy task:
        destinationDir = "$buildDir/manuals"
        from 'manuals'

        // specific parameters:
        title = 'My Manuals' // the HTML page title; not set by default
        theme = 'simplex' // theme to use; detaults to 'united'; check supported themes at http://strapdownjs.com/
        version = '0.2' // the version of strapdown.js; defaults to '0.2'

        // to redefine template file:
        templateFile = file(...)
    }


## How to build
Use Gradle wrapper: `./gradlew` for *nix, or `gradlew.bat` for Windows.

## License
See [LICENSE](LICENSE) file.
