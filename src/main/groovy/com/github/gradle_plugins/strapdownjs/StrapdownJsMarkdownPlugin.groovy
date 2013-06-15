package com.github.gradle_plugins.strapdownjs

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin

public class StrapdownJsMarkdownPlugin implements Plugin<Project>
{
	@Override
	void apply(Project project)
	{
		project.plugins.apply(BasePlugin)

		project.task('strapdownjs', type: StrapdownJsTask)
	}
}
