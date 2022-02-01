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
        val model = mapOf(Pair("pages", listOf(1,2,3)))
        testHandlebars(model)
        testFreemarker(model)
    }
    fun testFreemarker(model: Map<String, Any>){
        val dest = "${Paths.get("").toAbsolutePath()}/src/test/resources/samples/output"

        var engine = TemplateEngine(
            processor = FreemarkerProcessor(),
            loader = ResourceLoader.Default
        )
        // classpath
        FileOutputStream("$dest/invoice.fm.pdf").use {
            engine.executeFile(model,"classpath://samples/input/invoice.fm.xml", it)
        }
    }
    fun testHandlebars(model: Map<String, Any>){

        val dest = "${Paths.get("").toAbsolutePath()}/src/test/resources/samples/output"

        var engine = TemplateEngine(
            processor = HandlebarsProcessor(),
            loader = ResourceLoader.Default
        )

        // classpath
        FileOutputStream("$dest/invoice.hb.pdf").use {
            engine.executeFile(model, "classpath://samples/input/invoice.hb.xml", it)
        }
    }
}