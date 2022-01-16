package com.github.bodin.pdf.ast

abstract class Node(val attributes: Attributes = Attributes()) {

    abstract fun draw(ctx: DrawContext)
    abstract fun getParent(): Node?

    open fun cells(): Int {
        return this.attributes.colspan?:1
    }
}