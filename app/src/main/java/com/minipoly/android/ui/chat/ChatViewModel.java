package com.minipoly.android.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Conversation;
import com.minipoly.android.entity.Message;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.ChatRepository;

import java.util.List;

public class ChatViewModel extends ViewModel {
    private String conversationId;
    private FireLiveQuery<Message> messages;
    public MutableLiveData<String> text = new MutableLiveData<>("");
    public FireLiveDocument<Conversation> conversation;

    public ChatViewModel(String conversationId) {
        this.conversationId = conversationId;
        messages = ChatRepository.watchConversation(conversationId);
        conversation = ChatRepository.getConversation(conversationId);
    }

    public void sendMessage() {
        String txt = text.getValue();
        if (txt != null && !txt.isEmpty())
            ChatRepository.sendMessage(txt, conversationId, success -> {
                text.setValue("");
            });
    }


    public LiveData<List<Message>> getMessages() {
        return messages;
    }
}
