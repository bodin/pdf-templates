package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext
import kotlin.math.max

abstract class NestedContent(var content: MutableList<Content>): Content() {

    override fun calculate(ctx: DrawContext) {
        this.content.forEach {
            if(it.x < 0f) it.x = this.x
            if(it.widthPx == 0f) it.widthPx = this.widthPx
            it.calculate(ctx)
        }
    }

    fun drawChildren(ctx: DrawContext): Float {
        var max = 0f
        this.content.forEach {
            max = max(max, it.draw(ctx))
        }
        return max
    }

    override fun toString(): String {
        return "${this.javaClass.name}: {$content}\n"
    }
}