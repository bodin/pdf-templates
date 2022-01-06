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
Attributes by default cascade syntactically down into their children.  Specifying 
`fontColor="red"` on a table will mean every `text` cell is printed in red font - 
unless another parent changes it between the `table` and `text` cell.

**All attributes can be written in camel case, or dash syntax.  `paddingTop` or `padding-top`**

```
layout = "int int*"  
  * defaults to an even split 
  non cascading, only available on `table`
  Represents the number of colums and their propartion relative to each other.
bookmark = "string"
  cascades down to the first `text` cell
  This represents an entry in the PDF outline that can be used by a user to navigate 
  
fontName = "string"
  * defaults to Helvetica
  font name that is known to OpenPDF
  
fontColor = "red|green|..." or "#RRGGBB"
  * defaults to Black
  color name known to java.awt.Color as a field or hex value
  
fontSize = "int"
  * defaults to 12
  size in points of font
  
fontStyle = "bold | italic | strikethrough | underline | normal"
  * defaults to normal
  font style

alignVertical = "top | middle | bottom"
  * defaults to middle
  vertical alignment  
                            
alignHorizontal = "left | center | right"
  * defaults to left
  horizontal alignment   
  
backgroundColor = "red|green|..." or "#RRGGBB"
  * defaults to White
  color name known to java.awt.Color as a field or hex value

border = "size color" | "size" | "color" | "color size"
  * defaults to 0 black
  applies border styling to all sides of the cell

borderTop = "size color" | "size" | "color" | "color size"
  * defaults to 0 black
  applies border styling to the top side of the cell
  
borderBottom = "size color" | "size" | "color" | "color size"
  * defaults to 0 black
  applies border styling to the bottom side of the cell
  
borderLeft = "size color" | "size" | "color" | "color size"
  * defaults to 0 black
  applies border styling to the left side of the cell
  
borderRight = "size color" | "size" | "color" | "color size"
  * defaults to 0 black
  applies border styling to the right side of the cell  
  
padding = "int"
  * defaults to 0
  padding dimension for all sides
  
paddingTop = "int"
  * defaults to 0
  padding dimension for top
  
paddingBottom = "int"
  * defaults to 0
  padding dimension for bottom
  
paddingLeft = "int"
  * defaults to 0
  padding dimension for left
  
paddingRight = "int"
  * defaults to 0
  padding dimension for right

margin = "int"
  * defaults to 36pt / .5in
  cascading to page, only available on `document` or `page`
  margin dimension for all sides
  
marginTop = "int"
  * defaults to 36pt / .5in
  cascading to page, only available on `document` or `page`
  margin dimension for top
  
marginBottom = "int"
  * defaults to 36pt / .5in
  cascading to page, only available on `document` or `page`
  margin dimension for bottom
  
marginLeft = "int"
  * defaults to 36pt / .5in
  cascading to page, only available on `document` or `page`
  margin dimension for left
  
marginRight = "int"
  * defaults to 36pt / .5in
  cascading to page, only available on `document` or `page`
  margin dimension for right

width = "int"
  only available on image
  sets the image width 

height = "int"                           
  only available on image
  sets the image height
   
```


# TODO
## Cleanup
### Not Done
2. [ ] proper test cases, moving resources to test folder

### Done
1. [x] Fix when missing some cells in a row

## Features
### Not Done
1. [ ] colspan
2. [ ] Headers and Footers (document level and override at the page level)
3. [ ] page numbering options
4. [ ] add a 'cell' markup so we can style the cell of nested content. 
For example if we want a cell with padding and then a full bordered table.
5. [ ] Add CLI Main class 


### Won't do
4. Font (all in one directive) - don't like the complexity and as of now, does not seem to add much benefit
 
### Done
6. [x] image loading by generic protocol (https, file, classpath)
11. [x] image width and height
2. [x] margin properties
8. [x] font style - how do you apply multiple
9. [x] bold, italic, etc
10. [x] Border properties
11. [x] TOC
12. [x] allow 'dash based' attribute names
13. [x] add template engine support (freemarker, handlebars, etc)
14. [x] image cell
15. [x] Add RGB colors
16. [x] Set default values
9. [x] font style - if a table is bold, and a cell is italics - should it
       combine or change?