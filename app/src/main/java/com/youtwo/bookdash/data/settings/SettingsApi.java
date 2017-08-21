package com.youtwo.bookdash.data.settings;

import rx.Single;

/**
 * Created by Bill on 2017/8/20.
 */

public interface SettingsApi {

  boolean isFirstTime();

  void setIsFirstTime(boolean isFirstTime);

  Single<Boolean> isSubscribedToNewBookNotification();

  Single<Boolean> saveNewBookNotificationPreference(boolean onOff);
}
