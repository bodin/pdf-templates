package net.webspite.pdf.ast

import com.lowagie.text.pdf.PdfPCell
import net.webspite.pdf.model.DrawContext

class Row(content: MutableList<ContentCell> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    var expectedCells: Int = 0

    override fun cells(): Int {
        return this.content.sumBy { it.cells() }
    }
    override fun draw(ctx: DrawContext){
        this.drawChildren(ctx)
        var needed = this.expectedCells - this.cells()
        if(needed > 0){
            val cell = PdfPCell()
            cell.colspan = needed
            ctx.tables.peek().addCell(cell)
        }
    }
}