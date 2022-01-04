package com.github.bodin.pdf.ast

abstract class NestedContent(var content: MutableList<Content>): Content() {
    fun drawChildren(ctx: DrawContext) {
        //always copy bookmark down depth first to the first content
        this.bookmark?.let{
            val first = this.content.first()
            if(first.bookmark == null) {
                this.content.first()?.bookmark = this.bookmark
            }
        }
        this.content.forEach {
            this.copyTo(it)
            it.draw(ctx)
        }
    }

    override fun toString(): String {
        return "${this.javaClass.name}: {$content}\n"
    }
}