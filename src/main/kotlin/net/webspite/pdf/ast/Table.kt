package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Table(content: MutableList<Row>? = mutableListOf()) : Content<MutableList<Row>>(content) {
    override fun draw(ctx: DrawContext) {
        TODO("Not yet implemented")
    }
}