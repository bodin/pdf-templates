package com.github.bodin.pdf.ast.node

import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.InteriorNode

class PageNode(private val parent: Node, content: MutableList<TableNode> = mutableListOf())
    : InteriorNode<TableNode>(content) {
    override fun getParent(): Node? = parent

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