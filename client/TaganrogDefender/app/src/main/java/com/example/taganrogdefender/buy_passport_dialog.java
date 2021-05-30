package com.example.taganrogdefender;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class buy_passport_dialog extends AppCompatDialogFragment {

    Spinner spinner_is_guest;
    Spinner spinner_type;
    Spinner spinner_vip;
    Spinner spinner_rent;

    LinearLayout new_passport_type;

    EditText name;
    EditText surname;
    EditText size;

    Button btn;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_new_passport, null);

        spinner_is_guest = view.findViewById(R.id.spinner_is_guest);
        spinner_type = view.findViewById(R.id.spinner_type);
        spinner_vip = view.findViewById(R.id.spinner_vip);
        spinner_rent = view.findViewById(R.id.spinnet_is_rent);

        name = view.findViewById(R.id.name_new_passport);
        surname = view.findViewById(R.id.surname_new_passport);
        size = view.findViewById(R.id.clothes_size);

        new_passport_type = view.findViewById(R.id.new_passport_type);

        btn = view.findViewById(R.id.btn_buy_new_passport);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.is_guest));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_is_guest.setAdapter(myAdapter);

        myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(myAdapter);

        myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.yes_no));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_vip.setAdapter(myAdapter);

        myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.yes_no));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rent.setAdapter(myAdapter);

        spinner_is_guest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    new_passport_type.setVisibility(View.GONE);
                } else if (i == 1) {
                    new_passport_type.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_rent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    size.setVisibility(View.VISIBLE);
                } else if (i == 1) {
                    size.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        builder.setView(view);

        return builder.create();
    }
}
