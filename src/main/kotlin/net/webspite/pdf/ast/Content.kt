package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

abstract class Content {
    var x: Float = -1f
    var y: Float = -1f
    var widthPct: Float = 0f
    var widthPx: Float = 0f

    abstract fun draw(ctx: DrawContext): Float
    abstract fun calculate(ctx: DrawContext);
    override fun toString(): String {
        return "${this.javaClass.name}"
    }
}