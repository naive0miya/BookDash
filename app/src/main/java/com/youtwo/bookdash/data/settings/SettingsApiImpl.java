package com.youtwo.bookdash.data.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import java.util.concurrent.Callable;
import rx.Single;

/**
 * Created by Bill on 2017/8/20.
 */

public class SettingsApiImpl implements SettingsApi {

  private final Context context;
  public static final String PREF_IS_SUBSCRIBED_NEW_BOOK_NOTIFICATIONS = "pref_new_book_notification";
  private static final String PREF_IS_FIRST_TIME = "is_first_time";

  public SettingsApiImpl(Context context) {
    this.context = context;
  }

  @Override
  public boolean isFirstTime() {
    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_IS_FIRST_TIME, true);

  }

  @Override
  public void setIsFirstTime(boolean isFirstTime) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putBoolean(PREF_IS_FIRST_TIME, isFirstTime);
    editor.apply();
  }

  @Override
  public Single<Boolean> isSubscribedToNewBookNotification() {
    return Single.defer(new Callable<Single<Boolean>>() {
      @Override
      public Single<Boolean> call() throws Exception {
        return Single.just(PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(PREF_IS_SUBSCRIBED_NEW_BOOK_NOTIFICATIONS, true));
      }
    });
  }

  @Override
  public Single<Boolean> saveNewBookNotificationPreference(final boolean onOff) {
    return Single.defer(new Callable<Single<Boolean>>() {
      @Override
      public Single<Boolean> call() throws Exception {
        return Single.just(PreferenceManager.getDefaultSharedPreferences(context).edit()
            .putBoolean(PREF_IS_SUBSCRIBED_NEW_BOOK_NOTIFICATIONS, onOff).commit());
      }
    });
  }
}
