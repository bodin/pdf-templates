import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import com.github.jknack.handlebars.io.ClassPathTemplateLoader
import com.github.jknack.handlebars.io.TemplateLoader
import net.webspite.pdf.parser.XMLParser
import java.io.FileOutputStream


fun main(args: Array<String>) {
    basic()
    handlebars()
}
fun basic(){
    Thread.currentThread().contextClassLoader?.getResource("test-simple.xml")?.openStream().use {
        if(it != null) {
            val node = XMLParser().parse(it)
            node.write(FileOutputStream("build/test-simple.pdf"))
        }
    }

    Thread.currentThread().contextClassLoader?.getResource("test-nested.xml")?.openStream().use {
        if(it != null) {
            val node = XMLParser().parse(it)
            node.write(FileOutputStream("build/test-nested.pdf"))
        }
    }

    Thread.currentThread().contextClassLoader?.getResource("test-image.xml")?.openStream().use {
        if(it != null) {
            val node = XMLParser().parse(it)
            node.write(FileOutputStream("build/test-image.pdf"))
        }
    }

    Thread.currentThread().contextClassLoader?.getResource("test-hard.xml")?.openStream().use {
        if(it != null) {
            val node = XMLParser().parse(it)
            node.write(FileOutputStream("build/test-hard.pdf"))
        }
    }
}
fun handlebars(){
    val loader: TemplateLoader = ClassPathTemplateLoader("/", ".xml")
    val handlebars = Handlebars().with(loader)

    val template: Template = handlebars.compile("test-nested.hb")
println( template.apply(listOf("a", "b", "c")))
    template.apply(listOf("a", "b", "c")).byteInputStream().use {
        val node = XMLParser().parse(it)
        node.write(FileOutputStream("build/test-nested.hb.pdf"))
    }
}