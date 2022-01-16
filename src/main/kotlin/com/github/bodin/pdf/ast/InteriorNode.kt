package com.github.bodin.pdf.ast

abstract class InteriorNode<T:Node>(var content: MutableList<T>): Node() {
    fun drawChildren(ctx: DrawContext) {
        //always copy bookmark down depth first to the first content
        if(this.content.isNotEmpty()) {
            this.attributes.bookmark?.let {
                val first = this.content.first()
                if (first.attributes.bookmark == null) {
                    this.content.first().attributes.bookmark = this.attributes.bookmark
                }
            }
        }
        this.content.forEach {
            this.attributes.cascade(it.attributes)
            it.draw(ctx)
        }
    }

    override fun toString(): String {
        return "${this.javaClass.name}: {$content}\n"
    }
}