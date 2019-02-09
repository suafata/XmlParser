package com.suafata.xmlParser.component;

import com.suafata.xmlParser.service.FileProcessorService;
import com.suafata.xmlParser.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileRunnerComponent implements CommandLineRunner {

    private final FileProcessorService fileProcessorService;

    @Value("${xml.directory}")
    private String xmlDir;

    @Value("${xml.compute.files}")
    private Boolean computeFile;

    @Autowired
    public FileRunnerComponent(FileProcessorService fileProcessorService){
            this.fileProcessorService = fileProcessorService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(computeFile != null && computeFile) {
            File[] files = FileUtils.loadFiles(xmlDir);
            if (files != null && files.length != 0) {
                fileProcessorService.processFiles(files);
            }
        }
    }
}
