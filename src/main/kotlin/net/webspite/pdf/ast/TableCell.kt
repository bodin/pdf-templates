package net.webspite.pdf.ast

import be.quodlibet.boxable.BaseTable
import net.webspite.pdf.model.DrawContext

class TableCell(content: MutableList<Row> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun draw(ctx: DrawContext): Float {
        var c = ctx.tables.peek().rows.last().createCell(this.widthPct, "")

        ctx.tables.push(
            BaseTable(
            this.y,
            this.y,
            ctx.margin,
            this.widthPt,
            this.x,
            ctx.document,
            ctx.page,
            true,
            true)
        )
        var myY = 0f
        this.content.forEach {
            it.x = this.x
            it.widthPt = this.widthPt
            it.y = this.y - myY
            this.copyTo(it)
            myY = it.draw(ctx)
        }
        //This is needed - since an inner table does not reserve row height.
        c.height = myY
        ctx.tables.pop().draw()
        return this.y - myY
    }
}