package net.webspite.pdf.ast

import com.lowagie.text.pdf.PdfPTable
import net.webspite.pdf.model.DrawContext

class Table(content: MutableList<Row> = mutableListOf()) : NestedContent(content as MutableList<Content>) {
    override fun draw(ctx: DrawContext) {
        val table = PdfPTable((this.content.first() as Row).cells())
        ctx.tables.push(table)

        this.drawChildren(ctx)

        ctx.document?.add(table)
        ctx.tables.pop()
    }
}