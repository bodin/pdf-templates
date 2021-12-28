package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Row(content: MutableList<Cell> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun calculate(ctx: DrawContext) {

    }

    override fun draw(ctx: DrawContext) : Float{
        var r = ctx.tables.peek().createRow(0f)

        var widths = this.content.map {it.widthPct}

        var undefinedWidths = widths.filter { it == 0f }.size
        var remainingWidth = 100 - widths.filter { it > 0f }.fold(0f, {t, n -> t+n})
        var pctWidth = remainingWidth / undefinedWidths
        var currentX = this.x;

        this.content.forEach{
            it.x = currentX
            it.widthPct = pctWidth
            it.widthPx = this.widthPx * it.widthPct / 100

            currentX = it.x + it.widthPx
            it.y = this.y
            it.draw(ctx)
        }

        return r.height
    }
}