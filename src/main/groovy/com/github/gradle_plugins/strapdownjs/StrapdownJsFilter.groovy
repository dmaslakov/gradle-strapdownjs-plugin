package com.github.gradle_plugins.strapdownjs

import groovy.text.GStringTemplateEngine
import groovy.util.logging.Slf4j
import org.apache.commons.lang.StringEscapeUtils

/**
 * Creates HTML that wraps markdown.
 */
public class StrapdownJsFilter extends FilterReader
{
	public StrapdownJsFilter(Reader mdReader)
	{
		super(new TemplateExpanderReader(mdReader))
	}

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

		def template
		def title
		def theme
		def version
		def encoding

		public TemplateExpanderReader(Reader mdReader)
		{
			this.mdReader = mdReader
		}

		private static String getText(obj)
		{
			switch (obj)
			{
				case File:      return (obj as File).text
				case Closure:   return obj.call()
			}
			return obj.toString()
		}

		@Override
		int read(char[] cbuf, int off, int len) throws IOException
		{
			if (htmlReader == null) {
				// postponed evaluation of template, right when reading is started
				def vars = [
						mdContent: mdReader.text,
						title: StringEscapeUtils.escapeHtml(getText(this.title)),
						theme: getText(this.theme),
						version: getText(this.version),
						encoding: getText(this.encoding)
				]
				log.debug('Apply variables to template: {}', vars.dump())
				def content = new GStringTemplateEngine().createTemplate(getText(this.template)).make(vars).toString()
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
