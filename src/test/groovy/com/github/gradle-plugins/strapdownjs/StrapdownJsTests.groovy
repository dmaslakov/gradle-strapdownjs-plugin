package com.github.gradle_plugins.strapdownjs

import org.apache.commons.io.FileUtils
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class StrapdownJsTests extends Specification
{
	def "Markdown wrapper"()
	{
		given: /an empty project/
		def p = ProjectBuilder.builder().build()

		expect: /task 'strapdownjs' not exists/
		p.tasks.findByName('strapdownjs') == null


		when: /apply plugin/
		p.apply plugin: StrapdownJsMarkdownPlugin
		then: /task 'strapdownjs' exists/
		Task t = p.tasks.findByName('strapdownjs')
		t != null


		when: /configure and execute task 'strapdownjs'/
		t.configure {
			destinationDir = p.buildDir
			title = 'Simple Book Test'
			theme = 'simplex'
			from FileUtils.toFile(this.getClass().getResource('/simple'))
		}
		t.execute()

		then: "Target HTML file exists"
		def out_file = new File(t.destinationDir, 'simple.html')
		out_file.exists()

		and: "accompanying image file exists"
		def img_file = new File(t.destinationDir, 'simple.png')
		img_file.exists()

		// TODO check content is correct
		// TODO add file with possible replacements but with other extension -- it must not be processed
	}
}
