package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject




class ImageContent(var content: String): Content() {
    override fun draw(ctx: DrawContext) : Float{
        var cell = ctx.tables.peek().rows.last().cells.last();

        val pdImage = PDImageXObject.createFromFile("C:/logo.png", ctx.document)


        if(this.alignH != null) cell.align = this.alignH
        if(this.alignV != null) cell.valign = this.alignV
        if(this.colorFill != null) cell.fillColor = this.colorFill
        if(this.colorText != null) cell.textColor = this.colorText
        if(this.fontSize > 0f) cell.fontSize = this.fontSize

        return ctx.tables.peek().rows.last().cells.last().height
    }
}