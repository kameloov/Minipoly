package com.minipoly.android.entity;

import com.google.firebase.firestore.ServerTimestamp;
import com.minipoly.android.utils.SearchUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Realestate extends Pin implements Serializable {
    private String id;
    private String title;
    private String text;
    private String categoryId;
    private String subCategoryId;
    private List<Image> images;
    private HashMap<String, Boolean> tags;
    private String[] videos;
    private UserBrief userBrief;
    private float price;
    private int old;
    private String countryId;
    private String categoryName;
    private String categoryNameAr;
    private String subCatName;
    private String subCatNameAr;
    private String currency;
    private String cityId;
    private String cityName;
    private String cityNameAR;
    private int like;
    private int dislike;
    private int views;
    private boolean market;
    private boolean used;
    private CarInfo carInfo;
    private RealestateInfo realestateInfo;
    private MobileInfo mobileInfo;
    private ComputerInfo computerInfo;
    private boolean rented;
    private int offerPercent;
    private Date offerEnd;
    private int commentCount = 0;
    private Date promoteEnd;
    @ServerTimestamp
    private Date publishDate;
    private String l0;
    private String l1;
    private String l2;
    private String l3;
    private String l4;
    private String l5;
    private String l6;
    private String l7;
    private String l8;
    private String l9;

    public static List<String> getWords(Realestate r) {
        ArrayList<String> tagList = new ArrayList<>();
        tagList.addAll(SearchUtils.extractWords(r.categoryName));
        tagList.addAll(SearchUtils.extractWords(r.categoryNameAr));
        tagList.addAll(SearchUtils.extractWords(r.subCatName));
        tagList.addAll(SearchUtils.extractWords(r.subCatNameAr));
        tagList.addAll(SearchUtils.extractWords(r.cityName));
        tagList.addAll(SearchUtils.extractWords(r.cityNameAR));
        tagList.addAll(SearchUtils.extractWords(r.title));
        tagList.addAll(SearchUtils.extractWords(r.text));
        return tagList;
    }

    public Date getPromoteEnd() {
        return promoteEnd;
    }

    public void setPromoteEnd(Date promoteEnd) {
        this.promoteEnd = promoteEnd;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public String getL0() {
        return l0;
    }

    public HashMap<String, Boolean> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, Boolean> tags) {
        this.tags = tags;
    }

    public void setL0(String l0) {
        this.l0 = l0;
    }

    public String getL8() {
        return l8;
    }

    public void setL8(String l8) {
        this.l8 = l8;
    }

    public String getL9() {
        return l9;
    }

    public void setL9(String l9) {
        this.l9 = l9;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryNameAr() {
        return categoryNameAr;
    }

    public void setCategoryNameAr(String categoryNameAr) {
        this.categoryNameAr = categoryNameAr;
    }

    public String getL1() {
        return l1;
    }

    public void setL1(String l1) {
        this.l1 = l1;
    }

    public String getL2() {
        return l2;
    }

    public void setL2(String l2) {
        this.l2 = l2;
    }

    public String getL3() {
        return l3;
    }

    public void setL3(String l3) {
        this.l3 = l3;
    }

    public String getL4() {
        return l4;
    }

    public void setL4(String l4) {
        this.l4 = l4;
    }

    public String getL5() {
        return l5;
    }

    public void setL5(String l5) {
        this.l5 = l5;
    }

    public String getL6() {
        return l6;
    }

    public void setL6(String l6) {
        this.l6 = l6;
    }

    public String getL7() {
        return l7;
    }

    public void setL7(String l7) {
        this.l7 = l7;
    }

    public void setLevelCoord(int level, String coord) {
        switch (level) {
            case 0:
                l0 = coord;
                break;
            case 1:
                l1 = coord;
                break;
            case 2:
                l2 = coord;
                break;
            case 3:
                l3 = coord;
                break;
            case 4:
                l4 = coord;
                break;
            case 5:
                l5 = coord;
                break;
            case 6:
                l6 = coord;
                break;
            case 7:
                l7 = coord;
                break;
            case 8:
                l8 = coord;
                break;
            case 9:
                l9 = coord;
                break;
        }
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getOfferPercent() {
        return offerPercent;
    }

    public void setOfferPercent(int offerPercent) {
        this.offerPercent = offerPercent;
    }

    public Date getOfferEnd() {
        return offerEnd;
    }

    public void setOfferEnd(Date offerEnd) {
        this.offerEnd = offerEnd;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public ComputerInfo getComputerInfo() {
        return computerInfo;
    }

    public void setComputerInfo(ComputerInfo computerInfo) {
        this.computerInfo = computerInfo;
    }

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public RealestateInfo getRealestateInfo() {
        return realestateInfo;
    }

    public void setRealestateInfo(RealestateInfo realestateInfo) {
        this.realestateInfo = realestateInfo;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public String getSubCatNameAr() {
        return subCatNameAr;
    }

    public void setSubCatNameAr(String subCatNameAr) {
        this.subCatNameAr = subCatNameAr;
    }

    public MobileInfo getMobileInfo() {
        return mobileInfo;
    }

    public void setMobileInfo(MobileInfo mobileInfo) {
        this.mobileInfo = mobileInfo;
    }

    public boolean isMarket() {
        return market;
    }

    public void setMarket(boolean market) {
        this.market = market;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getViews() {
        return views;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityNameAR() {
        return cityNameAR;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCityNameAR(String cityNameAR) {
        this.cityNameAR = cityNameAR;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String[] getVideos() {
        return videos;
    }

    public void setVideos(String[] videos) {
        this.videos = videos;
    }

    public UserBrief getUserBrief() {
        return userBrief;
    }

    public void setUserBrief(UserBrief userBrief) {
        this.userBrief = userBrief;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }



    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }

}