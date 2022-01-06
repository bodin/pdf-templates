package com.github.bodin.pdf.ast.nodes

import com.github.bodin.pdf.ast.ContentCell
import com.github.bodin.pdf.ast.DrawContext
import com.lowagie.text.Image
import com.lowagie.text.pdf.PdfPCell

class ImageCell(content: String = ""): ContentCell(content) {
    override fun draw(ctx: DrawContext) {
        val i = Image.getInstance(ctx.loader.load(this.content))

        this.styleCell(i)
        var cell = PdfPCell(i)
        this.styleCell(cell)
        ctx.tables.peek().addCell(cell)
    }
}