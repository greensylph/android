package com.instagram.android.fragment;

import android.text.TextUtils;
import com.instagram.android.widget.SearchEditText;
import com.instagram.android.widget.SearchEditText.SearchEditTextListener;

class SearchFragment$1 extends SearchEditText.SearchEditTextListener
{
  public void onSearchSubmitted(SearchEditText paramSearchEditText, String paramString)
  {
    SearchFragment.access$000(this.this$0).clearFocus();
    if ((paramString.equalsIgnoreCase(SearchFragment.access$100(this.this$0))) || (TextUtils.isEmpty(paramString)));
    while (true)
    {
      return;
      this.this$0.performQuery(paramString);
      SearchFragment.access$102(this.this$0, paramString);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SearchFragment.1
 * JD-Core Version:    0.6.0
 */