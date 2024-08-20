package com.example.harmony.fragments;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harmony.R;

import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter {
    List<MessageChatModel> messageChatModelList;
    Activity context;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    public MessageChatAdapter(List<MessageChatModel> messageChatModelList, FragmentActivity context) {
        this.messageChatModelList = messageChatModelList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        MessageChatModel message = (MessageChatModel) messageChatModelList.get(position);
        if (message.getViewType() == 0) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.send_layout, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.receive_layout, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MessageChatModel message = messageChatModelList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messageChatModelList.size();
    }
    private static class SentMessageHolder extends RecyclerView.ViewHolder{
        TextView message;
        TextView time;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            message = (TextView)itemView.findViewById(R.id.message);
            time = (TextView)itemView.findViewById(R.id.time);
        }

        void bind(MessageChatModel messageModel) {
            message.setText(messageModel.getText());
            time.setText(messageModel.getTime());
        }

    }
    private static class ReceivedMessageHolder extends RecyclerView.ViewHolder{
        TextView message;
        TextView time;
        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            message = (TextView)itemView.findViewById(R.id.message);
            time = (TextView)itemView.findViewById(R.id.time);
        }

        void bind(MessageChatModel messageModel){
            message.setText(messageModel.getText());
            time.setText(messageModel.getTime());
        }
    }
}
