package ru.skillbench.tasks.javax.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;

public class XPathCallerImpl implements XPathCaller {
    private String getStringExpressionBegining(String docType) {
        String stringExpressionBegining;

        switch (docType) {
            case "emp":
                stringExpressionBegining = "/content/emp/employee";
                break;
            case "emp-hier":
                stringExpressionBegining = "//employee";
                break;
            default:
                throw new IllegalArgumentException();
        }

        return stringExpressionBegining;
    }

    private Element[] getEmployeesUsingStringExpression(Document src, String stringExpression) {
        try {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression xPathExpression = xPath.compile(stringExpression);
            NodeList nodeList = (NodeList) xPathExpression.evaluate(src, XPathConstants.NODESET);
            Element[] employees = new Element[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) {
                employees[i] = (Element) nodeList.item(i);
            }

            return employees;
        } catch (Exception exception) {
            System.out.print(exception.getMessage());

            return null;
        }
    }

    @Override
    public Element[] getEmployees(Document src, String deptno, String docType) {
        String stringExpression = getStringExpressionBegining(docType);

        if (deptno != null) {
            stringExpression += "[@deptno=" + deptno + "]";
        }

        return getEmployeesUsingStringExpression(src, stringExpression);
    }

    @Override
    public String getHighestPayed(Document src, String docType) {
        return getHighestPayed(src, null, docType);
    }

    @Override
    public String getHighestPayed(Document src, String deptno, String docType) {
        Element[] employees = getEmployees(src, deptno, docType);

        double salary = 0;
        double maxSalary = 0;
        String name = "";

        for (Element employee : employees) {
            salary = Double.parseDouble(employee.getElementsByTagName("sal").item(0).getTextContent());

            if (maxSalary < salary) {
                maxSalary = salary;

                name = employee.getElementsByTagName("ename").item(0).getTextContent();
            }
        }

        return name;
    }

    @Override
    public Element[] getTopManagement(Document src, String docType) {
        String stringExpression = getStringExpressionBegining(docType);

        stringExpression += "[not(@mgr)]";

        return getEmployeesUsingStringExpression(src, stringExpression);
    }

    @Override
    public Element[] getOrdinaryEmployees(Document src, String docType) {
        String stringExpression;

        if (docType.equals("emp")) {
            stringExpression = "/content/emp/employee[not(@empno=(/content/emp/employee/@mgr))]";
        } else {
            stringExpression = "//employee[not(./employee)]";
        }

        return getEmployeesUsingStringExpression(src, stringExpression);
    }

    @Override
    public Element[] getCoworkers(Document src, String empno, String docType) {
        String stringExpression = getStringExpressionBegining(docType);

        if (docType.equals("emp")) {
            stringExpression = "/content/emp/employee[not(@empno=" + empno + ") and @mgr=(/content/emp/employee[@empno=" + empno + "]/@mgr)]";
        } else {
            stringExpression = "//employee[@empno=" + empno + "]/../employee[not(@empno=" + empno + ")]";
        }

        return getEmployeesUsingStringExpression(src, stringExpression);
    }
}
