package net.webspite.pdf.ast

import com.lowagie.text.Phrase
import com.lowagie.text.pdf.PdfPCell
import net.webspite.pdf.model.DrawContext

class TextCell(content: String = ""): ContentCell(content) {
    override fun draw(ctx: DrawContext) {
        val cell = PdfPCell(Phrase(this.content))

        this.styleCell(cell)

        ctx.tables.peek().addCell(cell)
    }
}