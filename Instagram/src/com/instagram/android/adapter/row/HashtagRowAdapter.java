package com.instagram.android.adapter.row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.instagram.android.model.Hashtag;
import com.instagram.util.NumberUtil;

public class HashtagRowAdapter
{
  public static void bindView(Holder paramHolder, Hashtag paramHashtag, Context paramContext)
  {
    TextView localTextView = paramHolder.name;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramHashtag.getTagName();
    localTextView.setText(String.format("#%s", arrayOfObject));
    paramHolder.count.setText(NumberUtil.formatNumberOfPhotos(paramHashtag.getMediaCount()));
  }

  public static View newView(Context paramContext)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903106, null);
    Holder localHolder = new Holder();
    Holder.access$002(localHolder, (TextView)localView.findViewById(2131493047));
    Holder.access$102(localHolder, (TextView)localView.findViewById(2131493048));
    localView.setTag(localHolder);
    return localView;
  }

  public static class Holder
  {
    private TextView count;
    private TextView name;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.HashtagRowAdapter
 * JD-Core Version:    0.6.0
 */