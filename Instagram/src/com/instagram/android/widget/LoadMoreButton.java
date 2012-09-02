package com.instagram.android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ViewAnimator;

public class LoadMoreButton extends ViewAnimator
{
  private static final int INDEX_ALL_ITEMS_LOADED = 0;
  private static final int INDEX_LOADING = 1;
  private static final int INDEX_LOAD_MORE = 2;
  LoadMoreInterface mLoadMoreInterface;

  public LoadMoreButton(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public LoadMoreButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  private View createLoadMoreButton()
  {
    LargeGrayButton localLargeGrayButton = new LargeGrayButton(getContext());
    localLargeGrayButton.setText(2131230934);
    localLargeGrayButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        LoadMoreButton.this.mLoadMoreInterface.loadMore();
        LoadMoreButton.this.setDisplayedChild(1);
      }
    });
    localLargeGrayButton.setMinWidth(getResources().getDimensionPixelSize(2131427346));
    localLargeGrayButton.setGravity(17);
    return localLargeGrayButton;
  }

  private View createLoadingButton()
  {
    LargeGrayButton localLargeGrayButton = new LargeGrayButton(getContext());
    localLargeGrayButton.setEnabled(false);
    localLargeGrayButton.setText(2131230889);
    localLargeGrayButton.setMinWidth(getResources().getDimensionPixelSize(2131427346));
    localLargeGrayButton.setGravity(17);
    return localLargeGrayButton;
  }

  private void init()
  {
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2, 17);
    addView(createAllItemsLoadedButton(), localLayoutParams);
    addView(createLoadingButton(), localLayoutParams);
    addView(createLoadMoreButton(), localLayoutParams);
  }

  public void bind(LoadMoreInterface paramLoadMoreInterface)
  {
    this.mLoadMoreInterface = paramLoadMoreInterface;
    if (!paramLoadMoreInterface.isLoadMoreVisible())
      setVisibility(8);
    while (true)
    {
      return;
      setVisibility(0);
      if (paramLoadMoreInterface.isLoading())
      {
        setDisplayedChild(1);
        continue;
      }
      if (paramLoadMoreInterface.hasMoreItems())
      {
        setDisplayedChild(2);
        continue;
      }
      setDisplayedChild(0);
    }
  }

  protected View createAllItemsLoadedButton()
  {
    LargeGrayButton localLargeGrayButton = new LargeGrayButton(getContext());
    localLargeGrayButton.setEnabled(false);
    localLargeGrayButton.setText(2131230935);
    localLargeGrayButton.setMinWidth(getResources().getDimensionPixelSize(2131427346));
    localLargeGrayButton.setGravity(17);
    return localLargeGrayButton;
  }

  public static abstract interface LoadMoreInterface
  {
    public abstract boolean hasMoreItems();

    public abstract boolean isLoadMoreVisible();

    public abstract boolean isLoading();

    public abstract void loadMore();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.LoadMoreButton
 * JD-Core Version:    0.6.0
 */