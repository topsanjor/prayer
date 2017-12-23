package com.kotlab.tibetanbuddhistprayer.fragments;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



/**
 * Created by Tsephel_Treps on 12/4/2017.
 */

public class XmlParser {

    public XmlParser()
    {


    }

    public Document getDomElement(String xml){

        Document doc = null;
        DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc =db.parse(is);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return doc;
    }


    public String getValue(Element item, String str){

        NodeList ndlist = item.getElementsByTagName(str);
        return this.getElementValue(ndlist.item(0));
    }

    private String getElementValue(Node item) {

        Node child;
        if(item!=null){
            if (item.hasChildNodes()){
                for (child = item.getFirstChild(); child!=null;child=item.getNextSibling()){

                    if(child.getNodeType()==Node.TEXT_NODE){

                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }


}
