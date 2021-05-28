package com.example.taganrogdefender;

import org.json.JSONObject;

public interface request_interface {
    JSONObject POST(JSONObject jsonObject);
    JSONObject GET();
}
