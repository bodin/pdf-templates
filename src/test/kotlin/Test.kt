import com.github.bodin.pdf.TemplateEngine
import com.github.bodin.pdf.api.ResourceLoader
import com.github.bodin.pdf.pre.handlebars.HandlebarsProcessor
import java.io.FileOutputStream
import java.nio.file.Paths
import kotlin.test.Test

internal class Test {
    @Test
    fun test() {

        val src = "${Paths.get("").toAbsolutePath().toString()}/src/test/resources/samples/input"
        val dest = "${Paths.get("").toAbsolutePath().toString()}/src/test/resources/samples/output"

        val engine = TemplateEngine(
            processor = HandlebarsProcessor(),
            loader = ResourceLoader.Default
        )

        // classpath
        FileOutputStream("$dest/test-simple.pdf").use {
            engine.executeFile("classpath://samples/input/test-simple.xml", it)
        }

        // uri
        FileOutputStream("$dest/test-image.pdf").use {
            engine.executeFile("file://$src/test-image.xml", it)
        }

        // absolute
        FileOutputStream("$dest/test-nested.pdf").use {
            engine.executeFile("$src/test-nested.xml", it)
        }
        // relative
        FileOutputStream("$dest/test-hard.pdf").use {
            engine.executeFile("src/test/resources/samples/input/test-hard.xml", it)
        }

        //with handlebars preprocessing
        FileOutputStream("$dest/test-nested.hb.pdf").use {
            var ctx = arrayOf("val1", "val2")
            engine.executeFile(ctx, "classpath://samples/input/test-nested.hb.xml", it)
        }

    }
}