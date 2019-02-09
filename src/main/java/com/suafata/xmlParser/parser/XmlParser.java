package com.suafata.xmlParser.parser;

import com.suafata.xmlParser.domain.entity.User;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class XmlParser extends DefaultHandler {

    private User user = null;
    private List<User> users = new ArrayList<>();
    String content = null;

    @Override
    public void startElement(final String uri, final String localName, final String name, final Attributes attributes){
         if(name.equals("user")){
             user = new User();
        }
    }

    @Override
    public void endElement(final String uri, final String localName, final String name) {
        switch(name){
            case "user": users.add(user);
                break;
            case "firstname" : user.setFirstName(content);
                break;
            case "lastname" : user.setLastName(content);
                break;
        }
    }

    @Override
    public void characters(char []ch, int start,int length) {
        content = new String(ch, start, length);
    }

    public List<User> getUsers(){
        return users;
    }
}
