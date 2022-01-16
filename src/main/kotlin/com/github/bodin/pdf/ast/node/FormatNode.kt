package com.github.bodin.pdf.ast.node

import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.InteriorNode
import com.github.bodin.pdf.ast.Node

class FormatNode(private val parent: Node, content: MutableList<FormatNode> = mutableListOf())
    : InteriorNode<FormatNode>(content) {

    override fun getParent(): Node? = parent
    override fun draw(ctx: DrawContext) {
        this.drawChildren(ctx)
    }
}