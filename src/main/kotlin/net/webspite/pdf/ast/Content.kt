package net.webspite.pdf.ast

import com.lowagie.text.Chunk
import com.lowagie.text.Element
import com.lowagie.text.Image
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import net.webspite.pdf.model.DrawContext
import java.awt.Color

abstract class Content {
    //Preferences
    var fontSize: Float = 0f
    var alignH: Int? = null
    var alignV: Int? = null
    var colorText: Color? = null
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
        this.fontSize?.let{ c.fontSize = it }

        this.colorFill?.let{ c.colorFill = it }
        this.colorText?.let{ c.colorText = it }

        this.paddingTop?.let{ c.paddingTop = it }
        this.paddingBottom?.let{ c.paddingBottom = it }
        this.paddingLeft?.let{ c.paddingLeft = it }
        this.paddingRight?.let{ c.paddingRight = it }
    }


    fun styleCell(cell: PdfPCell){
        cell.horizontalAlignment= Element.ALIGN_RIGHT
        cell.verticalAlignment= Element.ALIGN_BOTTOM
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