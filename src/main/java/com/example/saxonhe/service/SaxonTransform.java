package com.example.saxonhe.service;

import com.example.saxonhe.models.TransformDTO;
import net.sf.saxon.s9api.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class SaxonTransform {

    public String transform(TransformDTO transformDTO) throws SaxonApiException, IOException
    {
        Resource xmlResource = new ClassPathResource("examples/xml/" + transformDTO.getXmlFile());
        Resource styleResource = new ClassPathResource("examples/stylesheets/" + transformDTO.getXsltFile());

        Processor processor = new Processor(false);
        XsltCompiler compiler = processor.newXsltCompiler();
        XsltExecutable stylesheet = compiler.compile(new StreamSource(styleResource.getFile()));
        ByteArrayOutputStream bot = new ByteArrayOutputStream();
        Serializer out = processor.newSerializer(bot);
        out.setOutputProperty(Serializer.Property.METHOD, "html");
        out.setOutputProperty(Serializer.Property.INDENT, "yes");
        Xslt30Transformer transformer = stylesheet.load30();

        transformer.setStylesheetParameters(this.convertToStylesheetMap(transformDTO.getStylesheetParams()));
        transformer.transform(new StreamSource(xmlResource.getFile()), out);

        return bot.toString();
    }

    private Map<QName, XdmValue> convertToStylesheetMap(Map<String, String> parameters)
    {
        return parameters.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> new QName(entry.getKey()),
                        entry -> new XdmAtomicValue(entry.getValue().toString())
                ));
    }
}
