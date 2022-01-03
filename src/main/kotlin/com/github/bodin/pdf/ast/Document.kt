package com.github.bodin.pdf.ast

import com.lowagie.text.Document
import com.lowagie.text.PageSize
import com.lowagie.text.pdf.PdfWriter
import java.io.OutputStream


class Document(content: MutableList<Page> = mutableListOf()) : NestedContent(content as MutableList<Content>) {

    fun write(out: OutputStream){
        val ctx = DrawContext()
        ctx.document = Document(PageSize.A4)

        //writer should be closed when document is closed
        val writer = PdfWriter.getInstance(ctx.document, out)
        //since we do not own the stream, do not close it
        writer.isCloseStream = false

        ctx.document?.open()
        draw(ctx)
        ctx.document?.close()
    }
    override fun draw(ctx: DrawContext) {
       this.drawChildren(ctx)
    }
}