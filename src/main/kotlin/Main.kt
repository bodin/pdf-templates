import be.quodlibet.boxable.BaseTable
import be.quodlibet.boxable.datatable.DataTable
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType1Font
import java.util.*
import kotlin.collections.ArrayList


fun main(args: Array<String>) {
    var MARGIN=10f;
    var doc = PDDocument()
    val page = PDPage(PDRectangle.A5)
    doc.addPage(page)


    val data: MutableList<List<*>?> = ArrayList()
    data.add(listOf("Column One", "Column Two", "Column Three", "Column Four", "Column Five"))
    for (i in 1..100) {
        data.add(listOf(
            "Row $i Col One",
            "Row $i Col Two", "Row $i Col Three", "Row $i Col Four", "Row $i Col Five"
        ))
    }

    val table = BaseTable(PDRectangle.A5.height-MARGIN, PDRectangle.A5.height-MARGIN, MARGIN, PDRectangle.A5.width-2*MARGIN, MARGIN, doc, page, true, true)
    val t = DataTable(table, page)
    t.addListToTable(data, DataTable.HASHEADER)
    table.draw()

    doc.save("./out.pdf")
    doc.close()
}
