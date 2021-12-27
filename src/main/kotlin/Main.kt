import be.quodlibet.boxable.*
import be.quodlibet.boxable.line.LineStyle
import net.webspite.pdf.model.DrawContext
import net.webspite.pdf.parser.XMLParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType1Font
import java.awt.Color


fun main(args: Array<String>) {
    Thread.currentThread()?.contextClassLoader?.getResource("test.xml")?.openStream().use {
        if(it != null) XMLParser().parse(it).draw(DrawContext())
    }
}

fun draw(){
    val margin = 10f;
    val doc = PDDocument()
    val page = PDPage(PDRectangle.A5)
    doc.addPage(page)


    val table = BaseTable(
        PDRectangle.A5.height - margin,
        PDRectangle.A5.height - margin,
        margin,
        PDRectangle.A5.width - 2 * margin,
        margin,
        doc,
        page,
        true,
        true
    )
    // Create a new font object selecting one of the PDF base fonts
    // Create a new font object selecting one of the PDF base fonts
    val fontPlain: PDFont = PDType1Font.HELVETICA
    val fontBold: PDFont = PDType1Font.HELVETICA_BOLD
    val fontItalic: PDFont = PDType1Font.HELVETICA_OBLIQUE
    val fontMono: PDFont = PDType1Font.COURIER

    // the parameter is the row height
    val headerRow: Row<PDPage> = table.createRow(1f)

    // the first parameter is the cell width
    var cell: Cell<PDPage?> = headerRow.createCell(100f, "Header")
    cell.font = fontBold
    cell.fontSize = 20f

    // vertical alignment
    cell.valign = VerticalAlignment.MIDDLE

    // border style
    cell.setTopBorderStyle(LineStyle(Color.BLACK, 0f))
    table.addHeaderRow(headerRow)

    var row: Row<PDPage?> = table.createRow(0f)
    cell = row.createCell(30f, "black left plain")
    cell.fontSize = 15f
    cell = row.createCell(70f, "black left bold")
    cell.fontSize = 15f
    cell.font = fontBold

    row = table.createRow(20f)
    cell = row.createCell(50f, "red right mono")
    cell.textColor = Color.RED
    cell.fontSize = 15f
    cell.font = fontMono
    // horizontal alignment
    cell.align = HorizontalAlignment.RIGHT
    cell.setBottomBorderStyle(LineStyle(Color.RED, 5f))
    cell = row.createCell(50f, "green centered italic")
    cell.textColor = Color.GREEN
    cell.fontSize = 15f
    cell.font = fontItalic
    cell.align = HorizontalAlignment.CENTER
    cell.setBottomBorderStyle(LineStyle(Color.GREEN, 5f))

    row = table.createRow(0f)
    cell = row.createCell(40f, "rotated")
    cell.fontSize = 15f
    // rotate the text
    cell.isTextRotated = true
    cell.align = HorizontalAlignment.RIGHT
    cell.valign = VerticalAlignment.MIDDLE
    // long text that wraps
    cell = row.createCell(30f, "long text long text long text long text long text long text long text")
    cell.fontSize = 12f
    // long text that wraps, with more line spacing
    cell = row.createCell(30f, "long text long text long text long text long text long text long text")
    cell.fontSize = 12f
    cell.lineSpacing = 2f

    table.draw()

    doc.save("./out.pdf")
    doc.close()
}
