package com.github.gradle_plugins.strapdownjs

import org.apache.commons.io.FileUtils
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional

public class StrapdownJsTask extends Copy
{
	@InputFile
	File templateFile

	@Input
	@Optional
	String title

	@Input
	@Optional
	String theme

	@Input
	@Optional
	String version

	public StrapdownJsTask()
	{
		group = 'Documentation'
		description = 'Convert markdown files into HTML files for http://strapdownjs.com/.'

		conventionMapping.with {
			map 'templateFile', { FileUtils.toFile(this.getClass().getResource('/com/github/gradle-plugins/strapdownjs/default.html')) }
			map 'title', { '' }
			map 'theme', { 'united' }
			map 'version', { '0.2' }
		}

		//with project.copySpec
		configure {
			include('**/*.md', '**/*.markdown')
			rename(~/(.*)\.(md|markdown)/, '$1.html')
			filter(StrapdownJsFilter,
				// parameters are wrapped with closures to postpone real evaluation till real execution
				template:   { this.getTemplateFile().text },
				title:      { this.getTitle() },
				theme:      { this.getTheme() },
				version:    { this.getVersion() }
			)
		}
	}
}
