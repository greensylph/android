package com.instagram.android.fragment;

import com.instagram.android.service.ActionBarService;
import com.instagram.android.widget.EnhancedWebView;
import com.instagram.android.widget.EnhancedWebView.OnLoadingChangeListener;

class NewsFragment$3
  implements EnhancedWebView.OnLoadingChangeListener
{
  public void onLoadingChange(EnhancedWebView paramEnhancedWebView, boolean paramBoolean)
  {
    if ((NewsFragment.access$000(this.this$0) == paramEnhancedWebView) && (paramEnhancedWebView.isShown()))
      ActionBarService.getInstance(this.this$0.getActivity()).setIsLoading(paramBoolean);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.NewsFragment.3
 * JD-Core Version:    0.6.0
 */