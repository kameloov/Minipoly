package com.minipoly.android.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.minipoly.android.R;
import com.minipoly.android.RootFragment;
import com.minipoly.android.adapter.NotificationAdapter;
import com.minipoly.android.adapter.RealestateAdapter;
import com.minipoly.android.databinding.HomeFragmentBinding;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.ui.category_dialog.CategoryDialog;
import com.minipoly.android.utils.ZoomOutPageTransformer;

import java.util.List;

public class HomeFragment extends RootFragment {

    private HomeViewModel model;
    private RealestateAdapter adapter;
    private ViewPager pager;
    private HomeFragmentBinding binding;
    private NotificationAdapter notificationAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        showNav();
        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        binding.load.startAnimation(a);
        prepareNotificationAdapter();
        attachObservers();
    }

    private void attachObservers() {
        model.typeRadio.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                model.category.setValue(null);
                model.subCategory.setValue(null);
                model.refresh();
            }
        });

        model.command.observe(this, command -> {
            if (command == HomeViewModel.Command.IDLE)
                return;
            String id = command == HomeViewModel.Command.SELECT_CAT ?
                    null : model.category.getValue().getId();
            CategoryDialog dialog = CategoryDialog.newInstance(id, !model.typeRadio.isChecked(), (sub, category) -> model.setCatOrSubId(sub, category));

            dialog.show(getFragmentManager(), "CAT");
            model.command.setValue(HomeViewModel.Command.IDLE);
        });

        model.getRealestates().observe(this, realestates -> {
            binding.load.clearAnimation();
            binding.load.setVisibility(View.INVISIBLE);
            if (adapter == null)
                adapter = new RealestateAdapter(realestates);
            else
                adapter.setRealestates(realestates);
            preparePager(binding.pager, realestates);

        });

        model.notifications.observe(this, notifications -> {
            if (notifications != null) {
                notificationAdapter.submitList(notifications);
                model.barController.blink();
            }
        });
    }


    private void preparePager(ViewPager pager, List<Realestate> realestates) {
        pager.setOffscreenPageLimit(5);
        pager.setPageTransformer(false, new ZoomOutPageTransformer());
        pager.setAdapter(adapter);
    }

    private void prepareNotificationAdapter() {
        notificationAdapter = new NotificationAdapter(notification -> {
            if (notification != null) {
                NavController controller = Navigation.findNavController(binding.getRoot());
                int type = notification.getType();
                if (type == 1)
                    model.showRealestate(notification.getItemId(), controller);
                if (type == 2)
                    model.showAuction(notification.getItemId(), controller);
                model.hideBar();
            }
        });
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        binding.topBar.lstNotification.setLayoutManager(manager);
        binding.topBar.lstNotification.setAdapter(notificationAdapter);
    }

}
