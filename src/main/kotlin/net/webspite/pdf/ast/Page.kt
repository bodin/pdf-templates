package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle

class Page(content: MutableList<Table> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun draw(ctx: DrawContext): Float {
        ctx.page = PDPage(PDRectangle(ctx.width, ctx.height))
        ctx.document?.addPage(ctx.page)

        var myY = this.y
        this.content.forEach {
            it.x = this.x
            it.widthPx = this.widthPx
            it.y = myY
            myY = it.draw(ctx)
        }
        return myY
    }
}