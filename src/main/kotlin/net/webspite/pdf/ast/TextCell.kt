package net.webspite.pdf.ast

import com.lowagie.text.Phrase
import com.lowagie.text.pdf.PdfPCell
import net.webspite.pdf.model.DrawContext

class TextCell(content: String = ""): ContentCell(content) {
    override fun draw(ctx: DrawContext) {
        val cell = PdfPCell(Phrase(this.content))

        //if(this.alignH != null) cell.align = this.alignH
        //if(this.alignV != null) cell.valign = this.alignV
        if(this.colorFill != null) cell.backgroundColor = this.colorFill
        //if(this.colorText != null) cell. = this.colorText
        //if(this.fontSize > 0f) cell.fontSize = this.fontSize

        ctx.tables.peek().addCell(cell)
    }
}