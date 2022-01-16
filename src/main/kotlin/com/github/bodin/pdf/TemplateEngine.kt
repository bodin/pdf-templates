package com.github.bodin.pdf

import com.github.bodin.pdf.api.ResourceLoader
import com.github.bodin.pdf.api.TemplateProcessor
import com.github.bodin.pdf.api.TemplateSource
import com.github.bodin.pdf.ast.Attributes
import com.github.bodin.pdf.parser.XMLParser
import java.io.OutputStream

class TemplateEngine(
    val processor: TemplateProcessor = TemplateProcessor.NoOp,
    val loader: ResourceLoader = ResourceLoader.Default
) {

    fun executeInline(template: String, out: OutputStream){
        this.executeInline(null, template, out)
    }
    fun executeInline(ctx:Any? = null, template: String, out: OutputStream){
        this.executeInline(ctx, template.toByteArray(), out)
    }

    fun executeInline(template: ByteArray, out: OutputStream){
        this.executeInline(null, template, out)
    }
    fun executeInline(ctx:Any? = null, template: ByteArray, out: OutputStream){
        this.execute(ctx, TemplateSource.Inline(template), out)
    }

    fun executeFile(file: String, out: OutputStream){
        this.execute(null, TemplateSource.URL(loader.load(file)), out)
    }
    fun executeFile(ctx:Any? = null, file: String, out: OutputStream){
        this.execute(ctx, TemplateSource.URL(loader.load(file)), out)
    }

    fun execute(source: TemplateSource, os: OutputStream){
        this.execute(null, source, os)
    }
    fun execute(ctx:Any? = null, source: TemplateSource, out: OutputStream){
        source.inputStream().use {fin ->
            processor.process(ctx, fin).use { pin ->
                var doc = XMLParser().parse(pin)
                Attributes.Default().cascade(doc.attributes)
                doc.write(out)
            }
        }
    }
}