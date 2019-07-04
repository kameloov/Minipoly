package com.minipoly.android;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.minipoly.android.databinding.MainActivityBinding;
import com.minipoly.android.entity.Car;
import com.minipoly.android.entity.Category;
import com.minipoly.android.entity.ComputerMisc;
import com.minipoly.android.repository.CategoryRepository;
import com.minipoly.android.repository.MiscRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private MainActivityBinding binding;
    private ActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        model = ViewModelProviders.of(this).get(ActivityViewModel.class);
        model.setNavController(Navigation.findNavController(this, R.id.fragment));
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        // addCars();
        addMobileMisc();
    }


    private void addMobileMisc() {
        ComputerMisc misc = new ComputerMisc();
        ArrayList<String> type = new ArrayList<>();
        type.add("intel HD 5000");
        type.add("Nvidia Gtx ");
        type.add("AMD Radeon ");
        misc.setGraphic(type);
        ArrayList<String> color = new ArrayList<>();
        color.add("core i3");
        color.add("core i5");
        color.add("core i7");
        color.add("AMD Rayzen");
        color.add("atom");
        misc.setProcessor(color);
        ArrayList<Integer> ram = new ArrayList<>();
        ram.add(1);
        ram.add(2);
        ram.add(4);
        ram.add(8);
        ram.add(16);
        misc.setRam(ram);
        misc.setStorage(ram);
        ArrayList<Float> batt = new ArrayList<>();
        batt.add(11.0f);
        batt.add(13.2f);
        batt.add(15.7f);
        batt.add(17f);
        misc.setScreen(batt);
        MiscRepository.setComputerMisc(misc);

    }

    private void addCars() {
        String[] cars = new String[]{"Ford", "Chevrolet", "Hyundai", "Kia", "Porsche", "Honda", "Mazda"};
        String[][] models = new String[][]{{"Focus", "Fiesta", "Ranger"}, {"Camaro", "cruze"},
                {"Accent", "Verna", "Azera"}, {"Optima", "Rio", "Picanto"}, {"Boxter", "911 Turbo", "Carera"},
                {"Civic", "Accord"}, {"MX5", "M3", "Rx5"}};
        for (int i = 0; i < cars.length; i++) {
            Car c = new Car();
            c.setName(cars[i]);
            c.setModels(Arrays.asList(models[i]));
            MiscRepository.addCar(c);
        }
    }

    private void addCategories() {
        String[] names = {"car", "mobile", "computer", "flat", "Land", "Studio", "Building", "Office", "Shop", "Villa"};
        String[] namesAr = {"سيارة", "موبايل", "كمبيوتر", "شقة", "أرض", "ستوديو", "عمارة", "مكتب", "متجر", "فيلا"};
        int i = 0;
        Random r = new Random();
        for (String s : names) {
            Category c = new Category();
            c.setTitle(s);
            c.setId(s);
            c.setMarket(i < 3);
            c.setTitleAr(namesAr[i]);
            c.setDealsCount(r.nextInt());
            CategoryRepository.addCategory(c);
            i++;
        }
    }

}
