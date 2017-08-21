package com.youtwo.bookdash.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.youtwo.bookdash.R;
import com.youtwo.bookdash.data.settings.SettingsApiImpl;
import com.youtwo.bookdash.data.settings.SettingsRepositories;
import com.youtwo.bookdash.data.settings.SettingsRepository;
import com.youtwo.bookdash.presentation.main.MainActivity;
import java.util.ArrayList;
import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

/**
 * Created by Bill on 2017/8/20.
 */

public class SplashActivity extends AppCompatActivity {

  private static final int SPLASH_SCREEN_REQUEST_CODE = 1;
  private SettingsRepository repositories;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    repositories = SettingsRepositories.getInstance(new SettingsApiImpl(getApplicationContext()));
    showSplashAfterDelay();
  }

  private void showSplashAfterDelay() {
    (new Handler()).postDelayed(new Runnable() {
      @Override
      public void run() {
        if (repositories.isFirstTime()) {
          loadTutorial();
        } else {
          loadMainScreen();
        }
      }
    }, 1000);

  }

  public void loadTutorial() {
    Intent mainAct = new Intent(SplashActivity.this, MaterialTutorialActivity.class);
    mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(getApplicationContext()));
    startActivityForResult(mainAct, SPLASH_SCREEN_REQUEST_CODE);
  }

  public void loadMainScreen() {
    Intent mainAct = new Intent(SplashActivity.this, MainActivity.class);
    startActivity(mainAct);
    finish();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == SPLASH_SCREEN_REQUEST_CODE) {
      repositories.setIsFirstTime(false);
      loadMainScreen();
    }
  }

  public ArrayList<TutorialItem> getTutorialItems(Context context) {
    TutorialItem tutorialItem1 = new TutorialItem(context.getString(R.string.slide_1_african_story_books), context.getString(R.string.slide_1_african_story_books_subtitle),
        R.color.slide_1, R.drawable.tut_page_1_front,  R.drawable.tut_page_1_background);

    TutorialItem tutorialItem2 = new TutorialItem(context.getString(R.string.slide_2_volunteer_professionals), context.getString(R.string.slide_2_volunteer_professionals_subtitle),
        R.color.slide_2,  R.drawable.tut_page_2_front,  R.drawable.tut_page_2_background);

    TutorialItem tutorialItem3 = new TutorialItem(context.getString(R.string.slide_3_download_and_go), context.getString(R.string.slide_3_download_and_go_subtitle),
        R.color.slide_3, R.drawable.tut_page_3_foreground,  R.drawable.tut_page_3_background);

    TutorialItem tutorialItem4 = new TutorialItem(context.getString(R.string.slide_4_different_languages), context.getString(R.string.slide_4_different_languages_subtitle),
        R.color.slide_4,  R.drawable.tut_page_4_foreground,  R.drawable.tut_page_4_background);

    ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
    tutorialItems.add(tutorialItem1);
    tutorialItems.add(tutorialItem2);
    tutorialItems.add(tutorialItem3);
    tutorialItems.add(tutorialItem4);

    return tutorialItems;
  }
}
