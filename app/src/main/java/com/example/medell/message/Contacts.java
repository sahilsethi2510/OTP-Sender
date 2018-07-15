package com.example.medell.message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Contacts extends Fragment {

    ArrayList<String> first_name,last_name,phone_number;
    ListView contact;


    public Contacts() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        first_name=new ArrayList<>();
        last_name=new ArrayList<>();
        phone_number=new ArrayList<>();


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts, container, false);
        contact = (ListView) view.findViewById(R.id.contactsList);
        loadJSONFromAsset();
        initList();

        return view;
    }

	/*
	This method loads the JSON from the assets folder
	*/
    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getActivity().getAssets().open("contacts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

	/*
	This method splits the JSON array and retrieves the name and mobile numbers from the JSON.
	Then it displays into the ListView by the help of DisplayContact Custom ArrayAdapter.
	On clicking the item of the list it intents the name and number of the selected contact to the OTP_Sender Activity 
	and moves the application to the OTP_Sender Activity.
	*/
    private void initList() {

        try {

            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String firstName = jsonObject.getString("first_name");
                String lastName = jsonObject.getString("last_name");
                String phone = jsonObject.getString("phone");
                first_name.add(firstName);
                last_name.add(lastName);
                phone_number.add(phone);
            }
            DisplayContact newcontact= new DisplayContact(getActivity(),first_name,last_name,phone_number);
            contact.setAdapter(newcontact);
            contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(getActivity(),Otp_Sender.class);
                    intent.putExtra("name",first_name.get(position)+" "+last_name.get(position));
                    intent.putExtra("number",phone_number.get(position));
                    startActivity(intent);
                }
            });

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }


}
