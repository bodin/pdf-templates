package com.github.bodin.pdf.ast.leaf

import com.github.bodin.pdf.ast.Node
import com.github.bodin.pdf.ast.LeafNode
import com.github.bodin.pdf.ast.DrawContext
import com.lowagie.text.Chunk

class TextLeaf(parent: Node, content: String = "")
    : LeafNode(parent, content) {

    override fun draw(ctx: DrawContext) {
        //TODO - move this to a strategy
        var new_content = this.content.replace("\$PAGE_NUMBER", ctx.document.pageNumber.toString())
        if(ctx.paragraph?.size?:0 > 0) new_content = " $new_content"
        val ch = Chunk(new_content, ctx.getFont(this.attributes))
        ctx.paragraph?.add(ch)
    }
}