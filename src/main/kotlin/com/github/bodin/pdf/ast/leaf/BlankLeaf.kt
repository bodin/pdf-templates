package com.github.bodin.pdf.ast.leaf

import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.LeafNode
import com.github.bodin.pdf.ast.DrawContext
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable

class BlankLeaf(parent: Node, content: String = "")
    : LeafNode(parent, content) {
    override fun draw(ctx: DrawContext) {
        val cell = PdfPCell()
        ctx.styleCell(cell, this.attributes)
        attributes.height?.let { cell.minimumHeight = it }

        if (ctx.tables.isEmpty()) {
            val table = PdfPTable(1)
            table.addCell(cell)
            ctx.document?.add(table)
        } else {
            ctx.tables.peek().addCell(cell)
        }
    }
}