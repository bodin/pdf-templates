package net.webspite.pdf.ast

import com.lowagie.text.Image
import net.webspite.pdf.model.DrawContext
import java.net.URI

class ImageCell(content: String = ""): ContentCell(content) {
    override fun draw(ctx: DrawContext) {
        val i = Image.getInstance(URI.create(this.content).toURL())
        ctx.tables.peek().addCell(i)

        //if (this.alignH != null) cell.align = this.alignH
        //if (this.alignV != null) cell.valign = this.alignV
        //if (this.colorFill != null) cell.fillColor = this.colorFill
        //if (this.colorText != null) cell.textColor = this.colorText
        //if (this.fontSize > 0f) cell.fontSize = this.fontSize
    }
}