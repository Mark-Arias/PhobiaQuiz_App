package com.example.phobiaquiz_app;

import android.app.Activity;
import android.widget.ImageView;

public class TestItem {

    String imageIdentifier = "picture";
    private ImageView image;
    int userEmotionalResponseScore;
    int pictureId;
    Activity activity;

    /**
     * Create a new TestItem with a specifc image picture id
     * @param pictureId of the specific image desired
     * @param activity that contains the child elements we want to render
     */
    public TestItem(int pictureId, Activity activity) {
        this.pictureId = pictureId;
        userEmotionalResponseScore = 50;
        image = (ImageView) activity.findViewById(R.id.imageView);       // set image to UI asset

        String temp = imageIdentifier + pictureId;    // create name of asset to retrieve
        int drawableResourceId = activity.getResources().getIdentifier(temp, "drawable", activity.getPackageName());    // get resource id
        image.setImageResource(drawableResourceId); //set image to what is specified by the passed in id
        //image.setImageResource(R.drawable.picture1);     // display default

    }


    // Getters and Setters ---------------------------------------------------------------
    public int getUserEmotionalResponseScore() {
        return userEmotionalResponseScore;
    }

    public void setUserEmotionalResponseScore(int userEmotionalResponseScore) {
        this.userEmotionalResponseScore = userEmotionalResponseScore;
    }
}
