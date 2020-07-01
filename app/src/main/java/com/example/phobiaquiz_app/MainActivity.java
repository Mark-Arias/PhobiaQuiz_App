package com.example.phobiaquiz_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
    String imageIdentifer = "picture";

    static int animals[] = {R.drawable.picture1, R.drawable.picture2,
            R.drawable.picture3,
            R.drawable.picture4, R.drawable.picture5,
            R.drawable.picture6,
            R.drawable.picture7, R.drawable.picture8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.imageView);       // set image to UI asset
        image.setImageResource(R.drawable.picture1);     // display default

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
                //tvProgressLabel.setText("Score: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

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

            decrementImageCount();  // move counter to prior image
            pictureFragment = PictureGalleryFragment.newInstance(imageIdentifer,getImageCount());

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.pictureGalleryFragment, pictureFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        });
        // ----------------------------------------------------------------------------------------
        /**
         * Block defines right button control
         */
        rightButton = findViewById(R.id.imageButtonRight);
        rightButton.setOnClickListener(v -> {

            incrementImageCount();  // move counter to next image
            pictureFragment = PictureGalleryFragment.newInstance(imageIdentifer,getImageCount()); // create a new fragment with passed in info to select specified image

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();   // transaction object
            transaction.replace(R.id.pictureGalleryFragment, pictureFragment);  // swap and replace in second param. into first param
            transaction.addToBackStack(null);
            transaction.commit();

        });
        // ----------------------------------------------------------------------------------------


    }

    //----------------------------------------------------------------------------------------------
    // Definition of local helper methods and a local class
    // imageCount methods used to keep track of the photo that needs to be accessed

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
}