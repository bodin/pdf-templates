package com.github.bodin.pdf

import com.github.bodin.pdf.api.ResourceLoader
import com.github.bodin.pdf.api.TemplateProcessor
import com.github.bodin.pdf.api.TemplateSource
import com.github.bodin.pdf.parser.XMLParser
import java.io.OutputStream

class TemplateEngine(
    val processor: TemplateProcessor = TemplateProcessor.NoOp,
    val loader: ResourceLoader = ResourceLoader.Default
) {

    fun executeInline(ctx:Any? = null, template: String, out: OutputStream){
        this.executeInline(null, template.toByteArray(), out)
    }
    fun executeInline(ctx:Any? = null, template: ByteArray, out: OutputStream){
        this.execute(ctx, TemplateSource.Inline(template), out)
    }

    fun executeFile(file: String, out: OutputStream){
        this.execute(null, loader.load(file), out)
    }
    fun executeFile(ctx:Any? = null, file: String, out: OutputStream){
        this.execute(ctx, loader.load(file), out)
    }

    fun execute(source: TemplateSource, os: OutputStream){
        this.execute(null, source, os)
    }
    fun execute(ctx:Any? = null, source: TemplateSource, out: OutputStream){
        source.inputStream().use {fin ->
            processor.process(ctx, fin).use { pin ->
                XMLParser().parse(pin).write(out)
            }
        }
    }
}