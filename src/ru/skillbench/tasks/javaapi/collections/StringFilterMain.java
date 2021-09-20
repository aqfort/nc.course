package ru.skillbench.tasks.javaapi.collections;

import java.util.Iterator;

public class StringFilterMain {
    public static void main(String[] args) {
        StringFilter a = new StringFilterImpl();

        a.add("stryes");
        a.add("yesstr");
        a.add("stryesstr");
        a.add("yesstryes");
        a.add("stryesstryes");
        a.add("yesstryesstr");
        a.add("(800)5553535");
        a.add("313373");
        a.add("69.420");
        a.add(null);

        for (String s : a.getCollection()) {
            System.out.println(s);
        }

        System.out.println("");

        Iterator<String> it = a.getStringsStartingWith("yes");
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        System.out.println("");

        it = a.getStringsContaining("3");
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        System.out.println("");

        it = a.getStringsByNumberFormat("(###)#######");
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        System.out.println("");

        a.add("distribute");

        it = a.getStringsByPattern("di*bute");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
