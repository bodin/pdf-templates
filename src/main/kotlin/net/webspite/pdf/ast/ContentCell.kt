package net.webspite.pdf.ast

import be.quodlibet.boxable.image.Image
import net.webspite.pdf.model.DrawContext
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import java.awt.image.BufferedImage
import java.net.URI
import javax.imageio.ImageIO

abstract class ContentCell(var content: String = ""): Content() {

}