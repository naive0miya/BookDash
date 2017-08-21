package com.youtwo.bookdash.presentation.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Bill on 2017/8/20.
 */

public class SquareImageView extends AppCompatImageView {
  public SquareImageView(Context context) {
    super(context);
  }

  public SquareImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onMeasure(int widthSpec, int heightSpec) {

    super.onMeasure(widthSpec, widthSpec);
  }
}
