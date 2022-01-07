package com.github.bodin.pdf.ast.nodes

import com.github.bodin.pdf.ast.Content
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.NestedContent

class Page(content: MutableList<Table> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun draw(ctx: DrawContext) {
        ctx.applyMargins(this)

        //first page does not need this
        if(ctx.page > 1) {
            ctx.document?.newPage()
        }
        this.drawChildren(ctx)
        ctx.page++
    }
}