package com.youtwo.bookdash.presentation.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.youtwo.bookdash.R;
import com.youtwo.bookdash.data.settings.SettingsApiImpl;
import com.youtwo.bookdash.data.settings.SettingsRepositories;
import com.youtwo.bookdash.presentation.main.NavDrawerInterface;
import java.util.ArrayList;
import rx.Observer;
import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

/**
 * Created by Bill on 2017/8/20.
 */

public class SettingsFragment extends PreferenceFragmentCompat {

  public static final String TUTORIAL_DISPLAY_KEY = "tutorial_display_key";
  private NavDrawerInterface navDrawerInterface;

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    addPreferencesFromResource(R.xml.app_preferences);
    setupTutorialDisplayPreference();
    setupNewBookNotificationPreference();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof NavDrawerInterface) {
      navDrawerInterface = (NavDrawerInterface) context;
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    navDrawerInterface = null;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {

      case android.R.id.home:
        if (navDrawerInterface != null) {
          navDrawerInterface.openNavDrawer();
        }
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Toolbar toolbar =  (Toolbar) view.findViewById(R.id.toolbar);

    navDrawerInterface.setToolbar(toolbar);

    final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  private void setupTutorialDisplayPreference() {
    Preference tutorialPreferenceItem = findPreference(TUTORIAL_DISPLAY_KEY);
    tutorialPreferenceItem.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      @Override
      public boolean onPreferenceClick(Preference preference) {
        showTutorialScreen(getTutorialItems());
        return true;
      }
    });
  }

  public void showTutorialScreen(ArrayList<TutorialItem> tutorialItems) {
    Intent mainAct = new Intent(getContext(), MaterialTutorialActivity.class);
    mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS,
        tutorialItems);
    startActivity(mainAct);
  }

  public ArrayList<TutorialItem> getTutorialItems() {
    final Context mContext = getContext();
    TutorialItem tutorialItem1 = new TutorialItem(mContext.getString(R.string.slide_1_african_story_books),
        mContext.getString(R.string.slide_1_african_story_books_subtitle), R.color.slide_1,
        R.drawable.tut_page_1_front, R.drawable.tut_page_1_background);

    TutorialItem tutorialItem2 = new TutorialItem(mContext.getString(R.string.slide_2_volunteer_professionals),
        mContext.getString(R.string.slide_2_volunteer_professionals_subtitle), R.color.slide_2,
        R.drawable.tut_page_2_front, R.drawable.tut_page_2_background);

    TutorialItem tutorialItem3 = new TutorialItem(mContext.getString(R.string.slide_3_download_and_go),
        mContext.getString(R.string.slide_3_download_and_go_subtitle), R.color.slide_3,
        R.drawable.tut_page_3_foreground, R.drawable.tut_page_3_background);

    TutorialItem tutorialItem4 = new TutorialItem(mContext.getString(R.string.slide_4_different_languages),
        mContext.getString(R.string.slide_4_different_languages_subtitle), R.color.slide_4,
        R.drawable.tut_page_4_foreground, R.drawable.tut_page_4_background);

    ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
    tutorialItems.add(tutorialItem1);
    tutorialItems.add(tutorialItem2);
    tutorialItems.add(tutorialItem3);
    tutorialItems.add(tutorialItem4);

    return tutorialItems;
  }

  private void setupNewBookNotificationPreference() {
    Preference notificationPreference = findPreference(SettingsApiImpl.PREF_IS_SUBSCRIBED_NEW_BOOK_NOTIFICATIONS);
    notificationPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
      @Override
      public boolean onPreferenceChange(final Preference preference, final Object newValue) {
        SettingsRepositories.getInstance(new SettingsApiImpl(getActivity())).setNewBookNotificationStatus((Boolean)newValue).subscribe(new Observer<Boolean>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(final Throwable e) {

          }

          @Override
          public void onNext(final Boolean aBoolean) {

          }
        });
        return true;
      }
    });
  }
}
