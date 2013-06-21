package com.github.gradle_plugins.strapdownjs

import org.gradle.api.file.FileCopyDetails
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional

/**
 * Converts a set of Markdown texts into HTML using approach defined at http://strapdownjs.com/.
 *
 * Markdown files must have extension one of .md, .mdown or .markdown. They will be converted into HTML files with extension .html.
 * Other files from source directory will be copied without changes.
 */
public class StrapdownJsTask extends Copy
{
	/**
	 * String representing HTML template.
	 * Template must match syntax supported by GStringTemplateEngine.
	 *
	 * Supported parameters to use in template file:
	 * <ul>
	 * <li>title - the title of generated HTML document</li>
	 * <li>theme - the theme to use with Strapdown.js, see http://strapdownjs.com/ for list of supported themes</li>
	 * <li>version - the version of Strapdown.js</li>
	 * <li>encoding - encoding to use within tag <meta> in generated HTML</li>
	 * </ul>
	 */
	@Input
	@Optional
	String template

	/**
	 * The title of generated HTML document. Default: not set.
	 */
	@Input
	@Optional
	String title

	/**
	 * The theme to use with Strapdown.js, see http://strapdownjs.com/ for list of supported themes. Default: united.
	 */
	@Input
	@Optional
	String theme

	/**
	 * The version of Strapdown.js. Default: 0.2.
	 */
	@Input
	@Optional
	String version

	/**
	 * Encoding to use within tag <meta> in generated HTML. Default: utf-8.
	 */
	@Input
	@Optional
	String encoding

	protected final Map<String, String> predefinedTemplates = [
		default: '/com/github/gradle_plugins/strapdownjs/default.html'
	].asImmutable()

	public StrapdownJsTask()
	{
		group = 'Documentation'
		description = 'Convert markdown files into HTML files for use with http://strapdownjs.com/.'

		conventionMapping.with {
			map 'template', { this.getClass().getResourceAsStream(this.predefinedTemplates.default).text }
			map 'title', { '' }
			map 'theme', { 'united' }
			map 'version', { '0.2' }
			map 'encoding', { 'utf-8' }
		}

		eachFile { FileCopyDetails fcd ->
			if (!fcd.directory) {
				def m = fcd.name =~ /^(.*)\.(?:md|mdown|markdown)$/
				if (m) { // go with only Markdown files
					logger.info('Convert Markdown file into HTML: {}', fcd.getPath())
					// change extension to html
					fcd.name = m[0][1] + '.html'
					// filter content
					fcd.filter(StrapdownJsFilter,
						template:   this.getTemplate(),
						title:      this.getTitle(),
						theme:      this.getTheme(),
						version:    this.getVersion(),
						encoding:   this.getEncoding()
					)
				}
			}
		} // eachFile
	}
}
