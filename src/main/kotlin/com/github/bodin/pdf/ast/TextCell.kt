package com.github.bodin.pdf.ast

import com.lowagie.text.Phrase
import com.lowagie.text.pdf.PdfPCell

class TextCell(content: String = ""): ContentCell(content) {
    override fun draw(ctx: DrawContext) {
        val cell = PdfPCell(Phrase(this.content, ctx.getFont(this)))

        this.styleCell(cell)

        ctx.tables.peek().addCell(cell)
    }
}