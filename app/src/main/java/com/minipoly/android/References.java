package com.minipoly.android;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class References {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static CollectionReference realestates = db.collection(C.COLLECTION_REALESTATE);
    public static CollectionReference markets = db.collection(C.COLLECTION_MARKETS);
    public static CollectionReference users = db.collection(C.COLLECTION_USER);
    public static CollectionReference relCat = db.collection(C.COLLECTION_REALESTATE_CATEGORY);
    public static CollectionReference marCat = db.collection(C.COLLECTION_MARKET_CATEGORY);
    public static CollectionReference interactions = db.collection(C.COLLECTION_INTERACTION);
    public static CollectionReference conversations = db.collection(C.COLLECTION_CONVERSATION);
    public static CollectionReference currencies = db.collection(C.COLLECTION_CURRENCY);
    public static CollectionReference cars = db.collection(C.COLLECTION_CAR);
    public static CollectionReference mobile = db.collection(C.COLLECTION_MOBILE);
    public static CollectionReference computer = db.collection(C.COLLECTION_COMPUTER);
    public static CollectionReference auctions = db.collection(C.COLLECTION_AUCTION);
    public static CollectionReference reports = db.collection(C.COLLECTION_REPORT);
    public static CollectionReference promotedAds = db.collection(C.COLLECTION_PROMOTED_ADS);
    public static CollectionReference countries = db.collection(C.COLLECTION_COUNTRY);
}
