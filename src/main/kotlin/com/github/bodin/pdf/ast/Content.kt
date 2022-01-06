package com.github.bodin.pdf.ast

import com.lowagie.text.Image
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import java.awt.Color

abstract class Content {
    val DEFAULT_MARGIN = 36f

    var bookmark :String? = null

    //Preferences
    var fontName: String? = null
    var fontSize: Float? = null
    var fontStyle: Int? = null
    var fontColor: Color? = null

    var alignH: Int? = null
    var alignV: Int? = null

    var backgroundColor: Color? = null

    var marginTop: Float? = null
    var marginBottom: Float? = null
    var marginLeft: Float? = null
    var marginRight: Float? = null

    var paddingTop: Float? = null
    var paddingBottom: Float? = null
    var paddingLeft: Float? = null
    var paddingRight: Float? = null

    var borderWidthTop: Float? = null
    var borderWidthBottom: Float? = null
    var borderWidthLeft: Float? = null
    var borderWidthRight: Float? = null

    var borderColorTop: Color? = null
    var borderColorBottom: Color? = null
    var borderColorLeft: Color? = null
    var borderColorRight: Color? = null

    var width: Float? = null
    var height: Float? = null

    abstract fun draw(ctx: DrawContext)

    open fun cells(): Int {
        return 1
    }

    fun copyTo(c: Content){
        if(c.fontName == null) c.fontName = this.fontName
        if(c.fontSize == null) c.fontSize = this.fontSize
        if(c.fontStyle == null) c.fontStyle = this.fontStyle
        if(c.fontColor == null) c.fontColor = this.fontColor

        if(c.backgroundColor == null) c.backgroundColor = this.backgroundColor
        if(c.fontColor == null) c.fontColor = this.fontColor

        if(c.marginTop == null) c.marginTop = this.marginTop
        if(c.marginBottom == null) c.marginBottom = this.marginBottom
        if(c.marginLeft == null) c.marginLeft = this.marginLeft
        if(c.marginRight == null) c.marginRight = this.marginRight

        if(c.paddingTop == null) c.paddingTop = this.paddingTop
        if(c.paddingBottom == null) c.paddingBottom = this.paddingBottom
        if(c.paddingLeft == null) c.paddingLeft = this.paddingLeft
        if(c.paddingRight == null) c.paddingRight = this.paddingRight

        if(c.borderWidthTop == null) c.borderWidthTop = this.borderWidthTop
        if(c.borderWidthBottom == null) c.borderWidthBottom = this.borderWidthBottom
        if(c.borderWidthLeft == null) c.borderWidthLeft = this.borderWidthLeft
        if(c.borderWidthRight == null) c.borderWidthRight = this.borderWidthRight

        if(c.borderColorTop == null) c.borderColorTop = this.borderColorTop
        if(c.borderColorBottom == null) c.borderColorBottom = this.borderColorBottom
        if(c.borderColorLeft == null) c.borderColorLeft = this.borderColorLeft
        if(c.borderColorRight == null) c.borderColorRight = this.borderColorRight
    }


    fun styleCell(cell: PdfPCell){
        this.backgroundColor?.let{ cell.backgroundColor = it }

        this.paddingTop?.let{ cell.paddingTop = it }
        this.paddingBottom?.let{ cell.paddingBottom = it }
        this.paddingLeft?.let{ cell.paddingLeft = it }
        this.paddingRight?.let{ cell.paddingRight = it }

        this.borderColorTop?.let{cell.borderColorTop = it }
        this.borderColorBottom?.let{cell.borderColorBottom = it }
        this.borderColorLeft?.let{cell.borderColorLeft = it }
        this.borderColorRight?.let{cell.borderColorRight = it }

        this.borderWidthTop?.let{cell.borderWidthTop = it }
        this.borderWidthBottom?.let{cell.borderWidthBottom = it }
        this.borderWidthLeft?.let{cell.borderWidthLeft = it }
        this.borderWidthRight?.let{cell.borderWidthRight = it }
    }

    fun styleCell(cell: Image){
        if(this.width != null && this.height != null){
            cell.scaleAbsolute(this.width?:0f, this.height?:0f)
        } else if(this.width != null){
            cell.scaleAbsoluteWidth(this.width?:0f)
        }else if (this.height != null){
            cell.scaleAbsoluteHeight(this.height?:0f)
        }
    }

    fun styleCell(cell: PdfPTable){
       // this.styleCell(cell.defaultCell)
    }
}