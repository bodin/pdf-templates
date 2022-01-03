package net.webspite.pdf.ast

import com.lowagie.text.pdf.PdfPTable
import net.webspite.pdf.model.DrawContext
import org.slf4j.LoggerFactory

class Table(content: MutableList<Row> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    var layout: FloatArray? = null
    var log = LoggerFactory.getLogger(this.javaClass)

    override fun draw(ctx: DrawContext) {
        val cells = this.content.maxOf { it.cells() }


        if(layout == null){
            layout = FloatArray(cells)
            layout?.fill(100f/(cells * 100f))

        }else if(layout?.size != cells){
            log.error("Layout on table has wrong number of cells - ignoring")
        }

        val table = PdfPTable(layout)

        ctx.tables.push(table)

        this.content.forEach { (it as Row).expectedCells = cells }
        this.drawChildren(ctx)

        ctx.tables.pop()
        if(ctx.tables.isEmpty()) {
            ctx.document?.add(table)
        } else {
            ctx.tables.peek()?.addCell(table)
        }
    }
}