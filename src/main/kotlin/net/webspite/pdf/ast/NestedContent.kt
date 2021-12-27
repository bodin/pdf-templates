package net.webspite.pdf.ast

abstract class NestedContent(var content: MutableList<Content>): Content() {
    override fun toString(): String {
        return "${this.javaClass.name}: {$content}\n"
    }
}