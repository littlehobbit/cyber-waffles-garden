package com.example.taganrogdefender;

import java.util.Map;
import java.util.TreeMap;

public class PassportItem {
    private String Name;
    private String Surname;
    private Boolean Is_VIP;
    private Boolean Is_participant;
    public enum type_of_participant {
        Sport,
        Reconstruction
    };

    private type_of_participant Type;

    public PassportItem(String name, String surname, Boolean is_VIP, Boolean is_participant, type_of_participant type) {
        Name = name;
        Surname = surname;
        Is_VIP = is_VIP;
        Is_participant = is_participant;
        Type = type;
    }

    String get_name () {
        return Name;
    }

    String get_surname () {
        return Surname;
    }

    Boolean get_is_VIP () {
        return Is_VIP;
    }

    Boolean get_is_participant () {
        return Is_participant;
    }


}
