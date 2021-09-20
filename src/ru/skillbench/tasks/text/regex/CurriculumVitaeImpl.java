package ru.skillbench.tasks.text.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurriculumVitaeImpl implements CurriculumVitae {
    private String text;
    private HashMap<String, String> hidden = new HashMap<String, String>();

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        if (this.text == null) {
            throw new IllegalStateException();
        }

        return this.text;
    }

    @Override
    public List<Phone> getPhones() {
        if (this.text == null) {
            throw new IllegalStateException();
        }

        List<Phone> phones = new ArrayList<>();

        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(this.getText());

        while (matcher.find()) {
            int areaCode = -1;
            int extension = -1;

            if (matcher.group(2) != null) {
                areaCode = Integer.parseInt(matcher.group(2));
            }

            if (matcher.group(7) != null) {
                extension = Integer.parseInt(matcher.group(7));
            }

            Phone newPhone = new Phone(matcher.group(), areaCode, extension);
            phones.add(newPhone);
        }

        return phones;
    }

    @Override
    public String getFullName() {
        if (this.text == null) {
            throw new IllegalStateException();
        }

        String namePattern = "([A-Z][a-z]*[.]*)\\s([A-Z][a-z]*[.]*)?\\s*([A-Z][a-z]*[.]*)";

        Pattern pattern = Pattern.compile(namePattern);
        Matcher matcher = pattern.matcher(this.getText());

        if (matcher.find()) {
            return matcher.group().trim();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public String getFirstName() {
        String fullName = this.getFullName();

        return fullName.split(" ")[0];
    }

    @Override
    public String getMiddleName() {
        String fullName = this.getFullName();

        if (fullName.split(" ").length == 3) {
            return fullName.split(" ")[1];
        } else {
            return null;
        }
    }

    @Override
    public String getLastName() {
        String fullName = this.getFullName();

        return fullName.split(" ")[fullName.split(" ").length - 1];
    }

    @Override
    public void updateLastName(String newLastName) {
        String lastName = this.getLastName();

        this.text = this.getText().replace(lastName, newLastName);
    }

    @Override
    public void updatePhone(Phone oldPhone, Phone newPhone) {
        if (this.text == null) {
            throw new IllegalStateException();
        }

        List<Phone> phones = this.getPhones();

        if (!this.getText().contains(oldPhone.getNumber())) {
            throw new IllegalArgumentException();
        }

        this.text = this.getText().replace(oldPhone.getNumber(), newPhone.getNumber());
    }

    @Override
    public void hide(String piece) {
        if (this.text == null) {
            throw new IllegalStateException();
        }

        if (!this.getText().contains(piece)) {
            throw new IllegalArgumentException();
        }

        String sourceString = piece.replaceAll("[^. @]", "X");

        this.hidden.put(sourceString, piece);
        this.text = this.getText().replace(piece, sourceString);
    }

    @Override
    public void hidePhone(String phone) {
        if(this.text == null) {
            throw new IllegalStateException();
        }

        if(!this.getText().contains(phone)) {
            throw new IllegalArgumentException();
        }

        String sourcePhone = phone.replaceAll("[\\d]", "X");

        this.hidden.put(sourcePhone, phone);
        this.text = this.getText().replace(phone, sourcePhone);
    }

    @Override
    public int unhideAll() {
        if(this.text == null) {
            throw new IllegalStateException();
        }

        int result = 0;

        for(HashMap.Entry<String, String> entry : this.hidden.entrySet()) {
            this.text = this.getText().replaceAll(entry.getKey().replaceAll("\\(", "\\\\\\(").replaceAll("\\)", "\\\\\\)"), entry.getValue());
            result++;
        }

        return result;
    }
}
