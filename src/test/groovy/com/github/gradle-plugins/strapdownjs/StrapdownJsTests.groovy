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
			def origDir = FileUtils.toFile(this.getClass().getResource('/simple'))

		expect: /task 'strapdownjs' not exists/
			p.tasks.findByName('strapdownjs') == null


		when: /apply plugin/
			p.apply plugin: StrapdownJsMarkdownPlugin
			Task t = p.tasks.findByName('strapdownjs')
		then: /task 'strapdownjs' exists/
			t != null


		when: /configure and execute task 'strapdownjs'/
			t.configure {
					destinationDir = p.buildDir
					title = 'Book of Simple Tests'
					theme = 'simplex'
					from origDir
			}
			t.execute()
		then: "Target HTML file exists and with expected content"
			def out_file = new File(t.destinationDir, 'simple.html')
			out_file.exists()
			def expected_content = FileUtils.toFile(StrapdownJsTask.getClass().getResource('/com/github/gradle_plugins/strapdownjs/default.html'))
					.text
					.replace('${theme}', t.theme)
					.replace('${version}', t.version)
					.replace('${encoding}', t.encoding)
					.replaceFirst(~/\<%.*?%\>/, "<title>${t.title}</title>")
					.replace('${mdContent}', new File(origDir, 'simple.md').text)
					.replaceAll(~/\r\n|\n\r|\n|\r/, '\n')
			expected_content == out_file.text.replaceAll(~/\r\n|\n\r|\n|\r/, '\n')
		and: "accompanying image file exists and content not changed"
			def img_file = new File(t.destinationDir, 'simple.png')
			img_file.exists()
			img_file.bytes == new File(origDir, 'simple.png').bytes
	}
}
