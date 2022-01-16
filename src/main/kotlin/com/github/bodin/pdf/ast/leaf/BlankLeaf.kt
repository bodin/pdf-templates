package com.github.bodin.pdf.ast.leaf

import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.LeafNode
import com.github.bodin.pdf.ast.DrawContext
import com.lowagie.text.pdf.PdfPCell

class BlankLeaf(parent: Node, content: String = ""): LeafNode(parent, content) {
    override fun draw(ctx: DrawContext) {
        val cell = PdfPCell()
        ctx.styleCell(cell, this.attributes)
        ctx.tables.peek().addCell(cell)
    }
}