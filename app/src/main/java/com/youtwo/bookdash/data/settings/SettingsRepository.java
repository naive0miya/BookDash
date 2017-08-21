package com.youtwo.bookdash.data.settings;

import rx.Single;

/**
 * Created by Bill on 2017/8/20.
 */

public interface SettingsRepository {

  boolean isFirstTime();

  void setIsFirstTime(boolean isFirstTime);

  Single<Boolean> isSubscribedToNewBookNotification();

  Single<Boolean> setNewBookNotificationStatus(boolean onOff);

  Single<Boolean> initialSubscribeToNewBookNotifications();
}
