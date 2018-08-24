package com.smu.expro2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    List<Friends> friend;
    String stEmail;
    Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textview_friends_name;
        public ImageView image_friends;
        public Button button_chat;

        public ViewHolder(View itemView) {
            super(itemView);
            textview_friends_name = (TextView)itemView.findViewById(R.id.friends_name_textview);
            image_friends=(ImageView)itemView.findViewById(R.id.friends_image);
            button_chat=(Button)itemView.findViewById(R.id.chat_button);
        }
    }

    // 데이터 셋 제공
    public FriendsAdapter(List<Friends> friend, Context context) {
        this.friend = friend;
        this.context=context;
    }

    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        //새 뷰 생성
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_friend,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textview_friends_name.setText(friend.get(position).getEmail());

        String setPhoto = friend.get(position).getPhoto();
        if(TextUtils.isEmpty(setPhoto)){
            Picasso.get().load(R.mipmap.ic_launcher).fit().centerInside().into(holder.image_friends);
        }
        else{
            Picasso.get().load(setPhoto).fit().centerInside().into(holder.image_friends);
        }

        //Picasso.get().load(friend.get(position).getPhoto()).fit().centerInside().into(holder.image_friends);
        holder.button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stFriendId = friend.get(position).getKey();
                Intent in = new Intent(context, ChatActivity.class);
                in.putExtra("friendUid", stFriendId);
                context.startActivity(in);

            }
        });
    }



    @Override
    public int getItemCount() {
        return friend.size();
    }
}
