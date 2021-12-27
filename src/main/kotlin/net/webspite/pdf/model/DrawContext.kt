package net.webspite.pdf.model

import be.quodlibet.boxable.Table
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
import java.util.*

class DrawContext {
    var margin: Float = 10f
    var width: Float = PDRectangle.A5.width
    var height: Float = PDRectangle.A5.height
    var document: PDDocument? = null
    var page: PDPage? = null
    var tables: Stack<Table<PDPage>> = Stack()
}