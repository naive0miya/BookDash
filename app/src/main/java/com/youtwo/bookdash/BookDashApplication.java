package com.youtwo.bookdash;

import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Bill on 2017/8/19.
 */

public class BookDashApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/fonttype1.ttf")
        .setFontAttrId(R.attr.fontPath)
        .build()
    );
  }
}
