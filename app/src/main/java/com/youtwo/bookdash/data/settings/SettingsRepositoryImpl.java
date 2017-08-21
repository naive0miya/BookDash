package com.youtwo.bookdash.data.settings;

import android.support.annotation.NonNull;
import java.util.concurrent.Callable;
import rx.Single;
import rx.functions.Func1;

/**
 * Created by Bill on 2017/8/20.
 */

public class SettingsRepositoryImpl implements SettingsRepository {

  public static final String TOPIC_NEW_BOOK_NOTIFICATIONS = "new_book_notifications";

  private final SettingsApi settingsApi;

  public SettingsRepositoryImpl(@NonNull SettingsApi settingsApi) {
    this.settingsApi = settingsApi;
  }

  @Override
  public boolean isFirstTime() {
    return settingsApi.isFirstTime();
  }

  @Override
  public void setIsFirstTime(boolean isFirstTime) {
    settingsApi.setIsFirstTime(isFirstTime);
  }

  @Override
  public Single<Boolean> isSubscribedToNewBookNotification() {
    return settingsApi.isSubscribedToNewBookNotification();
  }

  @Override
  public Single<Boolean> setNewBookNotificationStatus(final boolean onOff) {
    return Single.defer(new Callable<Single<Boolean>>() {
      @Override
      public Single<Boolean> call() throws Exception {
        if (onOff) {
          //firebaseMessaging.subscribeToTopic(TOPIC_NEW_BOOK_NOTIFICATIONS);
        } else {
          //firebaseMessaging.unsubscribeFromTopic(TOPIC_NEW_BOOK_NOTIFICATIONS);
        }
        return settingsApi.saveNewBookNotificationPreference(onOff);
      }
    });
  }

  @Override
  public Single<Boolean> initialSubscribeToNewBookNotifications() {
    return isSubscribedToNewBookNotification().flatMap(new Func1<Boolean, Single<Boolean>>() {

      @Override
      public Single<Boolean> call(final Boolean aBoolean) {
        if (aBoolean) {
          //firebaseMessaging.subscribeToTopic(TOPIC_NEW_BOOK_NOTIFICATIONS);
        } else {
          //firebaseMessaging.unsubscribeFromTopic(TOPIC_NEW_BOOK_NOTIFICATIONS);
        }
        return Single.just(aBoolean);
      }
    });
  }
}
