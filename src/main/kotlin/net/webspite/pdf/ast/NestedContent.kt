package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext
import kotlin.math.max

abstract class NestedContent(var content: MutableList<Content>): Content() {

    fun drawChildren(ctx: DrawContext): Float {
        var max = 0f
        this.content.forEach {
            this.copyTo(it)
            max = max(max, it.draw(ctx))
        }
        return max
    }

    override fun toString(): String {
        return "${this.javaClass.name}: {$content}\n"
    }
}