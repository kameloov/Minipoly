package com.minipoly.android.ui.comments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Comment;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.CommentRepository;
import com.minipoly.android.utils.LocalData;

import java.util.List;

public class CommentsViewModel extends ViewModel {

    public MutableLiveData<String> comment = new MutableLiveData<>();
    public MutableLiveData<Boolean> adding = new MutableLiveData<>(false);
    private Realestate realestate;
    private FireLiveQuery<Comment> comments;

    public CommentsViewModel(Realestate realestate) {
        this.realestate = realestate;
        comments = CommentRepository.loadComments(realestate.getId());
    }


    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    public void addComment() {
        if (comment.getValue() == null || comment.getValue().isEmpty())
            return;
        adding.setValue(true);
        Comment c = new Comment();
        c.setAdvrtId(realestate.getId());
        c.setText(comment.getValue());
        c.setUserBrief(new UserBrief(LocalData.getUserInfo()));
        CommentRepository.addComment(c, success -> {
            adding.setValue(false);
            comment.setValue("");
        });
    }
}
