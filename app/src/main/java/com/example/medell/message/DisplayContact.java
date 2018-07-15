package com.example.medell.message;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/*
This is a custom adapter used to display details of the contacts
*/
public class DisplayContact extends ArrayAdapter {
    ArrayList<String> firstname,lastname,phone;
    private Activity context;


    public DisplayContact(Activity context, ArrayList firstname, ArrayList lastname, ArrayList phone)
    {
        super(context, R.layout.contacts, firstname);
        this.firstname = firstname;
        this.lastname=lastname;
        this.phone=phone;
        this.context=  context;
    }



	/*
	This method helps to display data in the custom layout. 
	*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.details, null, true);
        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        TextView id = (TextView) listViewItem.findViewById(R.id.phone);
        name.setText( firstname.get(position).toString()+" "+ lastname.get(position).toString());
        id.setText(String.valueOf(phone.get(position)));
        return listViewItem;
    }

}
