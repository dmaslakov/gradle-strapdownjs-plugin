package com.github.gradle_plugins.strapdownjs

import groovy.text.GStringTemplateEngine

/**
 * Creates HTML that wraps markdown.
 */
public class StrapdownJsFilter extends FilterReader
{
	private Reader mdReader

	String template
	void setTemplate(String tmpl)
	{
		template = tmpl
		def w = new PipedWriter(super.in as PipedReader)
		def pp = [mdContent: mdReader.text, title: '']
		new GStringTemplateEngine().createTemplate(tmpl).make(pp).writeTo(w)
		w.close()
	}

	public StrapdownJsFilter(Reader input)
	{
		super(new PipedReader())
		mdReader = input
	}
}
