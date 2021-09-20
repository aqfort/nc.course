package ru.skillbench.tasks.javaapi.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

public class StringFilterImpl implements StringFilter {
    private HashSet<String> data;

    public StringFilterImpl() {
        this.data = new HashSet<String>();
    }

    @Override
    public void add(String s) {
        if (s == null) {
            this.data.add(null);
        } else {
            this.data.add(s.toLowerCase());
        }
    }

    @Override
    public boolean remove(String s) {
        if (s == null) {
            return this.data.remove(null);
        } else {
            return this.data.remove(s.toLowerCase());
        }
    }

    @Override
    public void removeAll() {
        this.data.clear();
    }

    @Override
    public Collection<String> getCollection() {
        return this.data;
    }

    @Override
    public Iterator<String> getStringsContaining(String chars) {
        if (chars == null || chars.isEmpty()) {
            return this.data.iterator();
        } else {
            HashSet<String> result = new HashSet<String>();

            for (String element : this.data) {
                if (element != null && element.contains(chars.toLowerCase())) {
                    result.add(element);
                }
            }

            return result.iterator();
        }
    }

    @Override
    public Iterator<String> getStringsStartingWith(String begin) {
        if (begin == null || begin.isEmpty()) {
            return this.data.iterator();
        } else {
            HashSet<String> result = new HashSet<String>();

            for (String element : this.data) {
                if (element != null && element.startsWith(begin.toLowerCase())) {
                    result.add(element);
                }
            }

            return result.iterator();
        }
    }

    @Override
    public Iterator<String> getStringsByNumberFormat(String format) {
        if (format == null || format.isEmpty()) {
            return this.data.iterator();
        } else {
            HashSet<String> result = new HashSet<String>();

            StringBuilder stringBuilder = new StringBuilder();

            char[] formatChars = new char[format.length()];

            for (int i = 0; i < format.length(); i++) {
                if (format.charAt(i) == '#') {
                    stringBuilder.append("\\d");
                } else {
                    stringBuilder.append('[');
                    stringBuilder.append(format.charAt(i));
                    stringBuilder.append(']');
                }
            }

            String regex = stringBuilder.toString();

            for (String element : this.data) {
                if (element != null && element.matches(regex)) {
                    result.add(element);
                }
            }

            return result.iterator();
        }
    }

    @Override
    public Iterator<String> getStringsByPattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return this.data.iterator();
        } else {
            HashSet<String> result = new HashSet<String>();

            StringBuilder stringBuilder = new StringBuilder();

            char[] formatChars = new char[pattern.length()];

            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) == '*') {
                    stringBuilder.append(".*");
                } else {
                    stringBuilder.append('[');
                    stringBuilder.append(pattern.charAt(i));
                    stringBuilder.append(']');
                }
            }

            String regex = stringBuilder.toString();

            for (String element : this.data) {
                if (element != null && element.matches(regex)) {
                    result.add(element);
                }
            }

            return result.iterator();
        }
    }
}
