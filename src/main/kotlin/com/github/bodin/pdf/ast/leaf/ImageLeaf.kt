package com.github.bodin.pdf.ast.leaf

import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.LeafNode
import com.github.bodin.pdf.ast.DrawContext
import com.lowagie.text.Image
import com.lowagie.text.pdf.PdfPCell

class ImageLeaf(parent: Node, content: String = ""): LeafNode(parent, content) {
    override fun draw(ctx: DrawContext) {
        val i = Image.getInstance(ctx.loader.load(this.content))

        DrawContext.styleCell(i, this.attributes)
        //this does not work in header - but does allow width / height to be set
        var cell = PdfPCell(i)

        if(ctx.inHeader) {
            // This works in header, but does not allow both width and height to be set
            cell = PdfPCell()
            cell.addElement(i)
        }

        DrawContext.styleCell(cell, this.attributes)

        if(ctx.tables.isEmpty()){
            ctx.document.add(i)
        }else{
            ctx.tables.peek().addCell(cell)
        }
    }
}