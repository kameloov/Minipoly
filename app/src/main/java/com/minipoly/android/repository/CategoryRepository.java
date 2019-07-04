package com.minipoly.android.repository;

import com.google.firebase.firestore.DocumentReference;
import com.minipoly.android.C;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.Category;
import com.minipoly.android.entity.Currency;
import com.minipoly.android.entity.Subcategory;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.livedata.FireLiveQuery;

import java.util.List;

import static com.minipoly.android.References.currencies;
import static com.minipoly.android.References.relCat;

public class CategoryRepository {
    private List<Category> categories;

    public static FireLiveQuery<Category> getCategories(boolean market) {
        return new FireLiveQuery<>(relCat.whereEqualTo("market", market).get(), Category.class);
    }

    public static void getCategories(boolean market, DataListener<List<Category>> listener) {
        relCat.whereEqualTo("market", market).get().addOnCompleteListener(task -> listener.onComplete(task.isSuccessful(),
                task.getResult().toObjects(Category.class)));
    }

    public static FireLiveQuery<Category> watchCategories(){
        return new FireLiveQuery<Category>(relCat.orderBy("title"), Category.class, false);
    }

    public static FireLiveDocument addCategory(Category category){
        String id = category.getId() != null ? category.getId() : relCat.document().getId();
        category.setId( id);
        return new FireLiveDocument(relCat.add(category), Category.class);
    }

    public static FireLiveQuery<Category> getSubcategories(String categoryId) {
        return new FireLiveQuery(
                relCat.document(categoryId).collection(C.COLLECTION_SUBCATEGORY)
                        .get(), Category.class);
    }

    public static FireLiveDocument addSubCategory(Subcategory subcategory){
        DocumentReference reference = relCat.document(subcategory.getCategoryId())
                .collection(C.COLLECTION_SUBCATEGORY).document();
        subcategory.setId(reference.getId());
        return new FireLiveDocument(reference.set(subcategory), Subcategory.class);
    }

    public static void getCurrencies(DataListener<List<Currency>> listener) {
        currencies.get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
                listener.onComplete(true, task.getResult().toObjects(Currency.class));
            else
                listener.onComplete(false, null);
        });
    }

    public static FireLiveQuery<Currency> getCurrencies() {
        return new FireLiveQuery<Currency>(currencies.get(), Currency.class);
    }
}
