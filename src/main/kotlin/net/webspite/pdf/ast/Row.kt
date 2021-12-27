package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Row(content: MutableList<Cell> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun draw(ctx: DrawContext) {
        ctx.tables.peek().createRow(20f)
        this.content.forEach { it.draw(ctx) }
    }
}