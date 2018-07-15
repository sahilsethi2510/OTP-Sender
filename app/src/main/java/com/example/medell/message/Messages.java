package com.example.medell.message;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Messages  extends Fragment {
    ListView messgeList;
    DatabaseHelper databaseHelper;
    ArrayList name,otp,time;
    Cursor cursor;

    public Messages() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name=new ArrayList();
        otp=new ArrayList();
        time=new ArrayList();
        databaseHelper=new DatabaseHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messages, container, false);
        messgeList = (ListView) view.findViewById(R.id.messageList);
        databaseHelper=new DatabaseHelper(getActivity());
            viewMessages();
        return view;
    }

	/*
	This method retrieves the data of the message history from the data base and displays into the list with the help custom adapter	
	*/
    private void viewMessages() {
        name.clear();
        otp.clear();
        time.clear();
        cursor = databaseHelper.gethistoryData();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            name.add(cursor.getString(cursor.getColumnIndex("name")));
            otp.add(cursor.getString(cursor.getColumnIndex("otp")));
            time.add(cursor.getString(cursor.getColumnIndex("timeofsend")));
            cursor.moveToNext();
        }
        if (!cursor.isClosed()) {
            cursor.isClosed();
        }
        MessegeDisplay messegeDisplay=new MessegeDisplay(getActivity(),name,otp,time);
        messgeList.setAdapter(messegeDisplay);
    }
}
