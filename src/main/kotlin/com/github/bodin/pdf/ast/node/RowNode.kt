package com.github.bodin.pdf.ast.node

import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.LeafNode
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.InteriorNode

class RowNode(private val parent: Node, content: MutableList<Node> = mutableListOf())
    : InteriorNode<Node>(content) {
    override fun getParent(): Node? = parent

    override fun cells(): Int {
        return this.content.sumOf { it.cells() }
    }
    override fun draw(ctx: DrawContext){
        this.drawChildren(ctx)
        ctx.tables.peek().completeRow()
    }
}