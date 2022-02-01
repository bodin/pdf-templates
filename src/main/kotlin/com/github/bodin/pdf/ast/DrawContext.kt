package com.github.bodin.pdf.ast

import com.github.bodin.pdf.api.ResourceLoader
import com.github.bodin.pdf.ast.node.DocumentNode
import com.github.bodin.pdf.ast.node.HeaderFooterProvider
import com.github.bodin.pdf.ast.node.HeaderOrFooterNode
import com.github.bodin.pdf.ast.node.PageNode
import com.lowagie.text.*
import com.lowagie.text.pdf.*
import java.awt.Color
import java.util.*


class DrawContext(var document: Document, val loader : ResourceLoader) {
    companion object {
        const val DEFAULT_MARGIN = 36f

        fun styleCell(cell: PdfPCell, a: Attributes){

            cell.isUseAscender = true
            cell.isUseDescender = true

            a.height?.let{ cell.minimumHeight = it}

            a.backgroundColor?.let{ cell.backgroundColor = it }

            a.paddingTop?.let{ cell.paddingTop = it }
            a.paddingBottom?.let{ cell.paddingBottom = it }
            a.paddingLeft?.let{ cell.paddingLeft = it }
            a.paddingRight?.let{ cell.paddingRight = it }

            a.borderColorTop?.let{cell.borderColorTop = it }
            a.borderColorBottom?.let{cell.borderColorBottom = it }
            a.borderColorLeft?.let{cell.borderColorLeft = it }
            a.borderColorRight?.let{cell.borderColorRight = it }

            a.borderWidthTop?.let{cell.borderWidthTop = it }
            a.borderWidthBottom?.let{cell.borderWidthBottom = it }
            a.borderWidthLeft?.let{cell.borderWidthLeft = it }
            a.borderWidthRight?.let{cell.borderWidthRight = it }

            a.alignV?.let { cell.verticalAlignment = it }

            a.colspan?.let { cell.colspan = it }
            a.rowspan?.let { cell.rowspan = it }
        }

        fun styleCell(cell: Image,a: Attributes){
            a.borderWidthTop?.let{cell.borderWidthTop = it }
            a.borderWidthBottom?.let{cell.borderWidthBottom = it }
            a.borderWidthLeft?.let{cell.borderWidthLeft = it }
            a.borderWidthRight?.let{cell.borderWidthRight = it }

            a.borderColorTop?.let{cell.borderColorTop = it }
            a.borderColorBottom?.let{cell.borderColorBottom = it }
            a.borderColorLeft?.let{cell.borderColorLeft = it }
            a.borderColorRight?.let{cell.borderColorRight = it }

            if(a.width != null && a.height != null){
                cell.scaleAbsolute(a.width?:0f, a.height?:0f)
            } else if(a.width != null){
                cell.scaleToFit(a.width?:0f, cell.plainHeight)
            }else if (a.height != null){
                cell.scaleToFit(cell.plainWidth, a.height?:0f)
            }

            a.alignH?.let{ cell.alignment = it }
        }

        fun styleCell(@Suppress("UNUSED_PARAMETER") cell: PdfPTable, @Suppress("UNUSED_PARAMETER") a: Attributes){
            // this.styleCell(cell.defaultCell)
        }
    }
    var writer: PdfWriter? = null
    var page: PageNode? = null
    var tables: Stack<PdfPTable> = Stack()
    var paragraph: Paragraph? = null

    var outline: PdfOutline? = null
    var inHeader: Boolean = false

    fun getHeader() = this.page?.header
    fun getFooter() = this.page?.footer

    fun applyMargins(ctx: DrawContext, c:DocumentNode){

        val header = ctx.getHeader()
        val footer = ctx.getFooter()

        val headerHeight = header?.getTable(document)?.totalHeight ?: 0f
        var topMargin = c.attributes.marginTop ?: DEFAULT_MARGIN
        if(headerHeight > topMargin) topMargin = headerHeight

        val footerHeight = footer?.getTable(document)?.totalHeight ?: 0f
        var bottomMargin = c.attributes.marginBottom ?: DEFAULT_MARGIN
        if(footerHeight > bottomMargin) bottomMargin = footerHeight

        document.setMargins(
           c.attributes.marginLeft ?: DEFAULT_MARGIN,
           c.attributes.marginRight ?: DEFAULT_MARGIN,
           topMargin,
           bottomMargin
        )

    }
    fun bookmark(ch: Paragraph, lbl:String){
        (ch.chunks.first() as Chunk).setLocalDestination(lbl)
        PdfOutline(outline, PdfAction.gotoLocalPage(lbl, false), lbl)
    }

    fun styleCell(cell: Paragraph, a: Attributes){
        cell.font = this.getFont(a)
        cell.setLeading(0f, 1.35f)

        a.alignH?.let{ cell.alignment = it }
    }

    fun getFont(a: Attributes) : Font {
        return this.getFont(
            a.fontName?:FontFactory.HELVETICA,
            a.fontSize?: Font.DEFAULTSIZE.toFloat(),
            a.fontStyle?: Font.NORMAL,
            a.fontColor?: Color.BLACK)
    }

    fun getFont(name: String, size: Float, style: Int, color: Color) : Font {
        var key = "$name|$size|$style!$color"
        return fontCache.getOrPut(key) {
            FontFactory.getFont(name, size, style, color)
        }
    }
    var fontCache: MutableMap<String, Font> = HashMap()
}