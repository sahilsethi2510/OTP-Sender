package com.example.medell.message;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


/*
This is a custom adapter to display message history
*/
public class MessegeDisplay extends ArrayAdapter {
    ArrayList<String> Contactname,Contactotp,Messagetime;
    private Activity context;
    public MessegeDisplay(Activity context, ArrayList name, ArrayList otp,ArrayList time)
    {
        super(context, R.layout.messages, name);
        this.context = context;
        this.Contactname = name;
        this.Contactotp=otp;
        this.Messagetime=time;
    }


	/*
	This method displays the details of message history into the list.
	*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.display_message, null, true);
        TextView name = (TextView) listViewItem.findViewById(R.id.contactname);
        TextView otp = (TextView) listViewItem.findViewById(R.id.contactotp);
        TextView time = (TextView) listViewItem.findViewById(R.id.time);
        name.setText( Contactname.get(position).toString());
        otp.setText("OTP: "+ Contactotp.get(position));
        time.setText(Messagetime.get(position).substring(0,5));
        return listViewItem;
    }


}
