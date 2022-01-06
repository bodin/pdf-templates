package com.github.bodin.pdf.ast.nodes

import com.github.bodin.pdf.ast.Content
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.NestedContent
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
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
        table.widthPercentage = this.width?:100f
        ctx.tables.push(table)

        this.content.forEach { (it as Row).expectedCells = cells }
        this.drawChildren(ctx)

        ctx.tables.pop()

        this.styleCell(table)

        if(ctx.tables.isEmpty()) {
            ctx.document?.add(table)
        } else {
            val cell = PdfPCell(table)
            this.styleCell(cell)

            cell.paddingTop = 0f
            cell.paddingBottom = 0f
            cell.paddingLeft = 0f
            cell.paddingRight = 0f
            ctx.tables.peek()?.addCell(cell)
        }
    }
}