package com.smu.expro2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
    List<Chat> mChat;
    String my_Email;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.mTextView);
        }
    }

    public MyAdapter(List<Chat> mChat, String email, Context context) {
        this.mChat = mChat;
        my_Email=email;
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        if(mChat.get(position).email.equals(my_Email)){
            return 1;
        }
        else { return 2; }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v;
        if(viewType==1){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.right_text_view, parent, false);
        }
        else{
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_text_view, parent, false);
        }

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mChat.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }
}
