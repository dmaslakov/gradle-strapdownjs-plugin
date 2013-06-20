# Gradle plug-in for Strapdown.js

> IN DEVELOPMENT

The plug-in for [Gradle](http://www.gradle.org/) producing HTML files for use along with [Strapdown.js](http://strapdownjs.com/).

## How to use

A snippet of gradle build script below will make usage clear. It will copy all files with extension `.md` or `.mdown` or `.markdown` into target directory with converting it into HTML and changing file extension to `.html`. All other files will be copied without any changes.

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

If not redefined through `templateFile`, the [default template](src/main/resources/com/github/gradle-plugins/strapdownjs/default.html) is used.

## How to develop
### Edit and build
Execute `gradle idea` and use IntelliJ IDEA with generated project file.

Alternatively, do build using Gradle wrapper (`./gradlew` for *nix, or `gradlew.bat` for Windows) or use locally installed Gradle 1.6.

### Code style
1. Use tabs: one tab for indent; two tabs for statement continuation.
2. Use opening brace `{` on new line for class and method, but on same line in other cases.

## License
See [LICENSE](LICENSE) file.
