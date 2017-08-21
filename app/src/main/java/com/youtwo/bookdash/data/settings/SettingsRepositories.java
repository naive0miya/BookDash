package com.youtwo.bookdash.data.settings;

import android.support.annotation.NonNull;

/**
 * Created by Bill on 2017/8/20.
 */

public class SettingsRepositories {

  private static SettingsRepository repository = null;

  private SettingsRepositories() {
    // no instance
  }

  public synchronized static SettingsRepository getInstance(@NonNull SettingsApi settingsApi
     ) {
    if (null == repository) {
      repository = new SettingsRepositoryImpl(settingsApi);
    }
    return repository;
  }
}
