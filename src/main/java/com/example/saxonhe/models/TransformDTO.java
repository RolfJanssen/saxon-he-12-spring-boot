package com.example.saxonhe.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransformDTO {
    String xmlFile;
    String xsltFile;
    String outPutFile;
    Map<String, String> stylesheetParams;
}
