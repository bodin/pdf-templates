package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Column(content: MutableList<Content<*>> = mutableListOf()) : Content<MutableList<*>>(content) {
    override fun draw(ctx: DrawContext) {
        TODO("Not yet implemented")
    }
}