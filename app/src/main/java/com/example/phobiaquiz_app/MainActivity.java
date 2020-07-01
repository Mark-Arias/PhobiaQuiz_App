package com.example.phobiaquiz_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    //TextView tvProgressLabel;
    ImageView image;
    ImageButton leftButton;
    ImageButton rightButton;
    SeekBar seekBar;
    ListView listView;
    PictureGalleryFragment pictureFragment;
    int imageCount = 1;
    String imageIdentifier = "picture";
    TestItem item;

    /*
    static int pictures[] = {R.drawable.picture1, R.drawable.picture2,
            R.drawable.picture3,
            R.drawable.picture4, R.drawable.picture5,
            R.drawable.picture6,
            R.drawable.picture7, R.drawable.picture8};

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = new TestItem(imageCount,this);
        //image = (ImageView) findViewById(R.id.imageView);       // set image to UI asset
        //image.setImageResource(R.drawable.picture1);     // display default

        // ----------------------------------------------------------------------------------------
        // Create seekBar widget and a event listener for it
        seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(50);    // init score to neutral ranking
        int progress = seekBar.getProgress();
        //tvProgressLabel = findViewById(R.id.tvSeekBarLabel);
        //tvProgressLabel.setText("Score: " + progress);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // update

                // block below changes the color of the  seekBar depending on the user ranking
                if(progress > 66 && progress <= 100) {    // positive ranking
                    int greenValue = (int) (255.0 * (progress / 100.0) - 45);
                    seekBar.setBackgroundColor(Color.rgb(0,greenValue,0));

                } else if(progress > 33 && progress <= 66) { /// neutral ranking
                    int grayValue = (int) (255.0 * (progress / 100.0));
                    seekBar.setBackgroundColor(Color.rgb(grayValue,grayValue,grayValue));

                } else if (progress >= 0 && progress <= 32) { // negative ranking
                    int redValue = (int) (255.0 * (progress / 100.0)  + 90);
                    seekBar.setBackgroundColor(Color.rgb(redValue,0,0));

                }
                //tvProgressLabel.setText("Score: " + progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // Operations to perform when the user releases the seek bar
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // ----------------------------------------------------------------------------------------
        /**
         * Block defines left button control
         */
        leftButton = findViewById(R.id.imageButtonLeft);
        leftButton.setOnClickListener(v -> {

            resetSeekBar();
            decrementImageCount();
            changeImageFragment(imageIdentifier,getImageCount());

        });
        // ----------------------------------------------------------------------------------------
        /**
         * Block defines right button control
         */
        rightButton = findViewById(R.id.imageButtonRight);
        rightButton.setOnClickListener(v -> {

            resetSeekBar();
            incrementImageCount();
            changeImageFragment(imageIdentifier,getImageCount());

        });
        // ----------------------------------------------------------------------------------------


    }

    //----------------------------------------------------------------------------------------------
    // Definition of local helper methods and a local class
    // imageCount methods used to keep track of the photo that needs to be accessed

    public void changeImageFragment(String imageIdentifier, int imageCount) {
        resetSeekBar();

        PictureGalleryFragment pictureFragment = PictureGalleryFragment.newInstance(imageIdentifier, imageCount); // create a new fragment with passed in info to select specified image
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();   // transaction object
        transaction.replace(R.id.pictureGalleryFragment, pictureFragment);  // swap and replace in second param. into first param
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public int getImageCount() {
        return imageCount;
    }

    public void incrementImageCount() {
        if(imageCount < 8) {
            imageCount++;
        }
    }

    public void decrementImageCount() {
        if(imageCount > 1) {
            imageCount--;
        }
    }

    public void resetSeekBar() {
        seekBar.setProgress(50);    // reset score to neutral ranking
        seekBar.setBackgroundColor(Color.rgb(255,255,255));   // reset color to blank
    }
}