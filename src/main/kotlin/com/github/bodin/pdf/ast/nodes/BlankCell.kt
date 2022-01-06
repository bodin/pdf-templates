package com.github.bodin.pdf.ast.nodes

import com.github.bodin.pdf.ast.ContentCell
import com.github.bodin.pdf.ast.DrawContext
import com.lowagie.text.pdf.PdfPCell

class BlankCell(content: String = ""): ContentCell(content) {
    override fun draw(ctx: DrawContext) {
        val cell = PdfPCell()
        this.styleCell(cell)
        ctx.tables.peek().addCell(cell)
    }
}