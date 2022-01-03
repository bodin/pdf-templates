package net.webspite.pdf.ast

import com.lowagie.text.Font
import com.lowagie.text.FontFactory
import com.lowagie.text.Image
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import net.webspite.pdf.model.DrawContext
import java.awt.Color


abstract class Content {
    //Preferences
    var fontName: String? = null
    var fontSize: Float? = null
    var fontStyle: Int? = null
    var fontColor: Color? = null

    var alignH: Int? = null
    var alignV: Int? = null

    var colorFill: Color? = null
    var paddingTop: Float? = null
    var paddingBottom: Float? = null
    var paddingLeft: Float? = null
    var paddingRight: Float? = null

    abstract fun draw(ctx: DrawContext)

    open fun cells(): Int {
        return 1
    }

    fun copyTo(c: Content){

        if(c.fontName == null) c.fontName = this.fontName
        if(c.fontSize == null) c.fontSize = this.fontSize
        if(c.fontStyle == null) c.fontStyle = this.fontStyle
        if(c.fontColor == null) c.fontColor = this.fontColor

        if(c.colorFill == null) c.colorFill = this.colorFill
        if(c.fontColor == null) c.fontColor = this.fontColor

        if(c.paddingTop == null) c.paddingTop = this.paddingTop
        if(c.paddingBottom == null) c.paddingBottom = this.paddingBottom
        if(c.paddingLeft == null) c.paddingLeft = this.paddingLeft
        if(c.paddingRight == null) c.paddingRight = this.paddingRight
    }


    fun styleCell(cell: PdfPCell){
        this.colorFill?.let{ cell.backgroundColor = it }

        this.paddingTop?.let{ cell.paddingTop = it }
        this.paddingBottom?.let{ cell.paddingBottom = it }
        this.paddingLeft?.let{ cell.paddingLeft = it }
        this.paddingRight?.let{ cell.paddingRight = it }
    }

    fun styleCell(cell: Image){
        if(this.colorFill != null) cell.backgroundColor = this.colorFill
    }

    fun styleCell(cell: PdfPTable){
        this.styleCell(cell.defaultCell)
    }
}