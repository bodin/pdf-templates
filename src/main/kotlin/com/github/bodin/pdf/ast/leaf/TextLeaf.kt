package com.github.bodin.pdf.ast.leaf

import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.LeafNode
import com.github.bodin.pdf.ast.DrawContext
import com.lowagie.text.Chunk

class TextLeaf(parent: Node, content: String = "")
    : LeafNode(parent, content) {

    override fun draw(ctx: DrawContext) {
        if(ctx.paragraph?.size?:0 > 0) this.content = " " + this.content
        val ch = Chunk(this.content, ctx.getFont(this.attributes))
        ctx.paragraph?.add(ch)
    }
}