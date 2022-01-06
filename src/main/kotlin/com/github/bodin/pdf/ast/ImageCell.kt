package com.github.bodin.pdf.ast

import com.lowagie.text.Image
import com.lowagie.text.pdf.PdfPCell
import java.net.URI

class ImageCell(content: String = ""): ContentCell(content) {
    override fun draw(ctx: DrawContext) {
        val i = Image.getInstance(ctx.loader.load(this.content))

        this.styleCell(i)
        var cell = PdfPCell(i)
        this.styleCell(cell)
        ctx.tables.peek().addCell(cell)
    }
}