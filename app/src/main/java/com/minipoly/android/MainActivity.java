package com.minipoly.android;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.minipoly.android.databinding.MainActivityBinding;
import com.minipoly.android.entity.Category;
import com.minipoly.android.repository.CategoryRepository;

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
        //addCategories();
    }

    private void addCategories() {
        String[] names = {"flat", "Land", "Studio", "Building", "Office", "Shop", "Villa"};
        for (String s : names) {
            Category c = new Category();
            c.setTitle(s);
            CategoryRepository.addCategory(c);
        }
    }

}
