package com.example.saxonhe.service;

import net.sf.saxon.s9api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class SaxonTransformTest {

    @Autowired
    ResourceLoader resourceLoader;

    public String testTransform(String xmlFile, String xsltFile) throws SaxonApiException {
        ByteArrayOutputStream bot = new ByteArrayOutputStream();
        Processor proc = new Processor(false);
        XQueryCompiler comp = proc.newXQueryCompiler();
        XQueryExecutable exp = comp.compile("<a b='c'>{5+2}</a>");
        Serializer out = proc.newSerializer(bot);
        out.setOutputProperty(Serializer.Property.METHOD, "xml");
        out.setOutputProperty(Serializer.Property.INDENT, "yes");
        out.setOutputProperty(Serializer.Property.OMIT_XML_DECLARATION, "yes");
        exp.load().run(out);

        return bot.toString();
    }

    public void testTransformToHtml() throws SaxonApiException, FileNotFoundException
    {
        File xslFile = null;
        xslFile = ResourceUtils.getFile("output/tour.xsl");

        Processor processor = new Processor(false);
        XsltCompiler compiler = processor.newXsltCompiler();
        XsltExecutable exp = compiler.compile(new StreamSource(xslFile));
        Serializer out = processor.newSerializer(new File("classpath:styles/tour.html"));
        out.setOutputProperty(Serializer.Property.METHOD, "html");
        out.setOutputProperty(Serializer.Property.INDENT, "yes");
        Xslt30Transformer trans = exp.load30();
        trans.callTemplate(new QName("main"), out);
    }

    public String simpleFileTransformation(String xmlLocation, String stylesheetLoation) throws SaxonApiException, IOException
    {
        Resource xmlResource = new ClassPathResource(xmlLocation);
        Resource styleResource = new ClassPathResource(stylesheetLoation);

        Map <QName, XdmValue> stylesheetParameterMap = new HashMap<>();
        Processor processor = new Processor(false);
        XsltCompiler compiler = processor.newXsltCompiler();
        XsltExecutable stylesheet = compiler.compile(new StreamSource(styleResource.getFile()));
        ByteArrayOutputStream bot = new ByteArrayOutputStream();
        Serializer out = processor.newSerializer(bot);
        out.setOutputProperty(Serializer.Property.METHOD, "html");
        out.setOutputProperty(Serializer.Property.INDENT, "yes");
        Xslt30Transformer transformer = stylesheet.load30();

        //Create stylesheetParamMap with convertToStylesheetMap

        QName paramName = new QName("in");
        stylesheetParameterMap.put(paramName, new XdmAtomicValue("somestring"));

        transformer.setStylesheetParameters(stylesheetParameterMap);
        transformer.transform(new StreamSource(xmlResource.getFile()), out);

        return bot.toString();
    }
}
