package ru.skillbench.tasks.javax.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringWriter;

public class SimpleXMLImpl implements SimpleXML {
    @Override
    public String createXML(String tagName, String textNode) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement(tagName);
            root.appendChild(document.createTextNode(textNode));
            document.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter stringWriter = new StringWriter();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(stringWriter);
            transformer.transform(domSource, streamResult);

            return stringWriter.toString();
        } catch (Exception exception) {
            System.out.println(exception.toString());

            return exception.getMessage();
        }
    }

    @Override
    public String parseRootElement(InputStream xmlStream) throws SAXException {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

            saxParserFactory.setValidating(true);
            saxParserFactory.setNamespaceAware(false);
            SAXParser saxParser = saxParserFactory.newSAXParser();

            class MyHandler extends DefaultHandler {
                private String rootName;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (rootName == null)
                        rootName = qName;
                }

                public String getRootName() {
                    return rootName;
                }
            }
            ;

            MyHandler myHandler = new MyHandler();

            saxParser.parse(xmlStream, myHandler);

            return myHandler.getRootName();
        } catch (Exception exception) {
            System.out.println(exception.toString());

            return exception.getMessage();
        }
    }
}
