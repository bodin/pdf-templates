package com.github.bodin.pdf.ast.nodes

import com.github.bodin.pdf.ast.*
import com.lowagie.text.Chunk
import com.lowagie.text.Paragraph
import com.lowagie.text.Phrase
import com.lowagie.text.pdf.PdfPCell

class TextCell(content: MutableList<TextChunk> = mutableListOf())
    : NestedContent(content as MutableList<Content>), CharacterAware {
    override fun draw(ctx: DrawContext) {
        var p = Paragraph()
        p.font = ctx.getFont(this)
        p.setLeading(0f, 1.35f)
        ctx.paragraph = p
        this.drawChildren(ctx)
        ctx.paragraph = null

        val cell = PdfPCell(p)
        this.styleCell(cell)

        this.bookmark?.let { ctx.bookmark(p, it) }
        ctx.tables.peek().addCell(cell)
    }
}