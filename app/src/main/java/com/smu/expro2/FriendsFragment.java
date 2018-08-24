package com.smu.expro2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class FriendsFragment extends Fragment {


    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    List<Friends> mfriend;
    FirebaseDatabase database;
    FriendsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends,container,false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_friends);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mfriend = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new FriendsAdapter(mfriend,getActivity());
        mRecyclerView.setAdapter(mAdapter);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                Log.d(TAG, "Value is: " + value);

                for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){

                    String value2 = dataSnapshot2.getValue().toString();
                    Log.d(TAG, "Value is: " + value2);
                    Friends friend = dataSnapshot2.getValue(Friends.class);

                    mfriend.add(friend);
                    mAdapter.notifyItemInserted(mfriend.size() - 1);
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return v;

    }

}