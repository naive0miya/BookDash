package com.youtwo.bookdash.presentation.bookinfo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.youtwo.bookdash.BaseActivity;
import com.youtwo.bookdash.R;
import com.youtwo.bookdash.presentation.listbooks.FireBookDetails;
import mbanje.kurt.fabbutton.FabButton;

/**
 * Created by Bill on 2017/8/20.
 */

public class BookInfoActivity extends BaseActivity {

  public static final String BOOK_PARCEL = "book_parcel";
  private CollapsingToolbarLayout collapsingToolbarLayout;
  private FabButton floatingActionButton;
  private View gradientBackground;
  private RecyclerView contributorRecyclerView;
  private View scrollView;
  private ImageView imageViewBook;
  private AppBarLayout appBarLayout;
  private CoordinatorLayout coordinatorLayout;
  private ProgressBar loadingProgressBar;
  private CardView contributorCard, mainBookCard;
  private Toolbar toolbar;
  private ActionBar actionBar;
  private FireBookDetails bookInfo;
  private View download;
  private FireBookDetails bookDetailParcelable;

  public static void startBookInfo(Activity activity,  FireBookDetails bookDetails) {
    Intent intent = new Intent(activity, BookInfoActivity.class);
    intent.putExtra(BookInfoActivity.BOOK_PARCEL, bookDetails);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
   setContentView(R.layout.activity_book_information);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().getSharedElementReturnTransition()
          .addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
              floatingActionButton.animate().scaleY(0).scaleX(0)
                  .setInterpolator(new AccelerateInterpolator())
                  .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                  .start();

            }

            @Override
            public void onTransitionEnd(Transition transition) {
              enterAnimation();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
          });
      postponeEnterTransition();
    }

    contributorRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_contributors);
    contributorCard = (CardView) findViewById(R.id.contributor_card);
    imageViewBook = (ImageView) findViewById(R.id.image_view_book_cover);
    appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
    mainBookCard = (CardView) findViewById(R.id.card_view_main_book_info);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_content);
    loadingProgressBar = (ProgressBar) findViewById(R.id.activity_loading_book_info);
    download = findViewById(R.id.fab_download);
    collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

    setSupportActionBar(toolbar);
    actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
    scrollView = findViewById(R.id.scrollViewBookInfo);

    gradientBackground = findViewById(R.id.toolbar_background_gradient);
    floatingActionButton = (FabButton) findViewById(R.id.fab_download);
    floatingActionButton.setScaleX(0);
    floatingActionButton.setScaleY(0);


    download.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        floatingActionButton.resetIcon();
        floatingActionButton.showProgress(true);
        floatingActionButton.setProgress(0);
      }
    });

    calculateLayoutHeight();

    imageViewBook.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override
      public boolean onPreDraw() {
        imageViewBook.getViewTreeObserver().removeOnPreDrawListener(this);

        enterAnimation();
        return true;
      }
    });

    bookDetailParcelable = getIntent().getParcelableExtra(BOOK_PARCEL);

    final OnPreDrawListener listener = new OnPreDrawListener() {
      @Override
      public boolean onPreDraw() {
        toolbar.getViewTreeObserver().removeOnPreDrawListener(this);
        toolbar.setTitle("");
        return true;
      }
    };
    toolbar.getViewTreeObserver().addOnPreDrawListener(listener);
    setToolbarTitle(bookDetailParcelable.getBookTitle());

    runUiThread(new Runnable() {
      @Override
      public void run() {
        Glide.with(getApplicationContext()).load(R.drawable.bookdash_placeholder).asBitmap().into(new SimpleTarget<Bitmap>() {
          @Override
          public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            imageViewBook.setImageBitmap(resource);
            extractPaletteColors(resource);
          }
        });
      }
    });
  }

  private void runUiThread(Runnable runnable) {

    this.runOnUiThread(runnable);
  }

  private void extractPaletteColors(Bitmap resource) {
    Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
      @Override
      public void onGenerated(Palette palette) {
        if (null == palette) {
          return;
        }
        Palette.Swatch toolbarSwatch = palette.getMutedSwatch();

        int toolbarColor = toolbarSwatch != null ? toolbarSwatch.getRgb() : ContextCompat
            .getColor(getApplicationContext(), R.color.colorPrimary);
        int accentColor = palette
            .getVibrantColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

        setToolbarColor(toolbarColor);
        setAccentColor(accentColor);

        if (toolbarSwatch != null) {
          float[] darkerShade = toolbarSwatch.getHsl();
          darkerShade[2] = darkerShade[2] * 0.8f; //Make it a darker shade for the status bar
          setStatusBarColor(ColorUtils.HSLToColor(darkerShade));
        } else {
          setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        }
      }
    });
  }

  private void setToolbarColor(int color) {
    if (collapsingToolbarLayout != null) {
      collapsingToolbarLayout.setStatusBarScrimColor(color);
      collapsingToolbarLayout.setContentScrimColor(color);
    } else {
      actionBar.setBackgroundDrawable(new ColorDrawable(color));
    }
    floatingActionButton.setRingProgressColor(color);

    if (gradientBackground != null) {
      GradientDrawable gradientDrawable = new GradientDrawable();
      gradientDrawable.setColor(color);
      gradientDrawable.setAlpha(140);
      gradientBackground.setBackground(gradientDrawable);
    }
  }


  private void setAccentColor(int accentColor) {
    floatingActionButton.setColor(accentColor);

  }

  private void setStatusBarColor(int color) {
    if (isFinishing()) {
      return;
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.setStatusBarColor(color);
    }
  }

  public void setToolbarTitle(String title) {

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(title);
    }
    if (collapsingToolbarLayout != null) {
      collapsingToolbarLayout.setTitle(title);
    }
  }

  private void enterAnimation() {

    floatingActionButton.setScaleX(0);
    floatingActionButton.setScaleY(0);
    floatingActionButton.animate().setStartDelay(500).scaleY(1).scaleX(1)
        .setInterpolator(new OvershootInterpolator())
        .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime)).start();
  }

  @SuppressWarnings("SuspiciousNameCombination")
  private void calculateLayoutHeight() {
    if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
      DisplayMetrics dMetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
      CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) imageViewBook
          .getLayoutParams();
      lp.height = dMetrics.widthPixels;
      imageViewBook.setLayoutParams(lp);

      CoordinatorLayout.LayoutParams lp2 = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
      lp2.height = dMetrics.widthPixels;
      appBarLayout.setLayoutParams(lp2);
    }
  }

  public void showBookDetailView() {
    mainBookCard.setVisibility(View.VISIBLE);
    loadingProgressBar.setVisibility(View.GONE);
    coordinatorLayout.setVisibility(View.VISIBLE);
    scrollView.setVisibility(View.VISIBLE);
    floatingActionButton.setVisibility(View.VISIBLE);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_book_info, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();

    if (id == R.id.action_share_book) {
      sendShareEvent(bookDetailParcelable.getBookTitle());
      return true;
    } else if (id == android.R.id.home) {
      onBackPressed();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void sendShareEvent(String bookTitle) {
    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.sharing_book_title, bookTitle));
    sendIntent.setType("text/plain");
    startActivity(sendIntent);
  }
}
