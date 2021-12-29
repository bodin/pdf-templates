package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Cell(content: MutableList<Content> = mutableListOf()) : NestedContent(content) {
    override fun draw(ctx: DrawContext): Float {
        var c = ctx.tables.peek().rows.last().createCell(this.widthPct, "")
        this.content.forEach {
            it.x = this.x
            it.widthPt = this.widthPt
            it.y = this.y

            this.copyTo(it)
            it.draw(ctx)
        }
        return c.height
    }
}