package com.minipoly.android.ui.auction_details;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.minipoly.android.UserManager;
import com.minipoly.android.entity.Auction;
import com.minipoly.android.entity.Comment;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.param_managers.BidManager;
import com.minipoly.android.param_managers.CarManager;
import com.minipoly.android.param_managers.ComputerManager;
import com.minipoly.android.param_managers.MobileManager;
import com.minipoly.android.param_managers.RealestateManager;
import com.minipoly.android.repository.AuctionRepository;
import com.minipoly.android.repository.CommentRepository;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.SocialRepository;
import com.minipoly.android.utils.CountDown;
import com.minipoly.android.utils.LocalData;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AuctionDetailsViewModel extends ViewModel {
    private FireLiveDocument<Auction> relestate;
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
    public BidManager bidManager;
    public CountDown countDown;

    public enum Command {IDLE, BLOCKED, LAST_BIDDER}

    public MutableLiveData<Command> command = new MutableLiveData<>(Command.IDLE);

    public AuctionDetailsViewModel(Auction r) {
        currentImage.setValue(0);
        comments = CommentRepository.loadAuctionComments(r.getId());
        loading.setValue(false);
        like.setValue(false);
        dislike.setValue(false);
        this.relestate = AuctionRepository.watchAuction(r.getId());
        SocialRepository.isFollowing(r.getUserBrief().getId(), (success, data) -> following.setValue(data));
        sync(r.getId());
        RealestateRepository.isFollowing(r.getId(), (success, data) -> watching.setValue(success && data));
        prepareTags(r);
        bidManager = new BidManager(r.getId());
        AuctionRepository.updateViews(r.getId());
        countDown = new CountDown(getRemaingingTime(r.getStartTime()));
    }


    public void bid() {
        Auction a = relestate.getValue();
        if (a.getBlocked() != null && a.getBlocked().contains(UserManager.getUserID())) {
            command.setValue(Command.BLOCKED);
            return;
        }
        if (a.getLastBid().getBidder().getId().equals(UserManager.getUserID())) {
            command.setValue(Command.LAST_BIDDER);
            return;
        }
        bidManager.bid();
    }

    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    public void notifyFriends(View v) {
        Navigation.findNavController(v).navigate(AuctionDetailsDirections.auctionUserList());
    }

    private void prepareTags(Auction auction) {
        if (!auction.isMarket())
            tags = RealestateManager.getTags(auction.getRealestateInfo());
        else {
            if (auction.getCategoryId().equals("car"))
                tags = CarManager.getTags(auction.getCarInfo());
            if (auction.getCategoryId().equals("computer"))
                tags = ComputerManager.getTags(auction.getComputerInfo());
            if (auction.getCategoryId().equals("mobile"))
                tags = MobileManager.getTags(auction.getMobileInfo());


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


    private long getRemaingingTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date end = calendar.getTime();
        if (end.getTime() > now.getTime())
            return end.getTime() - now.getTime();
        else
            return 0;
    }

    private void sync(String id) {
        SocialRepository.liked(id, (success, data) -> like.setValue(success && data));
        SocialRepository.disliked(id, (success, data) -> dislike.setValue(success && data));
    }


    public void like() {
        AuctionRepository.like(relestate.getValue().getId(), (success, data) -> {
            if (success)
                like.setValue(data);
            sync(relestate.getValue().getId());
        });
    }

    public void showBidders(View v) {
        AuctionDetailsDirections.Bidders acion = AuctionDetailsDirections.bidders(relestate.getValue().getId());
        Navigation.findNavController(v).navigate(acion);
    }


    public void dislike() {
        AuctionRepository.dislike(relestate.getValue().getId(), (success, data) -> {
            if (success)
                dislike.setValue(data);
            sync(relestate.getValue().getId());
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
        CommentRepository.addAuctionComment(c, success -> {
            adding.setValue(false);
            comment.setValue("");
        });
    }

    public LiveData<Auction> getRealestate() {
        return relestate;
    }
}
