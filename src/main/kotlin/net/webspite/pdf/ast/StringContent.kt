package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class StringContent(content: String): Content<String>(content) {
    override fun draw(ctx: DrawContext) {
        TODO("Not yet implemented")
    }
}