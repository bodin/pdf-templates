package com.github.bodin.pdf.ast.nodes

import com.github.bodin.pdf.ast.ContentCell
import com.github.bodin.pdf.ast.DrawContext
import com.lowagie.text.Chunk
import com.lowagie.text.Phrase
import com.lowagie.text.pdf.PdfPCell

class TextCell(content: String = ""): ContentCell(content) {
    override fun draw(ctx: DrawContext) {
        val ch = Chunk(this.content, ctx.getFont(this))
        this.bookmark?.let { ctx.bookmark(ch, it) }
        val cell = PdfPCell(Phrase(ch))

        this.styleCell(cell)

        ctx.tables.peek().addCell(cell)
    }
}