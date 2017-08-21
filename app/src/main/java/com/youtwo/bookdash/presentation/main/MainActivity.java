package com.youtwo.bookdash.presentation.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.youtwo.bookdash.BaseActivity;
import com.youtwo.bookdash.R;
import com.youtwo.bookdash.presentation.listbooks.ListBooksFragment;
import com.youtwo.bookdash.presentation.setting.SettingsFragment;

public class MainActivity extends BaseActivity implements NavDrawerInterface {

  private DrawerLayout drawerLayout;
  private NavigationView navigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    navigationView = (NavigationView) findViewById(R.id.navigation_view);

    final ActionBar actionBar = getSupportActionBar();

    if (actionBar != null) {
      actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    setUpNavDrawer();
    showAllBooks();
  }

  private void setUpNavDrawer() {

    navigationView.setCheckedItem(R.id.action_all_books);
    navigationView
        .setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

              case R.id.action_all_books: {
                break;
              }
              case R.id.action_downloads:
                break;
              case R.id.action_about:
                break;
              case R.id.action_settings: {
                showSettingsScreen();
                break;
              }
              case R.id.action_thanks: {
                showThanksPopover();
                break;
              }
              case R.id.action_invite_friends: {
                break;
              }
              case R.id.action_rate_app: {
                break;
              }
              default:

            }
            drawerLayout.closeDrawers();
            if (menuItem.getItemId() == R.id.action_thanks || menuItem
                .getItemId() == R.id.action_invite_friends
                || menuItem.getItemId() == R.id.action_rate_app) {
              return false;
            } else {
              return true;
            }

          }
        });
  }

  @Override
  public void openNavDrawer() {
    drawerLayout.openDrawer(navigationView);
  }

  @Override
  public void closeNavDrawer() {
    drawerLayout.closeDrawer(navigationView);
  }

  @Override
  public void setToolbar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        drawerLayout.openDrawer(GravityCompat.START);
      }
    });
  }

  public void showAllBooks() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction ft = fragmentManager.beginTransaction();
    Fragment f = ListBooksFragment.newInstance();
    ft.replace(R.id.fragment_content, f, "ALLBOOKS");
    ft.commit();
  }

  public void showSettingsScreen() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction ft = fragmentManager.beginTransaction();
    Fragment settingsFragment = new SettingsFragment();
    ft.replace(R.id.fragment_content, settingsFragment, "SETTINGS");
    ft.commit();
  }

  public void showThanksPopover() {
    AlertDialog.Builder thanksDialog = new AlertDialog.Builder(this);
    thanksDialog.setTitle(getString(R.string.contributions_to_app));
    thanksDialog.setMessage(Html.fromHtml(getString(R.string.list_of_contributors)));

    thanksDialog.setPositiveButton(android.R.string.ok, null);
    AlertDialog ad = thanksDialog.show();
    ((TextView) ad.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

  }
}
