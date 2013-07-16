# Gradle plug-in for Strapdown.js

The plug-in for [Gradle](http://www.gradle.org/) producing HTML files for use along with [Strapdown.js](http://strapdownjs.com/).

## How to use

A snippet of gradle build script below will make usage clear. It will copy all files with extension `.md` or `.mdown` or `.markdown` into target directory with converting it into HTML and changing file extension to `.html`. All other files will be copied without any changes.

    buildscript {
        repositories {
            mavenRepo url: uri('http://dl.bintray.com/dmaslakov/gradle-plugins')
        }
        dependencies {
            classpath 'com.github.gradle-plugins:gradle-strapdownjs-plugin:0.1'
        }
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

        template( file(...) )       // redefine template with content of file
        template( '...' )           // or redefine template with string value;
                                    // check the default template for inspiration

        // also custom templates may contain additional properties
        template( '...', prop1:'val1', prop2:'val2' )
    }

If not redefined through `template`, the [default template](src/main/resources/com/github/gradle-plugins/strapdownjs/default.html) is used.

## How to develop
### Edit and build
Execute `gradle idea` and use IntelliJ IDEA with generated project file.

Alternatively, do build using Gradle wrapper (`./gradlew` for *nix, or `gradlew.bat` for Windows) or use locally installed Gradle 1.6.

### Code style
1. Use tabs: one tab for indent; two tabs for statement continuation.
2. Use opening brace `{` on new line for class and method, but on same line in other cases.

## License
See [LICENSE](LICENSE) file.
