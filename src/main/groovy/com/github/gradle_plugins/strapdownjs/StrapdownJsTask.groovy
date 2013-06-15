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
	String htmlTitle

	@Input
	@Optional
	String htmlPrefix

	@Input
	@Optional
	String htmlSuffix

	public StrapdownJsTask()
	{
		group = 'Documentation'
		description = 'Convert markdown files into HTML files for http://strapdownjs.com/.'

		conventionMapping.with {
			map 'templateFile', { FileUtils.toFile(this.getClass().getResource('/com/github/gradle-plugins/strapdownjs/default.html')) }
		}

		include('**/*.md', '**/*.markdown').configure {
			filter(StrapdownJsFilter, template: getTemplateFile().text)
		}
		rename(~/(.*)\.(md|markdown)/, '$1.html')
	}
}
