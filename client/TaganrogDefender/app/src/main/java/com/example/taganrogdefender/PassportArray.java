package com.example.taganrogdefender;

import android.app.Application;
import android.content.Context;
import java.util.ArrayList;

public class PassportArray extends Application {

    static private ArrayList<PassportItem> passports = null;

    public void loadArray(Context context)
    {
        passports =  null; // переделать
        if (passports == null)
        {
            passports =  new ArrayList<PassportItem>();
        }
    }

    public void add_new_passport(String name, String surname, Boolean is_VIP, Boolean is_participant, PassportItem.type_of_participant type)
    {
        passports.add(0, new PassportItem(name, surname, is_VIP, is_participant, type));
    }

    public ArrayList<PassportItem> getPassports()
    {
        return passports;
    }
}
