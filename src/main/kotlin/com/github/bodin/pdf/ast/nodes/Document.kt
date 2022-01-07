package com.github.bodin.pdf.ast.nodes

import com.github.bodin.pdf.api.ResourceLoader
import com.github.bodin.pdf.ast.Content
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.NestedContent
import com.lowagie.text.*
import com.lowagie.text.Document
import com.lowagie.text.pdf.PdfWriter
import java.io.OutputStream

class Document(content: MutableList<Page> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    var pageSize: Rectangle? = null

    fun write(out: OutputStream){
        val ctx = DrawContext(ResourceLoader.Default)
        ctx.document = Document(pageSize?:PageSize.A4)
        //this is needed before document.open
        ctx.applyMargins(this)

        //writer should be closed when document is closed
        val writer = PdfWriter.getInstance(ctx.document, out)
        //since we do not own the stream, do not close it
        writer.isCloseStream = false

        ctx.document?.open()
        ctx.outline = writer?.directContent?.rootOutline
        draw(ctx)
        ctx.document?.close()
    }
    override fun draw(ctx: DrawContext) {
       this.drawChildren(ctx)
    }
}