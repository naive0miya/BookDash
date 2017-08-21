package com.youtwo.bookdash.presentation.listbooks;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bill on 2017/8/20.
 */

public class FireBookDetails implements Parcelable {
  private String bookTitle;

  public FireBookDetails() {

  }

  protected FireBookDetails(Parcel in) {
    bookTitle = in.readString();
  }

  public static final Creator<FireBookDetails> CREATOR = new Creator<FireBookDetails>() {
    @Override
    public FireBookDetails createFromParcel(Parcel in) {
      return new FireBookDetails(in);
    }

    @Override
    public FireBookDetails[] newArray(int size) {
      return new FireBookDetails[size];
    }
  };

  public String getBookTitle() {
    return bookTitle;
  }

  public void setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.bookTitle);
  }
}
