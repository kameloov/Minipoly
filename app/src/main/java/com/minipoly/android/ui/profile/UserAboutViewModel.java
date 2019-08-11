package com.minipoly.android.ui.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Review;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.repository.SocialRepository;

import java.util.List;

public class UserAboutViewModel extends ViewModel {

    public MutableLiveData<Integer> section = new MutableLiveData<>(1);
    public MutableLiveData<List<UserBrief>> users = new MutableLiveData<>();
    public MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
    private String userId;

    public UserAboutViewModel(String userId) {
        this.userId = userId;
        setSection(1);
    }

    public void setSection(int value) {
        section.setValue(value);
        switch (value) {
            case 0:
                loadReviews();
                break;
            case 2:
                loadFollowers();
                break;
            case 1:
                loadFollowing();
                break;
            case 3:
                loadBrokage();
                break;
        }
    }

    private void loadReviews() {
        SocialRepository.getReviews(userId, (success, data) -> {
            if (success && data != null)
                reviews.setValue(data);
        });
    }


    private void loadFollowers() {
        SocialRepository.getFollowers(userId, (success, data) -> {
            if (success)
                users.setValue(data);
        });
    }

    private void loadFollowing() {
        SocialRepository.getFollowing(userId, (success, data) -> {
            if (success)
                users.setValue(data);
        });
    }

    private void loadBrokage() {

    }

}
