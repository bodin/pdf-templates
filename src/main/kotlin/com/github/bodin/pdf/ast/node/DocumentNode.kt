package com.github.bodin.pdf.ast.node

import com.github.bodin.pdf.CustomPageEvent
import com.github.bodin.pdf.api.ResourceLoader
import com.github.bodin.pdf.ast.Attributes
import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.InteriorNode
import com.lowagie.text.Document
import com.lowagie.text.PageSize
import com.lowagie.text.pdf.PdfWriter
import java.io.OutputStream

class DocumentNode(content: MutableList<PageNode> = mutableListOf())
    : InteriorNode<PageNode>(content){

    init{
        Attributes.Default().cascade(this.attributes)
    }
    var header: HeaderOrFooterNode? = null
    var footer: HeaderOrFooterNode? = null

    fun getPage(cnt: Int): PageNode?{
        val pages = this.content.filterIsInstance<PageNode>()
        if(pages.size < cnt) return null
        return pages[cnt-1]
    }

    override fun getParent(): Node? = null

    fun write(out: OutputStream){
        val ctx = DrawContext(Document(attributes.pageSize?:PageSize.A4), ResourceLoader.Default)

        //this is needed before document.open
        ctx.applyMargins(ctx, this)

        //writer should be closed when document is closed
        ctx.writer = PdfWriter.getInstance(ctx.document, out)
        //since we do not own the stream, do not close it
        ctx.writer?.isCloseStream = false
        ctx.writer?.pageEvent = CustomPageEvent(this)

        ctx.document.open()
        ctx.outline = ctx.writer?.directContent?.rootOutline
        draw(ctx)
        ctx.document.close()
    }
    override fun draw(ctx: DrawContext) {
       this.drawChildren(ctx)
    }
}