# Gradle plug-in for Strapdown.js

> IN DEVELOPMENT

The plug-in for [Gradle](http://www.gradle.org/) producing HTML files for use along with [Strapdown.js](http://strapdownjs.com/).

## How to use

A snippet of gradle build script below will make usage clear.

    buildscript {
        TODO add link to bintray
    }

    strapdownjs {
        // parameters inherited from Copy task (check it for more details):
        destinationDir = "$buildDir/manuals"
        from 'manuals'

        // parameters:
        title = 'My Manuals'        // the HTML page title; not set by default

        theme = 'simplex'           // theme to use; detaults to 'united';
                                    // check supported themes at http://strapdownjs.com/

        // advanced parameters:
        version = '0.2'             // the version of strapdown.js; defaults to '0.2'

        encoding = 'utf-8'          // the encoding that will be used in HTML tag <meta>;
                                    // defaults to 'utf-8'

        templateFile = file(...)    // to redefine template file;
                                    // check default template for inspiration
    }

If not overridden the [default template](src/main/resources/com/github/gradle-plugins/strapdownjs/default.html) is used.

## How to develop
Execute `gradle idea` and use IntelliJ IDEA with generated project file.

Alternatively, do build using Gradle wrapper (`./gradlew` for *nix, or `gradlew.bat` for Windows) or use locally installed Gradle 1.6.

## License
See [LICENSE](LICENSE) file.
