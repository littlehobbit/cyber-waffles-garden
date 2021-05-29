package com.example.taganrogdefender;

import org.json.JSONObject;

public interface request_interface {
    JSONObject POST(String url, String json);
    JSONObject GET(String url);
}
