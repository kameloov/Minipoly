package com.minipoly.android.ui.home;

import android.util.Log;
import android.view.View;
import android.widget.PopupMenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.minipoly.android.ActivityViewModel;
import com.minipoly.android.NavGraphDirections;
import com.minipoly.android.R;
import com.minipoly.android.UserManager;
import com.minipoly.android.entity.Category;
import com.minipoly.android.entity.City;
import com.minipoly.android.entity.Country;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.Notification;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.ValueFilter;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.popup.PopupNew;
import com.minipoly.android.popup.PopupRealestateFilter;
import com.minipoly.android.repository.AuctionRepository;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.ui.bars.top_bar.TopBarController;
import com.minipoly.android.utils.ImageBuffer;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements ActivityViewModel.IKindListener {
    private MutableLiveData<List<Realestate>> realestates = new MutableLiveData<>();
    private FireLiveQuery<Realestate> r;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public TopBarController barController = new TopBarController();
    public MutableLiveData<Command> command = new MutableLiveData<>(Command.IDLE);
    public MutableLiveData<Category> category = new MutableLiveData<>();
    public MutableLiveData<Category> subCategory = new MutableLiveData<>();
    public MutableLiveData<Country> country = new MutableLiveData<>();
    public MutableLiveData<City> city = new MutableLiveData<>();

    public FireLiveQuery<Notification> notifications = UserRepository.getUserNotifications(UserRepository.getUserId(), 10);
    public CustomRadio priceRadio = new CustomRadio(false, "Low first", "High first ");
    private List<ValueFilter> realestateFilters;
    private PopupRealestateFilter rFilter;
    public MutableLiveData<Boolean> kind = new MutableLiveData<>(false);

    public HomeViewModel() {
        rFilter = new PopupRealestateFilter(parmas -> {
            realestateFilters = parmas;
            refresh();
        });

        ActivityViewModel.addKindListener(this);
        refresh();
    }

    public void showProfile(View view) {
        NavGraphDirections.ActionGlobalProfile profile =
                NavGraphDirections.actionGlobalProfile(UserManager.getUserID());
        Navigation.findNavController(view).navigate(profile);
    }

    public void showNew(View view) {
        PopupNew popupNew = new PopupNew(view.getContext(), type -> {
            NavController navController = Navigation.findNavController(view);
            switch (type) {
                case NT_AD:
                    ImageBuffer.reset();
                    navController.navigate(R.id.action_global_add_promoted);
                    break;
                case NT_AUCTION:
                    ImageBuffer.reset();
                    NavGraphDirections.ActionGlobalAddAuction
                            auction = NavGraphDirections.actionGlobalAddAuction(AuctionRepository.generateAuction(12, 11, null));
                    navController.navigate(auction);
                    break;
                case NT_DEAL:
                    ImageBuffer.reset();
                    NavGraphDirections.ActionGlobalAddRealestate
                            realestate = NavGraphDirections.actionGlobalAddRealestate(RealestateRepository.generateRealestate(5, -7, null));
                    navController.navigate(realestate);
                    break;
            }
        });
        Log.e("showNew: ", "id is " + view.getId());
        popupNew.show(view);
    }

    private List<ValueFilter> generateFilter() {
        ArrayList<ValueFilter> filters = new ArrayList<>();
        filters.add(new ValueFilter("market", ValueFilter.FilterType.EQUAL, !kind.getValue()));
        if (category.getValue() != null)
            filters.add(new ValueFilter("categoryId", ValueFilter.FilterType.EQUAL, category.getValue().getId()));
        if (subCategory.getValue() != null)
            filters.add(new ValueFilter("subCategoryId", ValueFilter.FilterType.EQUAL, subCategory.getValue().getId()));
        if (country.getValue() != null)
            filters.add(new ValueFilter("countryId", ValueFilter.FilterType.EQUAL, country.getValue().getId()));
        if (city.getValue() != null)
            filters.add(new ValueFilter("cityId", ValueFilter.FilterType.EQUAL, city.getValue().getId()));
        if (realestateFilters != null)
            filters.addAll(realestateFilters);
        return filters;
    }

    public void refresh() {
        loading.setValue(true);
        RealestateRepository.getRealestates(generateFilter(), (success, data) -> {
            if (success && data != null)
                realestates.postValue(data);
            loading.setValue(false);
        });
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<List<Realestate>> getRealestates() {
        return realestates;
    }


    public void setCatOrSubId(boolean sub, Category cat) {
        if (sub)
            subCategory.setValue(cat);
        else
            category.setValue(cat);
        refresh();
    }


    public void showLocationMenu(View view) {
        PopupMenu menu = new PopupMenu(view.getContext(), view);
        menu.inflate(R.menu.location_menu);
        menu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_country) {

            }

            if (item.getItemId() == R.id.menu_city) {

            }
            return false;
        });
        menu.show();
    }

    public void showTypeMenu(View view) {
        PopupMenu menu = new PopupMenu(view.getContext(), view);
        menu.inflate(R.menu.type_menu);
        menu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_category) {
                command.setValue(Command.SELECT_CAT);
            }

            if (item.getItemId() == R.id.menu_subcategory) {
                command.setValue(Command.SELECT_SUBCAT);
            }

            return false;
        });
        menu.getMenu().findItem(R.id.menu_subcategory).setEnabled(category.getValue() != null);
        menu.show();
    }

    public void hideBar() {
        barController.toggle();
        barController.toggleMenu();
    }

    public void showRealestateFilter(View view) {
        rFilter.setAnchor(view);
        rFilter.show();
        Log.e("showRealestateFilter: ", " not showing");
    }



    public void showAuction(String id, NavController controller) {
        AuctionRepository.getAuction(id, (success, data) -> {
            if (success && data != null) {
                HomeFragmentDirections.ActionHomeFragmentToAuctionDetails action =
                        HomeFragmentDirections.actionHomeFragmentToAuctionDetails(data);
                controller.navigate(action);
            }
        });
    }

    public void showRealestate(String id, NavController controller) {
        RealestateRepository.getRealestate(id, (success, data) -> {
            if (success && data != null) {
                HomeFragmentDirections.RealestateDetails realestateDetails =
                        HomeFragmentDirections.realestateDetails(data);
                controller.navigate(realestateDetails);
            }
        });

    }

    public void showRealestate(Realestate realestate, View view) {

        NavGraphDirections.ActionGlobalRealestateDetails action =
                NavGraphDirections.actionGlobalRealestateDetails(realestate);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onKindChanged(boolean kind) {
        this.kind.setValue(kind);
        refresh();
    }


    enum Command {IDLE, SELECT_CAT, SELECT_SUBCAT, SELECT_COUNTRY, SELECT_CITY}
}
