package net.webspite.pdf.ast

import net.webspite.pdf.model.DrawContext

abstract class Content {
    abstract fun draw(ctx: DrawContext);
    override fun toString(): String {
        return "${this.javaClass.name}"
    }
}