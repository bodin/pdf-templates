package com.github.bodin.pdf

import com.github.bodin.pdf.ast.node.DocumentNode
import com.lowagie.text.Document
import com.lowagie.text.pdf.PdfPageEventHelper
import com.lowagie.text.pdf.PdfWriter


class CustomPageEvent(val doc: DocumentNode): PdfPageEventHelper() {
    override fun onStartPage(writer: PdfWriter, document: Document) {
        doc.header?.let {

            val table = it.getTable(document)

            table.writeSelectedRows(0, 3, document.left(), document.pageSize.height, writer.directContent)
        }
    }

    override fun onEndPage(writer: PdfWriter, document: Document) {
        doc.footer?.let {
            val table = it.getTable(document)

            table.writeSelectedRows(0, -1, document.left(), document.bottomMargin(), writer.directContent)
        }
    }
}