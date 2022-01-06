package com.github.bodin.pdf.ast.nodes

import com.github.bodin.pdf.ast.Content
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.NestedContent

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