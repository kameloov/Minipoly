package com.minipoly.android.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.minipoly.android.entity.Category;
import com.minipoly.android.entity.Subcategory;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.livedata.FireLiveQuery;

public class CategoryRepository {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference categories = db.collection("category");
    private static CollectionReference subCategories = db.collection("subcategory");

    public static FireLiveQuery<Category> getCategories(){
        return new FireLiveQuery<>(categories.get(),Category.class);
    }

    public static FireLiveQuery<Category> watchCategories(){
        return new FireLiveQuery<Category>(categories.orderBy("title"),Category.class);
    }

    public static FireLiveDocument addCategory(Category category){
        String id = categories.document().getId();
        category.setId( id);
        return new FireLiveDocument(categories.add(category),Subcategory.class);
    }

    public static FireLiveQuery<Subcategory> getSubcategories(String categoryId){
        return new FireLiveQuery<Subcategory>(
                subCategories.whereEqualTo("categoryId",categoryId).get(),Subcategory.class);
    }

    public static FireLiveDocument addSubCategory(Subcategory subcategory){
        String id = subCategories.document().getId();
        subcategory.setId( id);
        return new FireLiveDocument(subCategories.add(subcategory),Subcategory.class);
    }
}
