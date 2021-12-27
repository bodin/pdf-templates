package net.webspite.pdf.ast

import be.quodlibet.boxable.BaseTable
import net.webspite.pdf.model.DrawContext

class Table(content: MutableList<Row> = mutableListOf()) : Content<MutableList<Row>>(content) {
    override fun draw(ctx: DrawContext) {
        ctx.tables.push(
            BaseTable(
            ctx.height - ctx.margin,
            ctx.height - ctx.margin,
            ctx.margin,
            ctx.width - 2 * ctx.margin,
            ctx.margin,
            ctx.document,
            ctx.page,
            true,
            true)
        )
        this.content.forEach { it.draw(ctx) }
        ctx.tables.pop().draw()
    }
}