package net.webspite.pdf.model

import com.lowagie.text.Document
import com.lowagie.text.pdf.PdfPTable
import java.util.*

class DrawContext {
    var document: Document? = null
    var tables: Stack<PdfPTable> = Stack()
}