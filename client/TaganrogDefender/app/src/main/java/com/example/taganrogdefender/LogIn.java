package com.example.taganrogdefender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LogIn extends AppCompatActivity {

    Button btn_log,
            btn_reg,
            tab_log,
            tab_reg;
    EditText firstname_line,
            secondname_line,
            phone_line,
            password_line,
            email_line;

    LinearLayout firstname_block,
            secondname_block,
            phone_block,
            password_block,
            email_block;

    Boolean is_login = true;
    Boolean is_registration = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        btn_log = findViewById(R.id.btn_log);
        btn_reg = findViewById(R.id.btn_reg);
        tab_log = findViewById(R.id.log_tab);
        tab_reg = findViewById(R.id.reg_tab);

        firstname_line = findViewById(R.id.firstname_line);
        secondname_line = findViewById(R.id.secondname_line);
        phone_line = findViewById(R.id.phone_line);
        password_line = findViewById(R.id.password_line);
        email_line = findViewById(R.id.email_line);

        firstname_block = findViewById(R.id.firstname_block);
        secondname_block = findViewById(R.id.secondname_block);
        phone_block = findViewById(R.id.phone_block);
        password_block = findViewById(R.id.password_block);
        email_block = findViewById(R.id.email_block);

        tab_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_login();
            }
        });

        tab_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_registration();
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    void set_login() {
        is_login = true;
        is_registration = false;

        tab_log.setTextColor(0xffffffff);
        tab_reg.setTextColor(0xff717171);

        btn_log.setVisibility(View.VISIBLE);
        btn_reg.setVisibility(View.GONE);

        firstname_block.setVisibility(View.GONE);
        secondname_block.setVisibility(View.GONE);
        phone_block.setVisibility(View.GONE);
        password_block.setVisibility(View.VISIBLE);
        email_block.setVisibility(View.VISIBLE);
    }

    void set_registration() {
        is_login = false;
        is_registration = true;

        tab_reg.setTextColor(0xffffffff);
        tab_log.setTextColor(0xff717171);

        btn_log.setVisibility(View.GONE);
        btn_reg.setVisibility(View.VISIBLE);

        firstname_block.setVisibility(View.VISIBLE);
        secondname_block.setVisibility(View.VISIBLE);
        phone_block.setVisibility(View.VISIBLE);
        password_block.setVisibility(View.VISIBLE);
        email_block.setVisibility(View.VISIBLE);
    }


}