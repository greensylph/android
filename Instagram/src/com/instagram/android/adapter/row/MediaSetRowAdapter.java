package com.instagram.android.adapter.row;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.instagram.android.adapter.FeedAdapter.GridViewPadding;
import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.imagecache.IgImageView;
import com.instagram.android.model.Media;
import com.instagram.android.service.ClickManager;
import com.instagram.util.FragmentUtil;
import java.util.ArrayList;

public class MediaSetRowAdapter
{
  public static void bindView(Holder paramHolder, ArrayList<Media> paramArrayList, Context paramContext)
  {
    int i = paramArrayList.size();
    int j = 0;
    if (j < paramHolder.imageViews.length)
    {
      IgImageView localIgImageView = paramHolder.imageViews[j];
      ViewGroup localViewGroup = paramHolder.imageBorderViews[j];
      Media localMedia;
      if (j < i)
      {
        localMedia = (Media)paramArrayList.get(j);
        label53: if (localMedia != null)
          break label96;
        localIgImageView.setImageDrawable(paramContext.getResources().getDrawable(2130837813));
        localIgImageView.setOnClickListener(null);
        localViewGroup.setVisibility(4);
      }
      while (true)
      {
        j++;
        break;
        localMedia = null;
        break label53;
        label96: localIgImageView.setUrl(localMedia.getThumbnailUrl());
        localViewGroup.setVisibility(0);
        localIgImageView.setOnClickListener(new View.OnClickListener(localMedia)
        {
          public void onClick(View paramView)
          {
            FragmentManager localFragmentManager = ClickManager.getInstance().getCurrentFragmentManager();
            if (localFragmentManager != null)
            {
              Bundle localBundle = new Bundle();
              localBundle.putString("com.instagram.android.fragment.ARGUMENTS_KEY_EXTRA_MEDIA_ID", this.val$media.getId());
              FragmentUtil.navigateTo(localFragmentManager, new FeedFragment(), localBundle);
            }
          }
        });
      }
    }
  }

  public static View newView(Context paramContext, FeedAdapter.GridViewPadding paramGridViewPadding)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(paramContext);
    if (paramGridViewPadding == FeedAdapter.GridViewPadding.TIGHT);
    for (int i = 2130903105; ; i = 2130903104)
    {
      View localView = localLayoutInflater.inflate(i, null);
      Holder localHolder = new Holder();
      localHolder.imageViews[0] = ((IgImageView)localView.findViewById(2131493039));
      localHolder.imageViews[1] = ((IgImageView)localView.findViewById(2131493041));
      localHolder.imageViews[2] = ((IgImageView)localView.findViewById(2131493043));
      localHolder.imageViews[3] = ((IgImageView)localView.findViewById(2131493045));
      localHolder.imageBorderViews[0] = ((ViewGroup)localView.findViewById(2131493040));
      localHolder.imageBorderViews[1] = ((ViewGroup)localView.findViewById(2131493042));
      localHolder.imageBorderViews[2] = ((ViewGroup)localView.findViewById(2131493044));
      localHolder.imageBorderViews[3] = ((ViewGroup)localView.findViewById(2131493046));
      localView.setTag(localHolder);
      return localView;
    }
  }

  public static class Holder
  {
    ViewGroup[] imageBorderViews = new ViewGroup[4];
    IgImageView[] imageViews = new IgImageView[4];
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.MediaSetRowAdapter
 * JD-Core Version:    0.6.0
 */