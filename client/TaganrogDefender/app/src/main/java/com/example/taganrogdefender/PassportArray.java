package com.example.taganrogdefender;

import android.app.Application;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PassportArray extends Application {

    static private ArrayList<PassportItem> passports = null;
    RequestRealWithAuthorisationToken request = new RequestRealWithAuthorisationToken();

    public void loadArray(Context context)
    {
        passports = new ArrayList<>(); // мб из-за этого?
        JSONObject json = request.GET("http://"+ LogIn.server_ip + "/passports");
        try {
            JSONArray jsonPassports = json.getJSONArray("res");
            for (int i = 0; i < jsonPassports.length(); i++) {
                JSONObject total = jsonPassports.getJSONObject(i);
                String name = total.getString("FIRSTNAME");
                String surname = total.getString("SECONDNAME");
                boolean isVip = total.getInt("ISVIP") != 0;
                boolean isGuest = total.getInt("ISGUEST") != 0;
                String part = total.getString("PARTTYPE");
                PassportItem.type_of_participant type;
                if (part.equals(PassportItem.type_of_participant.Sport.toString())) {
                    type = PassportItem.type_of_participant.Sport;
                } else {
                    type = PassportItem.type_of_participant.Reconstruction;
                }
                boolean isRent = total.getInt("ISRENT") != 0;
                int size = total.getInt("SIZE");
                passports.add(new PassportItem(name, surname, isVip, isGuest, type, isRent, size));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void add_new_passport(String name, String surname, Boolean is_VIP,
                                 Boolean is_participant, PassportItem.type_of_participant type,
                                 boolean isRent, int size)
    {
        passports.add(0, new PassportItem(name, surname, is_VIP,
                is_participant, type, isRent, size));
    }

    public ArrayList<PassportItem> getPassports()
    {
        return passports;
    }
}
