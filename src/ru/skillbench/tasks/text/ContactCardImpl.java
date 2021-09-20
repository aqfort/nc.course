package ru.skillbench.tasks.text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

public class ContactCardImpl implements ContactCard {
    private String fullName;
    private String organization;
    private String gender;
    private Calendar birthDate;
    private HashMap<String, String> phoneNumber = new HashMap<>();

    public ContactCardImpl() {

    }

    @Override
    public ContactCard getInstance(Scanner scanner) {
        String s = scanner.nextLine();

        if (!s.equals("BEGIN:VCARD")) {
            throw new NoSuchElementException();
        }

        while (scanner.hasNextLine()) {
            s = scanner.nextLine();

            if (!s.contains(":")) {
                throw new InputMismatchException();
            }

            if (s.contains("FN")) {
                this.fullName = s.substring(3);
            } else if (s.contains("ORG")) {
                this.organization = s.substring(4);
            } else if (s.contains("GENDER")) {
                this.gender = s.substring(7);
            } else if (s.contains("BDAY")) {
                try {
                    this.birthDate = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    simpleDateFormat.setLenient(false);
                    this.birthDate.setTime(simpleDateFormat.parse(s.substring(5)));
                } catch (ParseException e) {
                    InputMismatchException mismatchException = new InputMismatchException();
                    mismatchException.initCause(e);
                    throw mismatchException;
                }
            } else if (s.contains("TEL")) {
                String number = s.substring(s.indexOf(":") + 1);

                if (!number.matches("\\d{10}")) {
                    throw new InputMismatchException();
                }

                this.phoneNumber.put(s.substring(s.indexOf("=") + 1, s.indexOf(":")), number);
            } else if (s.equals("END:VCARD")) {
                break;
            } else {
                throw new InputMismatchException();
            }
        }

        if (this.fullName == null || this.organization == null) {
            throw new NoSuchElementException();
        }

        return this;
    }

    @Override
    public ContactCard getInstance(String data) {
        return getInstance(new Scanner(data));
    }

    @Override
    public String getFullName() {
        return this.fullName;
    }

    @Override
    public String getOrganization() {
        return this.organization;
    }

    @Override
    public boolean isWoman() {
        if (this.gender == null) {
            return false;
        }

        return this.gender.equals("F");
    }

    @Override
    public Calendar getBirthday() {
        if (this.birthDate == null) {
            throw new NoSuchElementException();
        }

        return this.birthDate;
    }

    @Override
    public Period getAge() {
        if (this.birthDate == null) {
            throw new NoSuchElementException();
        }

        Calendar now = Calendar.getInstance();

        LocalDate birth = LocalDateTime.ofInstant(this.birthDate.toInstant(), this.birthDate.getTimeZone().toZoneId()).toLocalDate();
        LocalDate current = LocalDateTime.ofInstant(now.toInstant(), now.getTimeZone().toZoneId()).toLocalDate();

        return Period.between(birth, current);
    }

    @Override
    public int getAgeYears() {
        if (this.birthDate == null) {
            throw new NoSuchElementException();
        }

        return this.getAge().getYears();
    }

    @Override
    public String getPhone(String type) {
        if (!this.phoneNumber.containsKey(type)) {
            throw new NoSuchElementException();
        }
        StringBuilder stringBuilder = new StringBuilder(this.phoneNumber.get(type));
        stringBuilder.insert(0, "(");
        stringBuilder.insert(4, ")");
        stringBuilder.insert(5, " ");
        stringBuilder.insert(9, "-");
        return stringBuilder.toString();
    }
}
