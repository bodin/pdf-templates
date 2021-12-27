package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class StringContent(content: String): Content<String>(content) {
    override fun draw(ctx: DrawContext) {
        ctx.tables.peek().rows.last().cells.last().text = this.content
    }
}