package com.example.taganrogdefender;

import java.util.Map;
import java.util.TreeMap;

public class PassportItem {
    private String Name;
    private String Surname;
    private Boolean Is_VIP;
    private Boolean Is_guest;
    private Boolean IsRent;
    private int ClothesSize;
    public enum type_of_participant {
        Sport,
        Reconstruction
    };

    private type_of_participant Type;

    public PassportItem(String name, String surname, Boolean is_VIP, Boolean is_guest,
                        type_of_participant type, boolean isRent, int size) {
        Name = name;
        Surname = surname;
        Is_VIP = is_VIP;
        Is_guest = is_guest;
        Type = type;
        IsRent = isRent;
        ClothesSize = size;
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

    Boolean get_is_guest () {
        return Is_guest;
    }

    type_of_participant get_type () {
        return Type;
    }

    Boolean get_is_rent () {
        return IsRent;
    }

    int get_size () {
        return ClothesSize;
    }
}
