package net.webspite.pdf.ast

import com.lowagie.text.Image
import net.webspite.pdf.model.DrawContext
import java.net.URI

class ImageCell(content: String = ""): ContentCell(content) {
    override fun draw(ctx: DrawContext) {
        val i = Image.getInstance(URI.create(this.content).toURL())
        this.styleCell(i)
        ctx.tables.peek().addCell(i)
    }
}