package com.github.bodin.pdf.ast

abstract class LeafNode(private val parent: Node, var content: String = ""): Node() {
    override fun getParent(): Node? = parent
}