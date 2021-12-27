package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Document(content: MutableList<Page>? = mutableListOf()) : Content<MutableList<Page>>(content) {
    override fun draw(ctx: DrawContext) {
        TODO("Not yet implemented")
    }
}