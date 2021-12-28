package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class StringContent(var content: String): Content() {
    override fun draw(ctx: DrawContext) : Float{
        ctx.tables.peek().rows.last().cells.last().text = this.content
        return ctx.tables.peek().rows.last().cells.last().height
    }

    override fun calculate(ctx: DrawContext) {
    }
}