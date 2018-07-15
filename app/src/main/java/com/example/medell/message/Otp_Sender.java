package com.example.medell.message;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

import static android.Manifest.permission.SEND_SMS;

public class Otp_Sender extends AppCompatActivity {
    String name,number,hour,minute,second;;
    TextView otp;
    int random_otp,insert_flag=0;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__sender);
        name=getIntent().getStringExtra("name");
        number=getIntent().getStringExtra("number");
        otp=(TextView) findViewById(R.id.otp);
        Random random=new Random();
        random_otp=random.nextInt(99999)+99999;
        otp.setText("Your OTP is "+random_otp);
        databaseHelper=new DatabaseHelper(this);
    }

    public void send(View view) {
        insert_flag=1;
        sendSms();
    }

	/*
	This method helps to send the messages directly from the application.
	*/
    private void sendSms() {
        String sent = "SMS_SENT";
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(sent), 0);
        // callback on sending SMS
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                // if message sent successfully
                if(getResultCode() == Activity.RESULT_OK) {
                    Toast.makeText(getBaseContext(), "Message sent successfully.", Toast.LENGTH_SHORT).show();
                    otp.setText("");

					/*
					The value of hour, minute and second are compared to 10 as to display the time correctly.
					*/
                    int h=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    if(h<10) {
                         hour = "0"+String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    }
                    else{
                         hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    }

                    int m=Calendar.getInstance().get(Calendar.MINUTE);
                    if(m<10) {
                         minute = "0"+String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
                    }
                    else{
                         minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
                    }
                    int s=Calendar.getInstance().get(Calendar.SECOND);
                    if(s<10) {
                         second = "0"+String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
                    }
                    else{
                        second = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
                    }
					
					/*
					The insert flag is used as the broadcast receiver can receive more than one requests at times 
					*/
                   if(insert_flag==1){
                       databaseHelper.insertMessageDetails(name,number,String.valueOf(random_otp),hour+":"+minute+":"+second);
                       insert_flag=0;
                       Intent intent=new Intent(Otp_Sender.this,ContactList.class);
                       startActivity(intent);
                   }
                }
                else {
                    Toast.makeText(getBaseContext(), "Sending message failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new IntentFilter(sent));
      
		SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, otp.getText().toString(), sentPI, null);
    }
}
