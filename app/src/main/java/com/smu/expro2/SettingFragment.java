package com.smu.expro2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class SettingFragment extends Fragment {

    Button button_my_profile;
    Button button_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        button_my_profile = (Button) v.findViewById(R.id.my_profile_button);
        button_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyPageActivity.class);
                startActivity(i);
            }
        });



        button_logout = (Button)v.findViewById(R.id.logout_button);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getActivity(),MainActivity.class);
                startActivity(in);
            }
        });
    return v;
    }
}
