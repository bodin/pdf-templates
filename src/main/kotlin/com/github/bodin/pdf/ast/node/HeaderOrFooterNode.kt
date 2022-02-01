package com.github.bodin.pdf.ast.node

import com.github.bodin.pdf.api.ResourceLoader
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.InteriorNode
import com.github.bodin.pdf.ast.Node
import com.lowagie.text.Document
import com.lowagie.text.pdf.PdfPTable
import org.slf4j.LoggerFactory

interface HeaderFooterProvider {
    fun toNode(): Node
    fun getPageHeader(): HeaderOrFooterNode?
    fun getPageFooter(): HeaderOrFooterNode?
}

class HeaderOrFooterNode(private val parent: Node, content: MutableList<Node> = mutableListOf()): InteriorNode<Node>(content) {
    var log = LoggerFactory.getLogger(this.javaClass)

    override fun getParent(): Node? = parent

    fun getTable(d: Document): PdfPTable {
        //TODO - should do this once, not every page
        this.parent.attributes.cascade(this.attributes)

        //TODO - resource loader hardcoded
        val ctx = DrawContext(d, ResourceLoader.Default)
        ctx.inHeader = true

        //TODO - copy / pasted table code below
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
        table.totalWidth = table.widthPercentage * (d.pageSize.width - d.leftMargin() - d.rightMargin()) / 100
        ctx.tables.push(table)

        this.drawChildren(ctx)

        ctx.tables.pop()

        DrawContext.styleCell(table, this.attributes)

        return table
    }

    override fun draw(ctx: DrawContext) = throw Exception("Draw can't be called on headers and footers")
}