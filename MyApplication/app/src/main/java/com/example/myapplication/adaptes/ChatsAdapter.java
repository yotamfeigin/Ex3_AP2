package com.example.myapplication.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Chat;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

public interface RecycleViewClickListener {
    void onClick (Chat contact);
}

    private List<Chat> chats;
    private LayoutInflater inflater;
    private RecycleViewClickListener listener;

    public ChatsAdapter(List<Chat> chats, Context context, RecycleViewClickListener listener) {
        this.chats = chats;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

public class ChatsViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private TextView message;
    private TextView messageTime;
    private ImageView profilePic;
    public View view;

    public ChatsViewHolder(View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.contact_name);
        this.message = itemView.findViewById(R.id.contact_message);
        this.messageTime = itemView.findViewById(R.id.contact_massageTime);
        this.profilePic = itemView.findViewById(R.id.contact_profilePic);
        this.view = itemView;
    }

    public void bind(final Chat chat, final RecycleViewClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onClick(chat);
            }
        });
    }
}

    @NonNull
    @Override
    public ChatsAdapter.ChatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.contact_layout, parent, false);
        return new ChatsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatsViewHolder holder, int position) {
        String name = chats.get(position).getUser().getDisplayName();
        String message = chats.get(position).getLastMessage().getContent();
        String messageTime = chats.get(position).getLastMessage().getCreated();
        holder.name.setText(name);
        holder.message.setText(message);
        holder.messageTime.setText(messageTime);


        holder.bind(chats.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void setContacts(List<Chat> chatsList) {
        chats = chatsList;
        notifyDataSetChanged();
    }

    public List<Chat> getContacts() {
        return chats;
    }



}