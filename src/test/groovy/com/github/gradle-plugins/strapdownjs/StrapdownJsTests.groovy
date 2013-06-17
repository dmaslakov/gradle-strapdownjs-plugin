import com.github.gradle_plugins.strapdownjs.StrapdownJsMarkdownPlugin
import org.apache.commons.io.FileUtils
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class StrapdownJsTests extends Specification
{
	def "Test simple.md"()
	{
		def p = ProjectBuilder.builder().build()

		expect:
		p.tasks.findByName('strapdownjs') == null

		when:
		p.apply plugin: StrapdownJsMarkdownPlugin
		Task t = p.tasks.findByName('strapdownjs')
		t.configure {
			destinationDir = p.buildDir
			title = 'Simple Book Test'
			theme = 'simplex'
			from FileUtils.toFile(this.getClass().getResource('/simple'))
			include '**/*.png'
		}
		t.execute()
		def out_file = new File(t.destinationDir, 'simple.html')

		then:
		out_file.exists()
		// TODO check content is correct
	}
}
