package com.minipoly.android.ui.edit_profile;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.TransferInfo;
import com.minipoly.android.entity.User;
import com.minipoly.android.livedata.FireLiveUpload;
import com.minipoly.android.repository.MediaRepository;
import com.minipoly.android.repository.SocialRepository;
import com.minipoly.android.utils.LocalData;

public class EditProfileViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();
    private FireLiveUpload uploader = new FireLiveUpload();
    private String userName = "";
    private String email = "";
    private String phone = "";
    private String brief = "";
    private MutableLiveData<Boolean> success = new MutableLiveData<>();

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<Boolean> getSuccess() {
        return success;
    }

    public void setUserName(CharSequence userName) {
        this.userName = userName.toString();
    }

    public void setEmail(CharSequence email) {
        this.email = email.toString();
    }

    public void setPhone(CharSequence phone) {
        this.phone = phone.toString();
    }

    public void setBrief(CharSequence brief) {
        this.brief = brief.toString();
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public LiveData<TransferInfo> getUploader() {
        return uploader;
    }

    public void upateUser() {
        User u = user.getValue();
        if (!userName.isEmpty())
            u.setName(userName);
        if (!email.isEmpty())
            u.setEmail(email);
        if ((!phone.isEmpty()))
            u.setPhone(phone);
        if (!brief.isEmpty())
            u.setBio(brief);
        SocialRepository.updateUser(u, s -> success.setValue(s));
    }


    public void upload(Uri uri) {
        MediaRepository.updateAvatar(LocalData.getUserInfo().getId(), uri, uploader);
    }
}
