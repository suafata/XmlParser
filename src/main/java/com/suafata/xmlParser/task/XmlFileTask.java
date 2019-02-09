package com.suafata.xmlParser.task;

import com.suafata.xmlParser.domain.entity.User;
import com.suafata.xmlParser.domain.repository.UserRepository;
import com.suafata.xmlParser.exception.XmlParseException;
import com.suafata.xmlParser.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class XmlFileTask extends RecursiveAction {

    private final File[] files;
    private final Integer threshold;
    private final Integer capacity;
    private final UserRepository repository;
    private List<User> users;

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileTask.class);
    private static final AtomicInteger nextThreadId = new AtomicInteger(0);
    private static final AtomicLong nextUserId = new AtomicLong(0);
    private static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
                @Override protected Integer initialValue() {
                    return nextThreadId.getAndIncrement();
                }
            };

    public XmlFileTask(File[] files, Integer threshold, UserRepository repository, Integer capacity){
        this.files = files;
        this.threshold = threshold;
        this.repository = repository;
        this.capacity = capacity;
        users = new ArrayList();
    }

    @Override
    protected void compute() {
        if ( files.length <= threshold ) {
            try {
                for(int i =0; i < files.length; i++){
                    LOGGER.info("Thread Id: {} .Parsing file name: {}", getThreadId(), files[i].getName());
                    parseXML(files[i]);
                }
            } catch (IOException | XMLStreamException e) {
                throw new XmlParseException("Thread Id "+ getThreadId() +" failed to parse files: ", e);
            }
        }
        else {
            int center = files.length / 2;
            XmlFileTask task1 = new XmlFileTask(FileUtils.splitArray(files, 0, center), threshold, repository, capacity);
            XmlFileTask task2 = new XmlFileTask(FileUtils.splitArray(files, center, files.length), threshold, repository, capacity);
            invokeAll(task1, task2);
        }
    }

    public static int getThreadId() {
        return threadId.get();
    }

    private void parseXML(File file) throws FileNotFoundException, XMLStreamException {
        User user = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));
        while (xmlEventReader.hasNext()) {
            if(users.size() >= capacity ){
                flushRecords();
            }
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                if (startElement.getName().getLocalPart().equals("user")) {
                    user = new User();
                } else if (startElement.getName().getLocalPart().equals("firstname")) {
                    xmlEvent = xmlEventReader.nextEvent();
                    user.setFirstName(xmlEvent.asCharacters().getData());
                } else if (startElement.getName().getLocalPart().equals("lastname")) {
                    xmlEvent = xmlEventReader.nextEvent();
                    user.setLastName(xmlEvent.asCharacters().getData());
                }
            } else if (xmlEvent.isEndElement()) {
                EndElement endElement = xmlEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("user")) {
                    user.setId(nextUserId.incrementAndGet());
                    users.add(user);
                }
            }
        }
        if(users.size() != 0 ){
            flushRecords();
        }
    }

    private void flushRecords(){
        long startTime = System.nanoTime();
        LOGGER.info("Thread Id: {}. Flushing number of records: {}", getThreadId(), users.size());
        repository.saveAll(users);
        LOGGER.info("Thread Id: {} .Flushed Time {} seconds", getThreadId(), TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime));
        users.clear();
    }
}
