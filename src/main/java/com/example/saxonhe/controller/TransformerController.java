package com.example.saxonhe.controller;

import com.example.saxonhe.models.TransformDTO;
import com.example.saxonhe.service.SaxonTransform;
import net.sf.saxon.s9api.SaxonApiException;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
public class TransformerController {

    @RequestMapping(
            value = "/transform",
            method = RequestMethod.POST)
    public String process(@RequestBody TransformDTO transformDTO) {
        SaxonTransform service = new SaxonTransform();

        try {
            return service.transform(transformDTO);
        } catch (SaxonApiException saxonApiException) {
            return saxonApiException.getMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
