package com.github.bodin.pdf.ast.node

import com.github.bodin.pdf.ast.*
import com.lowagie.text.Paragraph
import com.lowagie.text.pdf.PdfPCell

class TextNode(private val parent: Node, content: MutableList<FormatNode> = mutableListOf())
    : InteriorNode<FormatNode>(content) {

    override fun getParent(): Node? = parent
    override fun draw(ctx: DrawContext) {
        var p = Paragraph()
        ctx.styleCell(p, this.attributes)

        val cell = PdfPCell()
        cell.addElement(p)
        DrawContext.styleCell(cell, this.attributes)

        ctx.paragraph = p
        this.drawChildren(ctx)
        ctx.paragraph = null

        this.attributes.bookmark?.let { ctx.bookmark(p, it) }
        if(ctx.tables.isEmpty()){
            ctx.document.add(p)
        }else{
            ctx.tables.peek().addCell(cell)
        }
    }
}