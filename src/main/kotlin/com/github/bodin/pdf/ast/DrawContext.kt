package com.github.bodin.pdf.ast

import com.github.bodin.pdf.api.ResourceLoader
import com.lowagie.text.*
import com.lowagie.text.pdf.PdfAction
import com.lowagie.text.pdf.PdfOutline
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import java.awt.Color
import java.util.*


class DrawContext(val loader : ResourceLoader) {
    var document: Document? = null
    var page = 1
    var tables: Stack<PdfPTable> = Stack()
    var paragraph: Paragraph? = null
    var fontCache: MutableMap<String, Font> = HashMap()
    var outline: PdfOutline? = null

    fun applyMargins(cell:Content){
        document?.setMargins(
            cell.marginLeft?: Content.DEFAULT_MARGIN,
            cell.marginRight?: Content.DEFAULT_MARGIN,
            cell.marginTop?: Content.DEFAULT_MARGIN,
            cell.marginBottom?: Content.DEFAULT_MARGIN
        )
    }
    fun bookmark(ch: Paragraph, lbl:String){
        (ch.chunks.first() as Chunk).setLocalDestination(lbl)
        PdfOutline(outline, PdfAction.gotoLocalPage(lbl, false), lbl)
    }

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