package com.example.taganrogdefender;

import android.util.JsonReader;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class request_real implements request_interface {
    @Override
    public JSONObject POST(String url, String json) {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        final JSONObject[] resp = {null};
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    JSONObject myResponse = null;
                    try {
                        resp[0] = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    countDownLatch.countDown();
                    return;
                }
                else
                {
                    countDownLatch.countDown();
                    return;
                }
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resp[0];
    }

    @Override
    public JSONObject GET() {
        return null;
    }
}
