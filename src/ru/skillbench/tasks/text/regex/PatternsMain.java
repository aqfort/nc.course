package ru.skillbench.tasks.text.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternsMain {
    public static void main(String[] args) {
        Patterns a = new PatternsImpl();

//    Matching href tag <a hReF="www.google.com"> failed. expected:<true> but was:<false>
//    Matching href tag <a hRef="myname"> failed. expected:<true> but was:<false>

        Pattern p = a.getEmailPattern();
        Matcher m = p.matcher("abc@007-agent.com");
        System.out.println(m.matches());
    }
}
