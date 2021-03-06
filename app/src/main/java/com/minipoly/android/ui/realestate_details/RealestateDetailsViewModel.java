package com.minipoly.android.ui.realestate_details;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.minipoly.android.R;
import com.minipoly.android.entity.Comment;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.Report;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.param_managers.CarManager;
import com.minipoly.android.param_managers.ComputerManager;
import com.minipoly.android.param_managers.MobileManager;
import com.minipoly.android.param_managers.RealestateManager;
import com.minipoly.android.popup.PopupInput;
import com.minipoly.android.repository.CommentRepository;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.SocialRepository;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.utils.LocalData;
import com.minipoly.android.utils.SocialUtils;

import java.util.Date;
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
    public MutableLiveData<Boolean> offer = new MutableLiveData<>(false);
    public List<String> tags;

    public RealestateDetailsViewModel(Realestate r) {
        currentImage.setValue(0);
        comments = CommentRepository.loadComments(r.getId());
        loading.setValue(false);
        like.setValue(false);
        dislike.setValue(false);
        this.relestate.setValue(r);
        offer.setValue(r.getOfferEnd() != null && r.getOfferEnd().after(new Date()));
        SocialRepository.isFollowing(r.getUserBrief().getId(), (success, data) -> following.setValue(data));
        sync();
        RealestateRepository.isFollowing(relestate.getValue().getId(), (success, data) -> watching.setValue(success && data));
        prepareTags(r);
        RealestateRepository.updateViews(r.getId());
    }


    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    public void order(View v) {
        String name = UserRepository.getBrief().getName();
        String msg = "Hello am " + name + " i wnat to talk to you about " + getRealestate().getValue().getTitle();
        SocialUtils.whatsappMsg(v.getContext(), "966559563649", msg);
    /*    ChatRepository.openConversation(relestate.getValue(), (success, id) -> {
            if (success) {
                RealestateDetailsDirections.DetailsToChat detailsToChat = RealestateDetailsDirections.detailsToChat();
                detailsToChat.setConversationId(id);
                Navigation.findNavController(v).navigate(detailsToChat);
            }
        });*/
    }


    public String gettype(Context c) {
        String red;
        Realestate realestate = this.relestate.getValue();
        if (realestate.isMarket())
            red = c.getString(realestate.isUsed() ? R.string.used_tag : R.string.new_tag);
        else
            red = c.getString(realestate.getRealestateInfo().isRent() ? R.string.rent : R.string.sell);
        return red;
    }

    public boolean isAdvrtOwner() {
        return relestate.getValue().getUserBrief().getId().equals(UserRepository.getUserId());
    }

    public void doAction(View v) {
        if (!isAdvrtOwner()) {
            RealestateDetailsDirections.ActionRealestateDetailsToMore action =
                    RealestateDetailsDirections.actionRealestateDetailsToMore(relestate.getValue());
            Navigation.findNavController(v).navigate(action);
        } else {
            RealestateDetailsDirections.ActionRealestateDetailsToEditAdvrtDialog action =
                    RealestateDetailsDirections.actionRealestateDetailsToEditAdvrtDialog(relestate.getValue());
            Navigation.findNavController(v).navigate(action);
        }
    }

    public void showGallery(View view) {

    }

    public void share(View v) {
        String link = generateLink(v.getContext());
        SocialUtils.shareLink(v.getContext(), link);
    }

    public void showMenu(View v) {
        Context context = v.getContext();
        PopupMenu menu = new PopupMenu(v.getContext(), v);
        menu.inflate(R.menu.advrt_pop);
        menu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_transfer:

                    return true;
                case R.id.menu_report:
                    PopupInput popupInput = new PopupInput(v, (positive, text) -> {
                        if (positive) {
                            Report report = new Report();
                            report.setReported(relestate.getValue().getUserBrief());
                            report.setReporter(UserRepository.getBrief());
                            report.setText(text);
                            UserRepository.reportUser(report, success -> {
                                Toast.makeText(context, context.getString(R.string.reported), Toast.LENGTH_SHORT).show();
                            });
                        }
                    });
                    popupInput.show();
                    return true;
            }
            return false;
        });
        menu.show();
    }


    private String generateLink(Context context) {
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://minipoly.page.link/1/" + relestate.getValue().getId()))
                .setDomainUriPrefix("https://minipoly.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle(relestate.getValue().getTitle())
                                .setDescription(context.getResources().getString(R.string.check_deal))
                                .build())
                .buildDynamicLink();
        return dynamicLink.getUri().toString();
    }

    public void notifyFriends(View v) {
        Navigation.findNavController(v).navigate(RealestateDetailsDirections.advrtUserList());
    }

    private void prepareTags(Realestate r) {
        if (!r.isMarket())
            tags = RealestateManager.getTags(r.getRealestateInfo());
        else {
            if (r.getCategoryId().equals("car"))
                tags = CarManager.getTags(r.getCarInfo());
            if (r.getCategoryId().equals("computer"))
                tags = ComputerManager.getTags(r.getComputerInfo());
            if (r.getCategoryId().equals("mobile"))
                tags = MobileManager.getTags(r.getMobileInfo());


        }

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
        RealestateRepository.like(relestate.getValue().getId(), (success, data) -> {
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

    public void changeFollow(UserBrief brief) {

        if (following.getValue())
            SocialRepository.unFollow(brief.getId(), success -> {
                if (success)
                    toggleFollow();
            });
        else {
            SocialRepository.follow(brief, success -> {
                if (success)
                    toggleFollow();
            });
        }

    }

    public void showComments(View v) {
        RealestateDetailsDirections.ActionRealestateDetailsToCommentsFragment action =
                RealestateDetailsDirections.actionRealestateDetailsToCommentsFragment(relestate.getValue());
        Navigation.findNavController(v).navigate(action);
    }

    public void dislike() {
        RealestateRepository.dislike(relestate.getValue().getId(), (success, data) -> {
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
