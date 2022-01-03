package com.github.bodin.pdf.pre.handlebars

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import com.github.bodin.pdf.api.TemplateProcessor
import java.io.*

class HandlebarsProcessor: TemplateProcessor {

    override fun process(ctx: Any?, ins: InputStream): InputStream {
        val handlebars = Handlebars()

        val str = ins.bufferedReader().use(BufferedReader::readText)
        val template: Template = handlebars.compileInline(str)

        return template.apply(ctx).byteInputStream()
    }
}