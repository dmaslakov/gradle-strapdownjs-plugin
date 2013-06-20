package com.github.gradle_plugins.strapdownjs

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin

/**
 * Allows make translate Markdown documents into HTML in conjunction with use of http://strapdownjs.com/ (see details there).
 */
public class StrapdownJsMarkdownPlugin implements Plugin<Project>
{
	@Override
	void apply(Project project)
	{
		project.plugins.apply(BasePlugin)

		project.task('strapdownjs', type: StrapdownJsTask)
	}
}
