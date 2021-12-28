package net.webspite.pdf.ast

import be.quodlibet.boxable.BaseTable
import net.webspite.pdf.model.DrawContext

class Table(content: MutableList<Row> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun draw(ctx: DrawContext): Float {
        ctx.tables.push(
            BaseTable(
            this.y,
            this.y,
            ctx.margin,
            this.widthPx,
            this.x,
            ctx.document,
            ctx.page,
            true,
            true)
        )
        var myY = this.y
        this.content.forEach {
            it.x = this.x
            it.widthPx = this.widthPx
            it.y = myY
            myY = it.draw(ctx)
        }
        return ctx.tables.pop().draw()
    }
}