package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Row(content: MutableList<NestedContent> = mutableListOf()) : NestedContent(content as MutableList<Content>) {

    override fun draw(ctx: DrawContext) : Float{
        var r = ctx.tables.peek().createRow(0f)

        var widths = this.content.map {it.widthPct}

        var undefinedWidths = widths.filter { it == 0f }.size
        var remainingWidth = 100 - widths.filter { it > 0f }.fold(0f, {t, n -> t+n})
        var pctWidth = remainingWidth / undefinedWidths
        var currentX = this.x;

        var myY = 0f
        this.content.forEach{
            it.x = currentX
            if(it.widthPct == 0f) it.widthPct = pctWidth
            if(it.widthPt == 0f) it.widthPt = this.widthPt * it.widthPct / 100

            currentX = it.x + it.widthPt
            it.y = this.y
            this.copyTo(it)
            myY = Math.min(myY, it.draw(ctx))
        }

        r.height = myY

        return r.height
    }
}