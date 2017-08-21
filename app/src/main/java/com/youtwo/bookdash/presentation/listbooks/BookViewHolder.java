package com.youtwo.bookdash.presentation.listbooks;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.youtwo.bookdash.R;

/**
 * Created by Bill on 2017/8/20.
 */

public class BookViewHolder extends RecyclerView.ViewHolder {
  public TextView bookTitle;
  public ImageView bookCover;
  public CardView cardContainer;
  public FireBookDetails bookDetail;
  public ImageView downloadedIcon;

  public BookViewHolder(View v) {
    super(v);
    cardContainer = (CardView) v.findViewById(R.id.card_view);
    bookTitle = (TextView) v.findViewById(R.id.textViewBookName);
    bookCover = (ImageView) v.findViewById(R.id.imageViewBookCover);
    downloadedIcon = (ImageView) v.findViewById(R.id.imageViewBookDownloaded);
  }
}
