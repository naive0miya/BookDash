package com.youtwo.bookdash.presentation.listbooks;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.youtwo.bookdash.R;
import com.youtwo.bookdash.presentation.bookinfo.BookInfoActivity;
import com.youtwo.bookdash.presentation.main.NavDrawerInterface;
import com.youtwo.bookdash.presentation.search.SearchActivity;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bill on 2017/8/20.
 */

public class ListBooksFragment extends Fragment {

  private NavDrawerInterface navDrawerInterface;
  private CircularProgressBar circularProgressBar;
  private RecyclerView recyclerViewBooks;
  private BookAdapter bookAdapter;

  private View.OnClickListener bookClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      BookViewHolder viewHolder = (BookViewHolder) v.getTag();
      FireBookDetails bookDetailResult = viewHolder.bookDetail;
      BookInfoActivity.startBookInfo(ListBooksFragment.this.getActivity(), bookDetailResult);
    }
  };

  private DialogInterface.OnClickListener languageClickListener = new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
      if (dialog != null) {
        dialog.dismiss();
      }
    }
  };

  public static Fragment newInstance() {
    return new ListBooksFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof NavDrawerInterface) {
      navDrawerInterface = (NavDrawerInterface) context;
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_list_books, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    circularProgressBar = (CircularProgressBar) view.findViewById(R.id.activity_loading_books);
    recyclerViewBooks = (RecyclerView) view.findViewById(R.id.recycler_view_books);
    recyclerViewBooks.setLayoutManager(
        new GridLayoutManager(getActivity(), getContext().getResources().getInteger(R.integer.book_span)));

    Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    if (navDrawerInterface != null) {
      navDrawerInterface.setToolbar(toolbar);
    }
    final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle(getString(R.string.book_dash));
    }
    setHasOptionsMenu(true);
    showBooks();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_main, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_language_choice) {
      showLanguagePopover();
      return true;
    }
    if (item.getItemId() == R.id.action_search_books) {
      SearchActivity.start(getActivity());
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void runUiThread(Runnable runnable) {
    Activity activity = getActivity();
    if (activity == null) {
      return;
    }
    activity.runOnUiThread(runnable);
  }

  public void showLanguagePopover() {
    final String[] languages = new String[]{"en", "cn", "usa"};
    final int selected = 0;
    runUiThread(new Runnable() {
      @Override
      public void run() {
        AlertDialog alertDialogLanguages = new AlertDialog.Builder(getActivity())
            .setTitle(getString(R.string.language_selection_heading))
            .setSingleChoiceItems(languages, selected, languageClickListener).create();
        alertDialogLanguages.show();
      }
    });

  }

  public void showBooks() {
    recyclerViewBooks.setVisibility(View.VISIBLE);
    circularProgressBar.setVisibility(View.GONE);
    FireBookDetails bookDetails = new FireBookDetails();
    final List<FireBookDetails> bookDetailList = new ArrayList<>();
    bookDetails.setBookTitle("Miss Tiny Chef");
    bookDetailList.add(bookDetails);
    bookDetails.setBookTitle("Mina and the Birthday Dress");
    bookDetailList.add(bookDetails);
    bookDetails.setBookTitle("Mogau's Gift");
    bookDetailList.add(bookDetails);
    bookDetails.setBookTitle("Hello");
    bookDetailList.add(bookDetails);
    bookDetails.setBookTitle("Tone's Big Drop");
    bookDetailList.add(bookDetails);
    bookDetails.setBookTitle("Thuli, Special and the Secret");
    bookDetailList.add(bookDetails);
    bookDetails.setBookTitle("Foxy Joxy Plays a Trick");
    bookDetailList.add(bookDetails);
    bookDetails.setBookTitle("What's in the Pot");
    bookDetailList.add(bookDetails);

    runUiThread(new Runnable() {
      @Override
      public void run() {
        bookAdapter = new BookAdapter(bookDetailList, ListBooksFragment.this.getActivity(), bookClickListener);
        recyclerViewBooks.setAdapter(bookAdapter);
      }
    });
  }

  @Override
  public void onDetach() {
    super.onDetach();
    navDrawerInterface = null;
  }
}
