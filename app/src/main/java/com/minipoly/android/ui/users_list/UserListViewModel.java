package com.minipoly.android.ui.users_list;

import androidx.lifecycle.ViewModel;

import com.minipoly.android.UserManager;
import com.minipoly.android.adapter.UserAdapter;
import com.minipoly.android.entity.User;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.SocialRepository;

import java.util.ArrayList;
import java.util.List;

public class UserListViewModel extends ViewModel implements UserAdapter.IUserBriefList {

    public FireLiveQuery<User> users = SocialRepository.getFollowing(UserManager.getUserID());
    private List<UserBrief> selectedList = new ArrayList<>();

    public void notifyFriends() {

    }

    @Override
    public void toggle(UserBrief brief) {
        if (selectedList.contains(brief))
            selectedList.remove(brief);
        else
            selectedList.add(brief);
    }
}
