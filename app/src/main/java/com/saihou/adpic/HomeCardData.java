package com.saihou.adpic;

/**
 * Created by saihou on 2/19/16.
 */
public class HomeCardData {
    String username;
    String time;
    String challengeRestaurant;
    String challengeDistance;
    String caption;
    String picture;

    public HomeCardData(String username, String time, String challengeRestaurant, String challengeDistance, String caption) {
        this.username = username;
        this.time = time;
        this.challengeRestaurant = challengeRestaurant;
        this.challengeDistance = challengeDistance;
        this.caption = caption;
    }

    public HomeCardData(String username, String time, String challengeRestaurant, String challengeDistance, String caption, String picture) {
        this.username = username;
        this.time = time;
        this.challengeRestaurant = challengeRestaurant;
        this.challengeDistance = challengeDistance;
        this.caption = caption;
        this.picture = picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }
    public String getUsername() {
        return username;
    }
    public String getTime() {
        return time;
    }
    public String getCaption() {
        return caption;
    }
    public String getChallengeDistance() {
        return challengeDistance;
    }
    public String getChallengeRestaurant() {
        return challengeRestaurant;
    }
}
