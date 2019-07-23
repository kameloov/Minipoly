package com.minipoly.android.ui.realestate_details;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.minipoly.android.entity.Comment;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.ChatRepository;
import com.minipoly.android.repository.CommentRepository;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.SocialRepository;
import com.minipoly.android.utils.LocalData;

import java.util.ArrayList;
import java.util.List;

public class RealestateDetailsViewModel extends ViewModel {
    private MutableLiveData<Realestate> relestate = new MutableLiveData<>();
    public MutableLiveData<Integer> currentImage = new MutableLiveData<>();
    private FireLiveQuery<Comment> comments;
    public MutableLiveData<String> comment = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<Boolean> like = new MutableLiveData<>();
    public MutableLiveData<Boolean> dislike = new MutableLiveData<>();
    public MutableLiveData<Boolean> adding = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> following = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> watching = new MutableLiveData<>(false);
    public List<String> tags;

    public RealestateDetailsViewModel(Realestate r) {
        currentImage.setValue(0);
        comments = CommentRepository.loadComments(r.getId());
        loading.setValue(false);
        like.setValue(false);
        dislike.setValue(false);
        this.relestate.setValue(r);
        SocialRepository.following(r.getUserBrief().getId(), (success, data) -> following.setValue(data));
        sync();
        RealestateRepository.isFollowing(relestate.getValue().getId(), (success, data) -> watching.setValue(success && data));
        prepareTags();
        RealestateRepository.updateViews(r.getId());
    }


    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    public void order(View v) {
        ChatRepository.openConversation(relestate.getValue(), (success, id) -> {
            if (success) {
                RealestateDetailsDirections.DetailsToChat detailsToChat = RealestateDetailsDirections.detailsToChat();
                detailsToChat.setConversationId(id);
                Navigation.findNavController(v).navigate(detailsToChat);
            }
        });
    }


    public void notifyFriends(View v) {
        Navigation.findNavController(v).navigate(RealestateDetailsDirections.advrtUserList());
    }

    private void prepareTags() {
        ArrayList<String> list = new ArrayList<>();
        tags = list;

    }

    public void toggleWatch() {
        if (watching.getValue()) {
            RealestateRepository.unfollow(relestate.getValue().getId(), success -> {
                if (success)
                    watching.setValue(false);
            });
        } else {
            RealestateRepository.follow(relestate.getValue().getId(), success -> {
                if (success)
                    watching.setValue(true);
            });
        }

    }

    private void sync() {
        SocialRepository.liked(getRealestate().getValue().getId(),
                (success, data) -> like.setValue(success && data));
        SocialRepository.disliked(getRealestate().getValue().getId(),
                (success, data) -> dislike.setValue(success && data));
    }


    public void like() {
        SocialRepository.like(relestate.getValue().getId(), false,
                (success, data) -> {
                    if (success) {
                        like.setValue(data);
                        int i = data ? 1 : -1;
                        Realestate r = relestate.getValue();
                        r.setLike(r.getLike() + i);
                        relestate.setValue(r);
                    }
                    sync();
                });
    }

    private void toggleFollow() {
        boolean a = following.getValue();
        following.setValue(!a);
    }

    public void changeFollow(String id) {

        if (following.getValue())
            SocialRepository.unFollow(id, success -> {
                if (success)
                    toggleFollow();
            });
        else {
            SocialRepository.follow(id, success -> {
                if (success)
                    toggleFollow();
            });
        }

    }

    public void dislike() {
        SocialRepository.dislike(relestate.getValue().getId(), false,
                (success, data) -> {
                    if (success) {
                        dislike.setValue(data);
                        int i = data ? 1 : -1;
                        Realestate r = relestate.getValue();
                        r.setDislike(r.getDislike() + i);
                        relestate.setValue(r);
                    }
                    sync();
                });
    }

    public void addComment() {
        if (comment.getValue() == null || comment.getValue().isEmpty())
            return;
        adding.setValue(true);
        Comment c = new Comment();
        c.setAdvrtId(relestate.getValue().getId());
        c.setText(comment.getValue());
        c.setUserBrief(new UserBrief(LocalData.getUserInfo()));
        CommentRepository.addComment(c, success -> {
            adding.setValue(false);
            comment.setValue("");
        });
    }

    public LiveData<Realestate> getRealestate() {
        return relestate;
    }
}
