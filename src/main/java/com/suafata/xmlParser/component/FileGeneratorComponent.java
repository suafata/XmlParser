package com.suafata.xmlParser.component;

import com.suafata.xmlParser.service.FileProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
public class FileGeneratorComponent implements CommandLineRunner {

    private final FileProcessorService fileProcessorService;

    @Value("${xml.directory}")
    private String xmlDir;

    @Value("${xml.generate.test.file}")
    private Boolean generateFile;

    @Value("${xml.generate.number}")
    private Integer number;

    @Autowired
    public FileGeneratorComponent(FileProcessorService fileProcessorService){
        this.fileProcessorService = fileProcessorService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(generateFile != null && generateFile) {
        PrintWriter writer = new PrintWriter(xmlDir + "\\xmlFile1.xml", "UTF-8");
        writer.println("<org>\n");
        for (int i = 0; i < number; i++) {
            writer.println("<user>\n" + getData(i) + "</user>\n");
        }
        writer.println("</org>\n");
        writer.close();
        }
    }

    private String getData(int i){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<firstname>Name" + i + "</firstname>\n");
        stringBuilder.append("<lastname>Surname" + i + "</lastname>\n");
        return stringBuilder.toString();
    }
}
