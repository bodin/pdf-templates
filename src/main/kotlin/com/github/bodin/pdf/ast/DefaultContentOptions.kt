package com.github.bodin.pdf.ast

import com.lowagie.text.Element
import com.lowagie.text.Font
import com.lowagie.text.FontFactory
import com.lowagie.text.Image
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import java.awt.Color

class DefaultContentOptions: Content {

    constructor(){
        this.fontName = FontFactory.HELVETICA
        this.fontSize = 12f
        this.fontStyle = Font.NORMAL
        this.fontColor = Color.BLACK

        this.alignH = Element.ALIGN_LEFT
        this.alignV = Element.ALIGN_MIDDLE

        this.backgroundColor = Color.WHITE

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
    override fun draw(ctx: DrawContext) {

    }
}