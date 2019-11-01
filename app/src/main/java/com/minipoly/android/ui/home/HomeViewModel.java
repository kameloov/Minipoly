package com.minipoly.android.ui.home;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;
import com.minipoly.android.ActivityViewModel;
import com.minipoly.android.NavGraphDirections;
import com.minipoly.android.R;
import com.minipoly.android.UserManager;
import com.minipoly.android.databinding.CountryPinBinding;
import com.minipoly.android.databinding.MapPinBinding;
import com.minipoly.android.entity.Category;
import com.minipoly.android.entity.City;
import com.minipoly.android.entity.Country;
import com.minipoly.android.entity.Notification;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.filters.FilterManager;
import com.minipoly.android.filters.RealestateFilter;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.popup.PopupNew;
import com.minipoly.android.popup.PopupRealestate;
import com.minipoly.android.repository.AuctionRepository;
import com.minipoly.android.repository.MiscRepository;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.ui.bars.top_bar.TopBarController;
import com.minipoly.android.utils.ImageBuffer;
import com.minipoly.android.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class HomeViewModel extends ViewModel implements ActivityViewModel.IKindListener {
    public MarkerViewManager manager;
    public String myId = UserManager.getUserID();
    public TopBarController barController = new TopBarController();
    public MutableLiveData<Command> command = new MutableLiveData<>(Command.IDLE);
    public MutableLiveData<Category> category = new MutableLiveData<>();
    public MutableLiveData<Category> subCategory = new MutableLiveData<>();
    public MutableLiveData<Country> country = new MutableLiveData<>();
    public MutableLiveData<City> city = new MutableLiveData<>();
    public FireLiveQuery<Notification> notifications = UserRepository.getUserNotifications(UserRepository.getUserId(), 10);
    public MutableLiveData<Boolean> kind = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> map = new MutableLiveData<>(false);
    public FireLiveQuery<Country> counries = MiscRepository.getCountries();
    public RealestateFilter realestateFilter = new RealestateFilter();
    private MutableLiveData<List<Realestate>> realestates;
    private MutableLiveData<Boolean> loading;
    private SymbolManager symbolManager;
    private ArrayList<MarkerView> markers = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private ViewMode viewMode = ViewMode.REALESTATE;
    private View mainView;
    private FilterManager filterManager;

    public HomeViewModel(View mainView) {
        this.mainView = mainView;
        filterManager = new FilterManager();
        loading = filterManager.loading;
        realestates = filterManager.data;
        MiscRepository.getCities((success, data) -> {
            if (success && data != null)
                cities = data;
        });
        ActivityViewModel.addKindListener(this);
    }

    public void showRealestateFilter(View view) {
        HomeFragmentDirections.ActionHomeFragmentToRealestateFilterFragment action =
                HomeFragmentDirections.actionHomeFragmentToRealestateFilterFragment(realestateFilter);
        Navigation.findNavController(view).navigate(action);
    }

    public void showProfile(View view) {
        NavGraphDirections.ActionGlobalProfile profile =
                NavGraphDirections.actionGlobalProfile(UserManager.getUserID());
        Navigation.findNavController(view).navigate(profile);
    }

    public void setMarkerManger(MarkerViewManager manager) {
        this.manager = manager;
    }

    public void setSymbolManager(SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }

    public void toggleMapView() {
        map.setValue(!map.getValue());
    }

    public void updateRegion(LatLngBounds bounds, LayoutInflater inflater) {
        int zoom = MapUtils.getZoomLevel(bounds);
        String coord = MapUtils.getCoordinatesString(zoom, bounds.getCenter());
        Timber.e("zoom : " + zoom + "and coord is " + coord);
        if (zoom < 9) {
            getMapRealestates(zoom, coord);
            viewMode = ViewMode.REALESTATE;
        } else if (zoom == 9) {
            if (viewMode == ViewMode.CITY)
                return;
            addCityMarkers(inflater);
            viewMode = ViewMode.CITY;
        } else {
            if (viewMode == ViewMode.COUNTRY)
                return;
            addCountryMarkers(inflater);
            viewMode = ViewMode.COUNTRY;
        }
    }

    private void getMapRealestates(int zoom, String coord) {
        realestateFilter.setZoom(zoom);
        realestateFilter.setSquare(coord);
        filterManager.filter(realestateFilter.getFilters());
    }

    public void clearMarkers() {
        if (markers == null)
            return;
        for (MarkerView marker : markers)
            if (marker != null)
                manager.removeMarker(marker);
        markers.clear();
    }

    public void addRealestateMarkers(LayoutInflater inflater) {
        for (Realestate r : realestates.getValue()) {
            MapPinBinding binding = MapPinBinding.inflate(inflater);
            binding.setR(r);
            String title = r.getCategoryName() != null ? r.getCategoryName() : r.getCategoryId();
            if (r.getRealestateInfo() != null)
                title += r.getRealestateInfo().isRent() ? " For rent" : " For sale";
            binding.setTitle(title);
            binding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            binding.getRoot().setOnClickListener(v -> {
                Timber.e(r.getTitle());
                PopupRealestate pop = new PopupRealestate(mainView, r);
                pop.setAnimationStyle(R.style.Card);
                pop.showAtLocation(mainView, Gravity.CENTER, 0, 0);
            });
            MarkerView markerView = new MarkerView(new LatLng(r.getLat(), r.getLang()), binding.getRoot());
            if (manager != null) {
                manager.addMarker(markerView);
                markers.add(markerView);
            }
        }
    }

    public void addCountryMarkers(LayoutInflater inflater) {
        clearMarkers();
        for (Country c : counries.getValue()) {
            CountryPinBinding binding = CountryPinBinding.inflate(inflater);
            binding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            binding.setC(c);
            MarkerView markerView = new MarkerView(new LatLng(c.getLat(), c.getLang()), binding.getRoot());
            if (manager != null) {
                manager.addMarker(markerView);
                markers.add(markerView);
            }
        }
    }

    public void addCityMarkers(LayoutInflater inflater) {
        clearMarkers();
        for (City c : cities) {
            CountryPinBinding binding = CountryPinBinding.inflate(inflater);
            binding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            binding.setC(c);
            MarkerView markerView = new MarkerView(new LatLng(c.getLat(), c.getLang()), binding.getRoot());
            if (manager != null) {
                manager.addMarker(markerView);
                markers.add(markerView);
            }
        }
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

    public void refresh() {
        filterManager.filter(realestateFilter.getFilters());
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
        this.kind.setValue(!kind);
        realestateFilter.setMarket(!kind);
        refresh();
    }

    enum ViewMode {REALESTATE, CITY, COUNTRY}

    enum Command {IDLE, SELECT_CAT, SELECT_SUBCAT, SELECT_COUNTRY, SELECT_CITY}
}
