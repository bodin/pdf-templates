# pdf-templates
PDF Templates is a simple XML to PDF conversion tool.  The layout 
is table based, including nested tables.  Additional flexibility 
can be added using a template engine to preprocess the document.
Handlebars is included as a preprocessing engine.

Example Template (without handlebars processing needed)
```xml
<document>
    <page>
        <table padding="10">
            <row>
                <text>column next to a nested table with 50% of the space</text>
                <table layout="30 70">
                    <row>
                        <text>cell with 30%</text>
                        <text>this is longer text given 70% of the nested table space</text>
                    </row>
                </table>
            </row>
        </table>
    </page>
</document>
```

## API Usage

```java
    var engine = TemplateEngine(
        processor = TemplateProcessor.NoOp,
        loader = ResourceLoader.Default
    )
        
    //  No handlebars processor - context if given is ignored
    FileOutputStream("build/test-simple.pdf").use {
        engine.executeFile("classpath://test-simple.xml", it)
    }

    engine = TemplateEngine(
        processor = HandlebarsProcessor(),
        loader = ResourceLoader.Default
    )    
        
    //  No handlebars context - handlebars still runs
    FileOutputStream("build/test-simple.pdf").use {
        engine.executeFile("classpath://test-simple.xml", it)
    }
        
    //  With Handlebars context
    FileOutputStream("build/test-nested.hb.pdf").use {
        var ctx = arrayOf("val1", "val2")
        engine.executeFile(ctx, "classpath://test-nested.hb.xml", it)
    }

```

## Grammar

The grammar is straight forward, with the only interesting rule being `row` 
which has multiple different `cell` representations

```
document := page+
page     := table+
table    := row+
row      := text+ | image+ | table+
```

## Attributes
Attributes by default cascaade syntactically down into their children.  Specifying 
`fontColor="red"` on a table will mean every `text` cell is printed in red font - 
unless another parent changes it between the `table` and `text` cell.

```
layout = "int int*"  
  non cascading, only available on 'table'
  Represents the number of colums and their propartion relative to each other.
  
fontName = "string"
  font name that is known to OpenPDF
  
fontColor = "red|green|..." or "#RRGGBB"
  color name known to java.awt.Color as a field or hex value
  
fontSize = "int"
  size in points of font
  
fontStyle = "bold | italic | strikethrough | underline"
  font style
  
backgroundColor = "red|green|..." or "#RRGGBB"
  color name known to java.awt.Color as a field or hex value
  
padding = "int"
  padding dimension for all sides
  
paddingTop = "int"
  padding dimension for top
  
paddingBottom = "int"
  padding dimension for bottom
  
paddingLeft = "int"
  padding dimension for left
  
paddingRight = "int"
  padding dimension for right

alignV = "top | middle | bottom"
  vertical alignment  
                            
alignH = "left | center | right"
  horizontal alignment                            
```


# TODO
## Cleanup
1. [x] Fix when missing some cells in a row

## Features
5. [x] add template engine support (freemarker, handlebars, etc)
6. [ ] TOC
7. [ ] Font (all in one directive)
8. [ ] bold, italic, etc
9. [x] image cell
10. [ ] image loading by generic protocol (https, file, classpath)
11. [ ] add a 'cell' markup so we can style the cell of nested content. 
For example if we want a cell with padding and then a full bordered table.
12. [ ] font style - how do you apply multiple
13. [ ] font style - if a table is bold, and a cell is italics - should it 
combine or change?
14. [ ] Add CLI Main class
15. [x] Add RGB colors
16. [ ] allow 'dash based' attribute names
