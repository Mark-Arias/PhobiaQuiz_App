package com.example.phobiaquiz_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //TextView tvProgressLabel;
    ImageView image;
    ImageButton leftButton;
    ImageButton rightButton;
    ImageButton submitButton;
    SeekBar seekBar;
    ListView listView;
    PictureGalleryFragment pictureFragment;
    int imageCount = 1;
    String imageIdentifier = "picture";
    TestItem item;
    HashMap<String,String>  userTestResults;   // hashmap to store test results for 1 run
                                                // picture name and sentiment score are stored
    Context context;

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
        context = getApplicationContext();

        userTestResults = new HashMap<>();
        //item = new TestItem(imageCount,this);
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
                //item.setUserEmotionalResponseScore(progress);   // set score to seek value user stopped at

                String temp = imageIdentifier + getImageCount();
                System.out.println("Value: " + temp);
                userTestResults.put(temp,Integer.toString(seekBar.getProgress()));

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
            //String temp = imageIdentifier + getImageCount();
            //userTestResults.put(temp,)

        });
        // ----------------------------------------------------------------------------------------
        /**
         * Block defines right button control
         */
        rightButton = findViewById(R.id.imageButtonRight);
        rightButton.setOnClickListener(v -> {

            if(seekBar.getProgress() == 50) {   // user left seekBar in default state
                String temp = imageIdentifier + getImageCount();
                userTestResults.put(temp,Integer.toString(seekBar.getProgress()));
            }

            resetSeekBar();
            incrementImageCount();
            changeImageFragment(imageIdentifier,getImageCount());



        });
        // ----------------------------------------------------------------------------------------
        /**
         * Block defines submit button
         *
         * User uses this button to turn in the results they have logged and
         * should load a results activity with their scores and info about their psychology test run
         *
         * See if I can disable this button until user has ranked all images
         * Display a toast to user if they have not finished quiz
         * display a completion toast upon successful attempt
         *
         * use the results stored inside the hashmap to build out the results for the user
         */
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            if(userTestResults.size() > 7) {    //user finished quiz, launch results activity
                Toast.makeText(getApplicationContext(), "Submission Successful", Toast.LENGTH_SHORT).show();
                //----------
                // ** Debug Code **
                // going to iterate through hashmap to see what values are stored there
                System.out.println("Results");
                for(int i = 1;i <= userTestResults.size(); i++) {
                    String temp = "picture" + i;
                    System.out.println(temp);
                    System.out.println(userTestResults.get(temp));
                }

                // Code block below begins results activity and passes over needed data
                String userNameTemp = "Adam";
                Intent resultActivityIntent = new Intent(this, ResultsActivity.class);
                resultActivityIntent.putExtra("data",userTestResults);  // send hashmap with results data
                resultActivityIntent.putExtra("userName",userNameTemp); // send user name
                startActivity(resultActivityIntent);    // launch new activity
                //-----------
            } else {        // user did not finish quiz
                Toast.makeText(getApplicationContext(), "Please finish quiz before submission", Toast.LENGTH_SHORT).show();
            }

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