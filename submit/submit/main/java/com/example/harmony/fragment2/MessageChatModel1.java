package com.example.harmony.fragment2;

public class MessageChatModel1 {
    private final String text;
    private final String time;
    private final int viewType;
    public MessageChatModel1(String text, String time, int viewType) {
        this.text = text;
        this.time = time;
        this.viewType = viewType;
    }
    public String getText() {
        return text;
    }
    public String getTime() {
        return time;
    }
    public int getViewType() {
        return viewType;
    }
}
