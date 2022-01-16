package com.github.bodin.pdf.ast.leaf

import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.LeafNode
import com.github.bodin.pdf.ast.Node
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable

class SpacerLeaf(parent: Node, content: String = "")
: LeafNode(parent, content) {
    override fun draw(ctx: DrawContext) {
        val table = PdfPTable(1)

        val cell = PdfPCell()
        ctx.styleCell(cell, this.attributes)
        attributes.height?.let { cell.minimumHeight = it }

        table.addCell(cell)
        if (ctx.tables.isEmpty()) {
            ctx.document?.add(table)
        } else {
            ctx.tables.peek().addCell(cell)
        }
    }
}