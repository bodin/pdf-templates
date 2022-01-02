package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Page(content: MutableList<Table> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun draw(ctx: DrawContext) {
        ctx.document?.newPage()
        this.drawChildren(ctx)
    }
}