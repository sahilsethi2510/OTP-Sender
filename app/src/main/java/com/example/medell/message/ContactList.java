package com.example.medell.message;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;

public class ContactList extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int PERMISSION_ALL = 1;
	
	/*
	List of permission required.
	*/
    String[] PERMISSIONS = {Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

		/*
		This method checks whether the required permissions are given or not.
		*/
        if(!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }
		/*
		This method asks for the permissions from the user that re required.
		*/
		public static boolean hasPermissions(Context context, String... permissions) {
			if (context != null && permissions != null) {
				for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
						return false;
					}	
				}
			}
			return true;
		}

		/*
		Method to setup the tabs
		*/
		private void setupViewPager(ViewPager viewPager) {
			ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
			adapter.addFragment(new Contacts(), "CONTACTS");
			adapter.addFragment(new Messages(), "MESSAGES");
			viewPager.setAdapter(adapter);
		}

		/*
		Class to display Viewpager
		*/
		class ViewPagerAdapter extends FragmentPagerAdapter {
			private final List<Fragment> mFragmentList = new ArrayList<>();
			private final List<String> mFragmentTitleList = new ArrayList<>();

			public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}



