package com.github.bodin.pdf.ast

class Page(content: MutableList<Table> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun draw(ctx: DrawContext) {
        ctx?.document?.setMargins(
            marginLeft?:DEFAULT_MARGIN,
            marginRight?:DEFAULT_MARGIN,
            marginTop?:DEFAULT_MARGIN,
            marginBottom?:DEFAULT_MARGIN)

        ctx.document?.newPage()

        this.drawChildren(ctx)
    }
}