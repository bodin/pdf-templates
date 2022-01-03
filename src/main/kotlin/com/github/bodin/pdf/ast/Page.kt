package com.github.bodin.pdf.ast

class Page(content: MutableList<Table> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun draw(ctx: DrawContext) {
        ctx.document?.newPage()
        this.drawChildren(ctx)
    }
}