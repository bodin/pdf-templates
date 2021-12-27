package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext
import org.apache.pdfbox.pdmodel.PDDocument

class Document(content: MutableList<Page> = mutableListOf()) : Content<MutableList<Page>>(content) {
    override fun draw(ctx: DrawContext) {
        ctx.document = PDDocument()
        this.content.forEach { it.draw(ctx) }
        ctx.document?.save("./out.pdf")
        ctx.document?.close()
    }
}