package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Page(content: MutableList<Table>? = mutableListOf()) : Content<MutableList<Table>>(content) {
    override fun draw(ctx: DrawContext) {
        TODO("Not yet implemented")
    }
}