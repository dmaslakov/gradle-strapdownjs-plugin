package com.github.gradle_plugins.strapdownjs

import groovy.text.GStringTemplateEngine
import groovy.util.logging.Slf4j

/**
 * Creates HTML code around input Markdown text.
 */
public class StrapdownJsFilter extends FilterReader
{
	public StrapdownJsFilter(Reader mdReader)
	{
		// inject own Reader that will add surrounding HTML code for input Markdown text.
		super(new TemplateExpanderReader(mdReader))
	}

	// all parameters are proxied down into own Reader implementation:

	void setTemplate(tmpl) { (super.in as TemplateExpanderReader).template = tmpl }
	def getTemplate() { (super.in as TemplateExpanderReader).template }

	void setTitle(ttl) { (super.in as TemplateExpanderReader).title = ttl }
	def getTitle() { (super.in as TemplateExpanderReader).title }

	void setTheme(thm) { (super.in as TemplateExpanderReader).theme = thm }
	def getTheme() { (super.in as TemplateExpanderReader).theme }

	void setVersion(v) { (super.in as TemplateExpanderReader).version = v }
	def getVersion() { (super.in as TemplateExpanderReader).version }

	void setEncoding(enc) { (super.in as TemplateExpanderReader).encoding = enc }
	def getEncoding() { (super.in as TemplateExpanderReader).encoding }

	@Slf4j
	static class TemplateExpanderReader extends Reader
	{
		private Reader mdReader
		private StringReader htmlReader

		String template
		String title
		String theme
		String version
		String encoding

		public TemplateExpanderReader(Reader mdReader)
		{
			this.mdReader = mdReader
		}

		@Override
		int read(char[] cbuf, int off, int len) throws IOException
		{
			if (htmlReader == null) {
				// postponed evaluation of template, right before reading is started
				def vars = [
						mdContent:  mdReader.text,
						title:      this.title,
						// TODO need escape
						//title:      StringEscapeUtils.escapeHtml(this.title),
						theme:      this.theme,
						version:    this.version,
						encoding:   this.encoding
				]
				log.debug('Apply variables to template: {}', vars.dump())
				def content = new GStringTemplateEngine().createTemplate(this.template).make(vars).toString()
				htmlReader = new StringReader(content)
			}
			return htmlReader.read(cbuf, off, len)
		}

		@Override
		void close() throws IOException
		{
			if (htmlReader != null)
				htmlReader.close()
		}
	}
}
