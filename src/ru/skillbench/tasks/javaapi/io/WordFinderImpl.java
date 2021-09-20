package ru.skillbench.tasks.javaapi.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class WordFinderImpl implements WordFinder {
    private String text;
    private HashSet<String> result;

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        this.text = text;
    }

    @Override
    public void setInputStream(InputStream is) throws IOException {
        if (is == null) {
            throw new IllegalArgumentException();
        }

        Scanner scanner = new Scanner(is);
        String stringText = scanner.nextLine();

        while (scanner.hasNextLine()) {
            stringText += (scanner.nextLine() + '\n');
        }

        this.setText(stringText);

        scanner.close();
    }

    @Override
    public void setFilePath(String filePath) throws IOException {
        if (filePath == null) {
            throw new IllegalArgumentException();
        }

        FileInputStream fileInputStream = new FileInputStream(filePath);

        this.setInputStream(fileInputStream);

        fileInputStream.close();
    }

    @Override
    public void setResource(String resourceName) throws IOException {
        if (resourceName == null) {
            throw new IllegalArgumentException();
        }

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);

        this.setInputStream(inputStream);

        if (inputStream != null) {
            inputStream.close();
        }
    }

    @Override
    public Stream<String> findWordsStartWith(String begin) {
        if (this.getText() == null) {
            throw new IllegalStateException();
        }

        if (begin == null) {
            begin = "";
        }

        HashSet<String> result = new HashSet<String>();

        Pattern pattern = Pattern.compile("\\p{Space}+", Pattern.CASE_INSENSITIVE);
        String[] tokens = pattern.split(this.getText().trim());
        Pattern patternBegin = Pattern.compile(begin + "[^\\p{Space}]+", Pattern.CASE_INSENSITIVE);

        for (String word : tokens) {
            if (patternBegin.matcher(word).matches()) {
                result.add(word.toLowerCase());
            }
        }

        this.result = result;

        return result.stream();
    }

    @Override
    public void writeWords(OutputStream os) throws IOException {
        if (this.getText() == null) {
            throw new IllegalStateException();
        }

        TreeSet<String> sortedResult = new TreeSet<String>(this.result);

        byte[] buffer;

        for (String str : sortedResult) {
            buffer = str.getBytes();
            os.write(buffer);

            if (sortedResult.last() != str) {
                os.write(' ');
            }
        }
    }
}
