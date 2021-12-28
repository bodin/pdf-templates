package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext
import org.apache.pdfbox.pdmodel.PDDocument
import kotlin.math.max

class Document(content: MutableList<Page> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun calculate(ctx: DrawContext) {

    }
    override fun draw(ctx: DrawContext): Float {
        this.x = ctx.margin
        this.y = ctx.height - ctx.margin
        this.widthPx = ctx.width - 2 * ctx.margin

        ctx.document = PDDocument()

        var myY = this.y
        this.content.forEach {
            it.x = this.x
            it.widthPx = this.widthPx
            it.y = myY
            myY = it.draw(ctx)
        }

        ctx.document?.save("./build/out.pdf")
        ctx.document?.close()
        return myY
    }
}