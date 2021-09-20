package ru.skillbench.tasks.javax.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;

public class XPathCallerMain {
    public static void main(String[] args) {
        XPathCaller a = new XPathCallerImpl();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse("src/ru/skillbench/tasks/javax/xml/emp-hier.xml");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

//        System.out.println(a.getHighestPayed(document, "10", "emp-hier"));

        Element[] employees = a.getOrdinaryEmployees(document, "emp-hier");

//        System.out.println(Arrays.toString(employees));

        for (Element employee : employees) {
            System.out.println(employee.getAttribute("empno") + ' ' + employee.getElementsByTagName("ename").item(0).getTextContent());
        }
    }
}
