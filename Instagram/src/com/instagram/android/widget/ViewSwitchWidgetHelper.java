package com.instagram.android.widget;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.instagram.android.adapter.FeedAdapter;
import com.instagram.android.adapter.FeedAdapter.ViewMode;
import com.instagram.android.adapter.FeedAdapter.ViewTypeChangeListener;

public class ViewSwitchWidgetHelper
{
  private static void bindViewInternal(FeedAdapter.ViewMode paramViewMode, Button paramButton1, Button paramButton2, FeedAdapter paramFeedAdapter)
  {
    boolean bool1 = true;
    boolean bool2;
    if (paramViewMode == FeedAdapter.ViewMode.FEED)
    {
      paramButton1.setOnClickListener(new SwitchModeListener(paramFeedAdapter, paramButton1, paramButton2));
      paramButton2.setOnClickListener(null);
      if (paramViewMode != FeedAdapter.ViewMode.GRID)
        break label82;
      bool2 = bool1;
      label40: paramButton1.setSelected(bool2);
      if (paramViewMode != FeedAdapter.ViewMode.FEED)
        break label88;
    }
    while (true)
    {
      paramButton2.setSelected(bool1);
      return;
      paramButton2.setOnClickListener(new SwitchModeListener(paramFeedAdapter, paramButton1, paramButton2));
      paramButton1.setOnClickListener(null);
      break;
      label82: bool2 = false;
      break label40;
      label88: bool1 = false;
    }
  }

  public static void bindViews(Button paramButton1, Button paramButton2, FeedAdapter paramFeedAdapter)
  {
    bindViewInternal(paramFeedAdapter.getViewMode(), paramButton1, paramButton2, paramFeedAdapter);
    paramFeedAdapter.setViewTypeChangeListener(new FeedAdapter.ViewTypeChangeListener(paramButton1, paramButton2, paramFeedAdapter)
    {
      public void onChange(FeedAdapter.ViewMode paramViewMode1, FeedAdapter.ViewMode paramViewMode2)
      {
        ViewSwitchWidgetHelper.access$000(paramViewMode2, this.val$gridButton, this.val$listButton, this.val$adapter);
      }
    });
  }

  private static class SwitchModeListener
    implements View.OnClickListener
  {
    private final FeedAdapter mAdapter;
    private final Button mGridButton;
    private final Button mListButton;

    public SwitchModeListener(FeedAdapter paramFeedAdapter, Button paramButton1, Button paramButton2)
    {
      this.mAdapter = paramFeedAdapter;
      this.mGridButton = paramButton1;
      this.mListButton = paramButton2;
    }

    public void onClick(View paramView)
    {
      boolean bool1 = true;
      this.mAdapter.switchMode();
      Button localButton1 = this.mGridButton;
      boolean bool2;
      Button localButton2;
      if (this.mAdapter.getViewMode() == FeedAdapter.ViewMode.GRID)
      {
        bool2 = bool1;
        localButton1.setSelected(bool2);
        localButton2 = this.mListButton;
        if (this.mAdapter.getViewMode() != FeedAdapter.ViewMode.FEED)
          break label68;
      }
      while (true)
      {
        localButton2.setSelected(bool1);
        return;
        bool2 = false;
        break;
        label68: bool1 = false;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.ViewSwitchWidgetHelper
 * JD-Core Version:    0.6.0
 */