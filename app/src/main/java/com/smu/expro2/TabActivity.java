package com.smu.expro2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class TabActivity extends AppCompatActivity {

    private TextView mTextMessage;
    long lastPressed;
    Fragment fragment;


    //OnNavigationItemSelectedListener : 이 인터페이스를 구현하면 사용자가 내비게이션 드로워의 항목을 클릭했을때
    //엑티비티가 이에 응답할 수 있다.
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_friends:
                    fragment = new FriendsFragment();
                    switchFragment(fragment);
                     return true;

                case R.id.navigation_chat:

                    fragment = new ChatFragment();
                    switchFragment(fragment);
                    return true;

                case R.id.navigation_setting:
                    fragment = new SettingFragment();
                    switchFragment(fragment);
                    return true;

        }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FriendsFragment fragment = new FriendsFragment();
        fragmentTransaction.add(R.id.Fcontent,fragment);
        fragmentTransaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void switchFragment(Fragment fragment){
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Fcontent,fragment);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //System.currentTimeillis() : 현재시간 ,  1500 : 1.5초
        if(System.currentTimeMillis() - lastPressed < 1500){
            finish();
        }
        else{
            Toast.makeText(this,"한 번 더 누르면 종료 됩니다.",Toast.LENGTH_SHORT).show();
        }
        lastPressed = System.currentTimeMillis();
    }
}
