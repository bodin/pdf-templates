package com.github.bodin.pdf.ast.node

import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.InteriorNode

class PageNode(private val parent: DocumentNode, content: MutableList<TableNode> = mutableListOf())
    : InteriorNode<TableNode>(content){

    var header: HeaderOrFooterNode? = null
    var footer: HeaderOrFooterNode? = null

    override fun getParent(): Node? = parent

    override fun draw(ctx: DrawContext) {
        ctx.applyMargins(ctx, this.parent)

        //first page does not need this
        if(ctx.page != null) {
            ctx.document.newPage()
        }
        ctx.page = this

        this.drawChildren(ctx)
    }
}