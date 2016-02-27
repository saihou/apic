package com.space.apic;

/**
 * Created by saihou on 2/19/16.
 */
public class ChallengeCardData {
    String merchaintName;
    String challengeDuration;
    String challengeRestaurant;
    String challengeDistance;
    String caption;
    String picture;

    public ChallengeCardData(String username, String time, String challengeRestaurant, String challengeDistance, String caption) {
        this.merchaintName = username;
        this.challengeDuration = time;
        this.challengeRestaurant = challengeRestaurant;
        this.challengeDistance = challengeDistance;
        this.caption = caption;
    }

    public ChallengeCardData(String username, String time, String challengeRestaurant, String challengeDistance, String caption, String picture) {
        this.merchaintName = username;
        this.challengeDuration = time;
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
        return merchaintName;
    }
    public String getTime() {
        return challengeDuration;
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
