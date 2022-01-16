package com.github.bodin.pdf.ast.node

import com.github.bodin.pdf.api.ResourceLoader
import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.InteriorNode
import com.lowagie.text.Document
import com.lowagie.text.PageSize
import com.lowagie.text.pdf.PdfWriter
import java.io.OutputStream

class DocumentNode(content: MutableList<PageNode> = mutableListOf())
    : InteriorNode<PageNode>(content) {

    override fun getParent(): Node? = null

    fun write(out: OutputStream){
        val ctx = DrawContext(ResourceLoader.Default)
        ctx.document = Document(attributes.pageSize?:PageSize.A4)
        //this is needed before document.open
        ctx.applyMargins(this)

        //writer should be closed when document is closed
        val writer = PdfWriter.getInstance(ctx.document, out)
        //since we do not own the stream, do not close it
        writer.isCloseStream = false

        ctx.document?.open()
        ctx.outline = writer?.directContent?.rootOutline
        draw(ctx)
        ctx.document?.close()
    }
    override fun draw(ctx: DrawContext) {
       this.drawChildren(ctx)
    }
}