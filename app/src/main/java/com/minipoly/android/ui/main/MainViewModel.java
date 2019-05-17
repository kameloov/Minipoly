package com.minipoly.android.ui.main;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.minipoly.android.StorageManager;
import com.minipoly.android.entity.Category;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.livedata.FireLiveUpload;
import com.minipoly.android.repository.CategoryRepository;


public class MainViewModel extends ViewModel {

    private static final String TAG="MainViewModel";
    public String text = " hello from VM";
    private FireLiveUpload uploadLive;
    private FireLiveQuery<Category> categories;
    public void setText(View view) {
        this.text = "clicked";
        Log.d(TAG, "setText: "+text);
    }

    public FireLiveQuery<Category> getCategories() {
        if (categories == null )
            categories = CategoryRepository.watchCategories();
        return categories;
    }

    public FireLiveUpload getUploadLive() {
        if (uploadLive==null)
            uploadLive = new FireLiveUpload();
        return uploadLive;
    }

    public void upload(String path , Uri uri){
        uploadLive.setUploadTask(StorageManager.getRoot().child(path).putFile(uri));
    }

    public void addCategories(){
        for ( int i = 0 ; i < 7 ; i++){
            Category category = new Category();
            category.setTitle("category "+ i );
            category.setIcon("icon"+i);
            CategoryRepository.addCategory(category);

        }
    }


}
