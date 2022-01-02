package net.webspite.pdf.ast

import com.lowagie.text.Document
import com.lowagie.text.PageSize
import com.lowagie.text.pdf.PdfWriter
import net.webspite.pdf.model.DrawContext
import java.io.OutputStream


class Document(content: MutableList<Page> = mutableListOf()) : NestedContent(content as MutableList<Content>) {

    fun write(out: OutputStream){
        val ctx = DrawContext()
        ctx.document = Document(PageSize.A4)
        PdfWriter.getInstance(ctx.document, out)

        ctx.document?.open()
        draw(ctx)
        ctx.document?.close()
    }
    override fun draw(ctx: DrawContext) {
       this.drawChildren(ctx)
    }
}