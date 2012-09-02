package com.instagram.android.adapter.row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.instagram.android.adapter.FeedAdapter;
import com.instagram.android.model.Hashtag;
import com.instagram.android.widget.ViewSwitchWidgetHelper;
import com.instagram.util.NumberUtil;

public class HashtagFeedHeaderRowAdapter
{
  public static void bindView(Holder paramHolder, Hashtag paramHashtag, Context paramContext, FeedAdapter paramFeedAdapter)
  {
    ViewSwitchWidgetHelper.bindViews(paramHolder.gridSwitchButton, paramHolder.listSwitchButton, paramFeedAdapter);
    TextView localTextView = paramHolder.name;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramHashtag.getTagName();
    localTextView.setText(String.format("#%s", arrayOfObject));
    paramHolder.count.setText(NumberUtil.formatNumberOfPhotos(paramHashtag.getMediaCount()));
  }

  public static View newView(Context paramContext)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903107, null);
    Holder localHolder = new Holder();
    Holder.access$002(localHolder, (TextView)localView.findViewById(2131493049));
    Holder.access$102(localHolder, (TextView)localView.findViewById(2131493050));
    localHolder.gridSwitchButton = ((Button)localView.findViewById(2131492973));
    localHolder.listSwitchButton = ((Button)localView.findViewById(2131492974));
    localView.setTag(localHolder);
    return localView;
  }

  public static class Holder
  {
    private TextView count;
    Button gridSwitchButton;
    Button listSwitchButton;
    private TextView name;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.HashtagFeedHeaderRowAdapter
 * JD-Core Version:    0.6.0
 */