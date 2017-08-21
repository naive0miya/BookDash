package com.youtwo.bookdash.presentation.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.youtwo.bookdash.BaseActivity;
import com.youtwo.bookdash.R;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by Bill on 2017/8/20.
 */

public class SearchActivity extends BaseActivity {

  private Toolbar toolbar;
  private String searchQuery;
  private RecyclerView recyclerViewBooks;
  private CircularProgressBar circularProgressBar;

  public static void start(final Activity activity) {
    Intent intent = new Intent(activity, SearchActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(getString(R.string.search_query_hint));
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

    ActionBar actionBar = getSupportActionBar();

    setSupportActionBar(toolbar);

    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setDisplayUseLogoEnabled(false);
      actionBar.setDisplayShowHomeEnabled(true);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {
    getMenuInflater().inflate(R.menu.menu_search, menu);

    final MenuItem item = menu.findItem(R.id.action_menu_search);
    final SearchView searchView = (SearchView) item.getActionView();

    toolbar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        item.expandActionView();
        searchView.setQuery(searchQuery, false);
      }
    });

    if (searchView != null) {
      item.expandActionView();
      if (searchQuery != null) {
        toolbar.setTitle(searchQuery);
        searchView.setQuery(searchQuery, false);

      }

      searchView.setQueryHint(getString(R.string.search_query_hint));
      searchView.setIconified(false);

      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
          if (TextUtils.isEmpty(query)) {
            return true;
          }
          item.collapseActionView();
          toolbar.setTitle(query);
          return true;
        }

        @Override
        public boolean onQueryTextChange(String query) {
          if (TextUtils.isEmpty(query)) {
            return true;
          }
          toolbar.setTitle(query);
          return true;
        }
      });
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
