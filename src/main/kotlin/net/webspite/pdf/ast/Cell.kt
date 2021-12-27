package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Cell(content: MutableList<Content<*>> = mutableListOf()) : Content<MutableList<Content<*>>>(content) {
    override fun draw(ctx: DrawContext) {
        ctx.tables.peek().rows.last().createCell(50f, "")
        this.content.forEach { it.draw(ctx) }
    }
}