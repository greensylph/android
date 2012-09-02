package com.instagram.android.adapter.row;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.instagram.android.adapter.FeedAdapter;
import com.instagram.android.imagecache.IgImageView;
import com.instagram.android.location.Venue;
import com.instagram.android.widget.MapImageViewHelper;
import com.instagram.android.widget.MapImageViewHelper.MapClickListener;
import com.instagram.android.widget.ViewSwitchWidgetHelper;
import com.instagram.util.ViewUtil;

public class LocationRowAdapter
{
  public static void bindView(Holder paramHolder, Venue paramVenue, Context paramContext, FeedAdapter paramFeedAdapter)
  {
    ViewSwitchWidgetHelper.bindViews(paramHolder.gridSwitchButton, paramHolder.listSwitchButton, paramFeedAdapter);
    paramHolder.map.setUrl(MapImageViewHelper.constructStaticMapUrl(paramVenue, "14", ViewUtil.getScreenWidthPixels(paramHolder.map.getContext()), paramContext.getResources().getDimensionPixelSize(2131427338), paramVenue.latitude, paramVenue.longitude));
    paramHolder.map.setOnClickListener(new MapImageViewHelper.MapClickListener(paramHolder.map.getContext(), paramVenue.latitude, paramVenue.longitude, paramVenue.name, "14"));
    paramHolder.location.setText(paramVenue.name);
    paramHolder.address.setText(paramVenue.address);
    paramHolder.address.setVisibility(ViewUtil.makeVisibilityGoneIfBlank(paramVenue.address));
  }

  public static View newView(Context paramContext)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903110, null);
    Holder localHolder = new Holder();
    Holder.access$002(localHolder, (IgImageView)localView.findViewById(2131493054));
    Holder.access$102(localHolder, (TextView)localView.findViewById(2131493052));
    Holder.access$202(localHolder, (TextView)localView.findViewById(2131493053));
    localHolder.gridSwitchButton = ((Button)localView.findViewById(2131492973));
    localHolder.listSwitchButton = ((Button)localView.findViewById(2131492974));
    localView.setTag(localHolder);
    return localView;
  }

  public static class Holder
  {
    private TextView address;
    Button gridSwitchButton;
    Button listSwitchButton;
    private TextView location;
    private IgImageView map;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.LocationRowAdapter
 * JD-Core Version:    0.6.0
 */