package com.smu.expro2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

   EditText edit_text;
   Button button_send;
   String email;
    List<Chat> mChat;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance();


        //실시간 데이터베이스
        //getReference(폴더(?)이름).child(하위);
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("chats").child(formattedDate);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            email = user.getEmail();

        }

        Intent in = getIntent();
        final String stChatId = in.getStringExtra("friendUid");


        edit_text = (EditText)findViewById(R.id.text_edit);
        button_send = (Button)findViewById(R.id.send_button);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stText = edit_text.getText().toString();
                    if (stText.equals("") || stText.isEmpty()) {
                        Toast.makeText(ChatActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();

                    } else {
                        //실시간 데이터베이스를 날짜별로 저장하기 위해
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = df.format(c.getTime());


                    //DatabaseReference myRef = database.getReference("chats").child(formattedDate);
                    DatabaseReference myRef = database.getReference().child(stChatId).child("chats").child(formattedDate);


                    //해쉬맵으로 저장함.
                    //put(키,값)
                    Hashtable<String, String> chat = new Hashtable<String, String>();
                    chat.put("email", email);
                    chat.put("text", stText);
                    myRef.setValue(chat);
                    edit_text.setText("");
                }
            }
        });

        Button button_finish = (Button)findViewById(R.id.finish_button);
        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mChat = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(mChat,email,ChatActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        //DatabaseReference myRef = database.getInstance().getReference().child("chats");
        DatabaseReference myRef = database.getReference().child(stChatId).child("chats");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Chat chat = dataSnapshot.getValue(Chat.class);

                mChat.add(chat);
                mRecyclerView.scrollToPosition(mChat.size()-1);
                //-1이 되는 이유는 position은 0부터 시작.
                mAdapter.notifyItemInserted(mChat.size()-1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
