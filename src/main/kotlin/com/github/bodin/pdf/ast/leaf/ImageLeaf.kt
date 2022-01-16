package com.github.bodin.pdf.ast.leaf

import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.LeafNode
import com.github.bodin.pdf.ast.DrawContext
import com.lowagie.text.Image
import com.lowagie.text.pdf.PdfPCell

class ImageLeaf(parent: Node, content: String = ""): LeafNode(parent, content) {
    override fun draw(ctx: DrawContext) {
        val i = Image.getInstance(ctx.loader.load(this.content))

        ctx.styleCell(i, this.attributes)
        var cell = PdfPCell(i)
        ctx.styleCell(cell, this.attributes)

        if(ctx.tables.isEmpty()){
            ctx.document?.add(i)
        }else{
            ctx.tables.peek().addCell(cell)
        }
    }
}