package com.github.bodin.pdf.ast

abstract class NestedContent(var content: MutableList<Content>): Content() {
    fun drawChildren(ctx: DrawContext) {
        this.content.forEach {
            this.copyTo(it)
            it.draw(ctx)
        }
    }

    override fun toString(): String {
        return "${this.javaClass.name}: {$content}\n"
    }
}