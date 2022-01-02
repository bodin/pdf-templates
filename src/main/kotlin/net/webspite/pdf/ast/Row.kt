package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

class Row(content: MutableList<ContentCell> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun cells(): Int {
        return this.content.sumBy { it.cells() }
    }
    override fun draw(ctx: DrawContext){
        this.drawChildren(ctx)
    }
}