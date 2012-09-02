package com.instagram.android.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.instagram.android.location.Venue;

public class MapImageViewHelper
{
  public static final String ZOOM = "14";

  public static String constructStaticMapUrl(Venue paramVenue, String paramString, int paramInt1, int paramInt2, Double paramDouble1, Double paramDouble2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramDouble1;
    arrayOfObject[1] = paramDouble2;
    localStringBuilder.append(String.format("http://maps.google.com/maps/api/staticmap?center=%s,%s", arrayOfObject));
    if (paramVenue.name != null)
      localStringBuilder.append("&markers=color:0xe45846%7C").append(paramDouble1).append(",").append(paramDouble2);
    localStringBuilder.append("&zoom=").append(paramString).append("&size=").append(paramInt1).append("x").append(paramInt2).append("&maptype=road&sensor=false&style=feature:landscape%7Celement:geometry%7Chue:0xf0eade%7csaturation:8&style=feature:road%7Celement:geometry%7Chue:0xf0d59f%7Csaturation:34%7Clightness:30");
    return localStringBuilder.toString();
  }

  public static class MapClickListener
    implements View.OnClickListener
  {
    private final Context mContext;
    private final Double mLatitude;
    private final Double mLongitude;
    private final CharSequence mName;
    private final String mZoom;

    public MapClickListener(Context paramContext, Double paramDouble1, Double paramDouble2, CharSequence paramCharSequence, String paramString)
    {
      this.mContext = paramContext;
      this.mLatitude = paramDouble1;
      this.mLongitude = paramDouble2;
      this.mName = paramCharSequence;
      this.mZoom = paramString;
    }

    public void onClick(View paramView)
    {
      IgDialogBuilder localIgDialogBuilder = new IgDialogBuilder(this.mContext);
      MapImageViewHelper.MapClickListener.1 local1 = new MapImageViewHelper.MapClickListener.1(this);
      localIgDialogBuilder.setMessage(2131230937).setPositiveButton(2131230938, local1).setNegativeButton(2131230921, local1).setCancelable(false).create().show();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.MapImageViewHelper
 * JD-Core Version:    0.6.0
 */