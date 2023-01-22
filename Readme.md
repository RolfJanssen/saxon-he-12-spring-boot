# Saxon HE 12 Spring boot integration

Applies stylesheet to xml and outputs html

Run ` ./mvnw spring-boot:run` to run the application

post to http://localhost:8080/transform

```json
{
  "xmlFile": "books.xml",
  "xsltFile": "books.xsl",
  "outPutFile": "output.xml",
  "stylesheetParams": {
    "param1": "value1",
    "param2": "value2"
  }
}
```

For now it just applies the book.xsl stylesheet to the books.xml and returns books.html as a string
xml and xsl are found in the resourses/examples folder