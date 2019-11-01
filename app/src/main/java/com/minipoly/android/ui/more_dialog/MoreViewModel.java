package com.minipoly.android.ui.more_dialog;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Realestate;
import com.minipoly.android.param_managers.CarManager;
import com.minipoly.android.param_managers.ComputerManager;
import com.minipoly.android.param_managers.MobileManager;
import com.minipoly.android.param_managers.RealestateManager;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.utils.SocialUtils;

import java.util.List;


public class MoreViewModel extends ViewModel {

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    public Realestate realestate;
    public List<String> tags;
    public MutableLiveData<Boolean> callVisible = new MutableLiveData<>(false);

    public MoreViewModel(Realestate realestate) {
        this.realestate = realestate;
        prepareTags(realestate);
    }

    public void toggleVisible() {
        callVisible.setValue(!callVisible.getValue());
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

    public void orderwhats(View v) {
        String name = UserRepository.getBrief().getName();
        String msg = "Hello am " + name + " i wnat to talk to you about " + realestate.getTitle();
        SocialUtils.whatsappMsg(v.getContext(), "249100979685", msg);
        toggleVisible();
    }

    public void orderCall(View v) {
        SocialUtils.dial(v.getContext(), "249100979685");
        toggleVisible();
    }
}
