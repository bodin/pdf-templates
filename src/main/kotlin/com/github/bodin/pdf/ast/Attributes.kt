package com.github.bodin.pdf.ast

import com.lowagie.text.Element
import com.lowagie.text.Font
import com.lowagie.text.FontFactory
import com.lowagie.text.Rectangle
import java.awt.Color

open class Attributes {

    //non cascading
    var pageSize: Rectangle? = null
    var layout: FloatArray? = null

    //partial cascading
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

    var colspan: Int? = null
    var rowspan: Int? = null

    fun cascade(c: Attributes){
        if(c.height == null) c.height = this.height
        if(c.fontName == null) c.fontName = this.fontName
        if(c.fontSize == null) c.fontSize = this.fontSize
        if(c.fontStyle == null) c.fontStyle = this.fontStyle
        if(c.fontColor == null) c.fontColor = this.fontColor

        if(c.backgroundColor == null) c.backgroundColor = this.backgroundColor
        if(c.alignH == null) c.alignH = this.alignH
        if(c.alignV == null) c.alignV = this.alignV

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

    companion object {
        const val DEFAULT_MARGIN = 36f
        fun Default() = object: Attributes(){
            init {
                this.fontName = FontFactory.HELVETICA
                this.fontSize = 12f
                this.fontStyle = Font.NORMAL
                this.fontColor = Color.BLACK

                this.alignH = Element.ALIGN_LEFT
                this.alignV = Element.ALIGN_MIDDLE

                this.backgroundColor = Color.WHITE

                this.marginTop = DEFAULT_MARGIN
                this.marginBottom = DEFAULT_MARGIN
                this.marginLeft = DEFAULT_MARGIN
                this.marginRight = DEFAULT_MARGIN

                this.paddingTop = 0f
                this.paddingBottom = 0f
                this.paddingLeft = 0f
                this.paddingRight = 0f

                this.borderWidthTop = 0f
                this.borderWidthBottom = 0f
                this.borderWidthLeft = 0f
                this.borderWidthRight = 0f

                this.borderColorTop = Color.BLACK
                this.borderColorBottom = Color.BLACK
                this.borderColorLeft = Color.BLACK
                this.borderColorRight = Color.BLACK
            }
        }
    }
}