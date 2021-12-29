package net.webspite.pdf.ast

import be.quodlibet.boxable.image.Image
import net.webspite.pdf.model.DrawContext
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import java.awt.image.BufferedImage
import java.net.URI
import javax.imageio.ImageIO

class ImageCell(content: String = ""): ContentCell(content) {
    override fun draw(ctx: DrawContext) : Float{

        var image: BufferedImage = ImageIO.read(URI.create(this.content).toURL())

        var cell = ctx.tables.peek().rows.last()
            .createImageCell(this.widthPct, Image(image))

        if (this.alignH != null) cell.align = this.alignH
        if (this.alignV != null) cell.valign = this.alignV
        if (this.colorFill != null) cell.fillColor = this.colorFill
        if (this.colorText != null) cell.textColor = this.colorText
        if (this.fontSize > 0f) cell.fontSize = this.fontSize

        return this.y - cell.height
    }
}