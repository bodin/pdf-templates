package com.github.bodin.pdf.ast

import com.lowagie.text.Document
import com.lowagie.text.Font
import com.lowagie.text.FontFactory
import com.lowagie.text.pdf.PdfPTable
import java.awt.Color
import java.util.*

class DrawContext {
    var document: Document? = null
    var tables: Stack<PdfPTable> = Stack()
    var fontCache: MutableMap<String, Font> = HashMap()

    fun getFont(cell: Content) : Font {
        return this.getFont(
            cell.fontName?:FontFactory.HELVETICA,
            cell.fontSize?: Font.DEFAULTSIZE.toFloat(),
            cell.fontStyle?: Font.NORMAL,
            cell.fontColor?: Color.BLACK)
    }
    fun getFont(name: String, size: Float, style: Int, color: Color) : Font {
        var key = "$name|$size|$style!$color"
        return fontCache.getOrPut(key) {
            FontFactory.getFont(name, size, style, color)
        }
    }
}