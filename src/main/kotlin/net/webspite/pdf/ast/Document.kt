package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext
import org.apache.pdfbox.pdmodel.PDDocument
import java.io.OutputStream

class Document(content: MutableList<Page> = mutableListOf()) : NestedContent(content as MutableList<Content>) {

    fun write(out: OutputStream){
        var ctx = DrawContext()
        ctx.document = PDDocument()

        draw(ctx)

        ctx.document?.save(out)
        ctx.document?.close()
    }
    override fun draw(ctx: DrawContext): Float {
        this.x = ctx.margin
        this.y = ctx.height - ctx.margin
        this.widthPt = ctx.width - 2 * ctx.margin

        var myY = this.y
        this.content.forEach {
            it.x = this.x
            it.widthPt = this.widthPt
            it.y = myY
            this.copyTo(it)
            myY = it.draw(ctx)
        }

        return myY
    }
}