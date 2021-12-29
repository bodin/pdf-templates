package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class TextCell(content: String = ""): ContentCell(content) {
    override fun draw(ctx: DrawContext) : Float{
        var cell = ctx.tables.peek().rows.last().createCell(this.widthPct, this.content)

        if(this.alignH != null) cell.align = this.alignH
        if(this.alignV != null) cell.valign = this.alignV
        if(this.colorFill != null) cell.fillColor = this.colorFill
        if(this.colorText != null) cell.textColor = this.colorText
        if(this.fontSize > 0f) cell.fontSize = this.fontSize

        return this.y - cell.height
    }
}