import com.github.bodin.pdf.TemplateEngine
import com.github.bodin.pdf.api.ResourceLoader
import com.github.bodin.pdf.pre.freemarker.FreemarkerProcessor
import com.github.bodin.pdf.pre.handlebars.HandlebarsProcessor
import java.io.FileOutputStream
import java.nio.file.Paths
import kotlin.test.Test

internal class Test {
    @Test
    fun test() {

        val src = "${Paths.get("").toAbsolutePath().toString()}/src/test/resources/samples/input"
        val dest = "${Paths.get("").toAbsolutePath().toString()}/src/test/resources/samples/output"

        var engine = TemplateEngine(
            processor = HandlebarsProcessor(),
            loader = ResourceLoader.Default
        )

        // classpath
        FileOutputStream("$dest/invoice.hb.pdf").use {
            engine.executeFile("classpath://samples/input/invoice.hb.xml", it)
        }

        engine = TemplateEngine(
            processor = FreemarkerProcessor(),
            loader = ResourceLoader.Default
        )
        // classpath
        FileOutputStream("$dest/invoice.fm.pdf").use {
            engine.executeFile("classpath://samples/input/invoice.fm.xml", it)
        }
    }
}