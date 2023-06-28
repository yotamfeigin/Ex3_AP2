package com.example.myapplication.adaptes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.User;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    class MessageViewHolder extends RecyclerView.ViewHolder{

        private final TextView singleMsg;
        private final TextView msgTime;
        private LinearLayout linearLayout;

        public MessageViewHolder(View itemView) {
            super(itemView);
            singleMsg = itemView.findViewById(R.id.singleMsg);
            msgTime = itemView.findViewById(R.id.msgTime);
            linearLayout = itemView.findViewById(R.id.allMsg);
        }
    }

    private final LayoutInflater mInflate;
    private List<Message> messages;
    private User user;

    public MessagesAdapter(Context context, List<Message> messages, User user) {
        mInflate = LayoutInflater.from(context);
        this.messages = messages;
        this.user = user;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = mInflate.inflate(R.layout.msg_layout, parent, false);
        return new MessageViewHolder(item);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if(messages != null){
            final Message current = messages.get(position);
            holder.singleMsg.setText(current.getContent());

            if(user.getUsername().equals(current.getSender_username())) {
                // Set the gradient drawable as the background
                holder.linearLayout.setBackgroundResource(R.drawable.msg2_background);
                holder.linearLayout.setGravity(Gravity.END);

            }
            holder.msgTime.setText(current.getCreated().substring(11,16));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 1.0f;
                params.gravity = Gravity.START;
                holder.linearLayout.setLayoutParams(params);
            }
        }


    @Override
    public int getItemCount() {
        if(messages != null)
            return messages.size();
        return 0;
    }

    public List<Message> getMessages(){
        return messages;
    }


    public void setMessages(List<Message> lsMsg){
        messages = lsMsg;
        notifyDataSetChanged();
    }
}