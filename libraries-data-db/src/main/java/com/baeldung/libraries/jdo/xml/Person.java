package com.baeldung.libraries.jdo.xml;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.List;

@PersistenceCapable
public class Person {
    private long personNum;

    @PrimaryKey
    private String firstName;
    private String lastName;

//    private List<String> phoneNumbers = new ArrayList<String>();
    private String phoneNumbers = "";

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Person(long personNum, String firstName, String lastName) {
        super();
        this.personNum = personNum;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getPersonNum() {
        return personNum;
    }

    public void setPersonNum(long personNum) {
        this.personNum = personNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public List<String> getPhoneNumbers() {
//        return phoneNumbers;
//    }

//    public void setPhoneNumbers(List<String> phoneNumbers) {
//        this.phoneNumbers = phoneNumbers;
//    }

}
