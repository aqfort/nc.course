package ru.skillbench.tasks.text;

import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounterImpl implements WordCounter {
    private String text;

    public WordCounterImpl() {
        this.text = null;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return this.text;
    }

    private String deleteComments(String text) {
        Pattern pattern = Pattern.compile("<[^><]*>");
        Matcher matcher = pattern.matcher(text);
        String result = matcher.replaceAll("");

        if (text.equals(result)) {
            return result;
        } else {
            return deleteComments(result);
        }
    }

    @Override
    public Map<String, Long> getWordCounts() {
        if (this.getText() == null) {
            throw new IllegalStateException();
        }

        String text = deleteComments(this.getText().trim().toLowerCase());
        Pattern pattern = Pattern.compile("\\p{Space}+");
        String[] words = pattern.split(text);

        Map<String, Long> result = new TreeMap<>();

        for (String word : words) {
            result.putIfAbsent(word, 0L);
            result.put(word, result.get(word) + 1L);
        }

        return result;
    }

    @Override
    public List<Map.Entry<String, Long>> getWordCountsSorted() {
        if (this.getText() == null) {
            throw new IllegalStateException();
        }

        Map<String, Long> map = this.getWordCounts();

        Comparator<Map.Entry<String, Long>> comparator = new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        };

        return this.sort(map, comparator);
    }

    @Override
    public <K extends Comparable<K>, V extends Comparable<V>> List<Map.Entry<K, V>> sort(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(comparator);
        return list;
    }

    @Override
    public <K, V> void print(List<Map.Entry<K, V>> entries, PrintStream ps) {
        for (Map.Entry<K, V> item : entries) {
            ps.println(item.getKey() + " " + item.getValue());
        }
    }
}
