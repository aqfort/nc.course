package ru.skillbench.tasks.text.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PatternsImpl implements Patterns {
    @Override
    public Pattern getSQLIdentifierPattern() {
        return Pattern.compile("[a-zA-Z][a-zA-Z0-9_]{0,29}");
    }

    @Override
    public Pattern getEmailPattern() {
        return Pattern.compile("([a-zA-Z0-9][a-zA-Z0-9_.-]{0,20}[a-zA-Z0-9])@((([a-zA-Z0-9]+(-[a-zA-Z0-9]+)*){2,}\\.){1,})(ru|com|net|org)$");
    }

    @Override
    public Pattern getHrefTagPattern() {
        return Pattern.compile("<[\t\n\r\f ]*(A|a)[\t\n\r\f ]*[H|h][R|r][E|e][F|f][\t\n\r\f ]*=[\t\n\r\f ]*([\\S]|\"[^\"]+\")+[\t\n\r\f ]*/?>");
    }

    @Override
    public List<String> findAll(String input, Pattern pattern) {
        List<String> result = new ArrayList<String>();

        if (input != null && pattern != null) {
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                result.add(matcher.group());
            }
        }

        return result;
    }

    @Override
    public int countMatches(String input, String regex) {
        int count = 0;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input.toLowerCase());

        while (matcher.find()) {
            count++;
        }

        return count;
    }
}
