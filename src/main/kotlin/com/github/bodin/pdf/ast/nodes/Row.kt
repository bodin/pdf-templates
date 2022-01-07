package com.github.bodin.pdf.ast.nodes

import com.github.bodin.pdf.ast.Content
import com.github.bodin.pdf.ast.ContentCell
import com.github.bodin.pdf.ast.DrawContext
import com.github.bodin.pdf.ast.NestedContent
import com.lowagie.text.pdf.PdfPCell

class Row(content: MutableList<ContentCell> = mutableListOf()) : NestedContent(content as MutableList<Content>) {

    override fun cells(): Int {
        return this.content.sumOf { it.cells() }
    }
    override fun draw(ctx: DrawContext){
        this.drawChildren(ctx)
        ctx.tables.peek().completeRow()
    }
}