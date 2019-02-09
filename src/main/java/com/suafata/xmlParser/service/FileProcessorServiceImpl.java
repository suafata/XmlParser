package com.suafata.xmlParser.service;

import com.suafata.xmlParser.domain.repository.UserRepository;
import com.suafata.xmlParser.exception.XmlParseException;
import com.suafata.xmlParser.task.XmlFileTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Service
public class FileProcessorServiceImpl implements FileProcessorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileProcessorServiceImpl.class);
    private final ForkJoinPool pool;
    private final UserRepository repository;

    @Value("${xml.max.file.threshold:1}")
    private Integer threshold;

    @Value("${xml.max.flush.capacity:10000}")
    private Integer capacity;

    @Autowired
    public FileProcessorServiceImpl(ForkJoinPool pool,  UserRepository repository){
        this.pool = pool;
        this.repository = repository;
    }


    public void processFiles(File[] files){
        if(files != null){
            XmlFileTask xmlFileTask = new XmlFileTask(files, threshold, repository, capacity);
            pool.execute(xmlFileTask);
            do
            {
                try
                {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e)
                {
                    throw new XmlParseException("InterruptedException while sleeping.", e);
                }
            } while (!xmlFileTask.isDone());

            pool.shutdown();

            Integer count = repository.countAll();
            LOGGER.info("Batch run inserted total : {} records", count);
        }
    }
}
