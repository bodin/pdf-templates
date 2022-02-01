package com.github.bodin.pdf.ast.node

import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.InteriorNode
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import org.slf4j.LoggerFactory

class TableNode(private val parent: Node, content: MutableList<RowNode> = mutableListOf())
    : InteriorNode<RowNode>(content) {
    var log = LoggerFactory.getLogger(this.javaClass)

    override fun getParent(): Node? = parent

    override fun draw(ctx: DrawContext) {
        val cells = this.content.maxOf { it.cells() }

        var layout = attributes.layout
        if(layout == null){
            layout = FloatArray(cells)
            layout.fill(100f/(cells * 100f))
        }else if(layout.size != cells){
            log.error("Layout on table has wrong number of cells - ignoring")
        }

        val table = PdfPTable(layout)
        table.widthPercentage = this.attributes.width?:100f
        ctx.tables.push(table)

        this.drawChildren(ctx)

        ctx.tables.pop()

        DrawContext.styleCell(table, this.attributes)

        if(ctx.tables.isEmpty()) {
            ctx.document.add(table)
        } else {
            val cell = PdfPCell(table)
            DrawContext.styleCell(cell, this.attributes)

            cell.paddingTop = 0f
            cell.paddingBottom = 0f
            cell.paddingLeft = 0f
            cell.paddingRight = 0f
            ctx.tables.peek()?.addCell(cell)
        }
    }
}