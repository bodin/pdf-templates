package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Row(content: MutableList<Column> = mutableListOf()) : Content<MutableList<Column>>(content) {
    override fun draw(ctx: DrawContext) {
        TODO("Not yet implemented")
    }
}