package com.instagram.android.adapter.row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.instagram.android.media.PendingMedia;
import com.instagram.android.service.PendingMediaService;

public class PendingMediaRowAdapter
{
  public static void bindView(Context paramContext, BaseAdapter paramBaseAdapter, Holder paramHolder, PendingMedia paramPendingMedia)
  {
    paramHolder.imageView.setImageBitmap(paramPendingMedia.getThumbnail());
    if ((paramPendingMedia.getNeedsConfigure()) || (paramPendingMedia.getNeedsUpload()))
      if (paramPendingMedia.getInProgress())
      {
        paramHolder.retryButton.setVisibility(8);
        paramHolder.cancelButton.setVisibility(8);
        paramHolder.progressBar.setVisibility(0);
        paramHolder.statusText.setVisibility(8);
      }
    while (true)
    {
      paramHolder.retryButton.setOnClickListener(new View.OnClickListener(paramContext, paramPendingMedia)
      {
        public void onClick(View paramView)
        {
          PendingMediaService.retry(this.val$context, this.val$media);
        }
      });
      paramHolder.cancelButton.setOnClickListener(new View.OnClickListener(paramContext, paramPendingMedia)
      {
        public void onClick(View paramView)
        {
          PendingMediaService.cancel(this.val$context, this.val$media);
        }
      });
      return;
      paramHolder.retryButton.setVisibility(0);
      paramHolder.cancelButton.setVisibility(0);
      paramHolder.progressBar.setVisibility(4);
      paramHolder.statusText.setText(2131231026);
      paramHolder.statusText.setVisibility(0);
      continue;
      paramHolder.retryButton.setVisibility(8);
      paramHolder.cancelButton.setVisibility(8);
      paramHolder.progressBar.setVisibility(8);
      paramHolder.statusText.setText(2131230851);
      paramHolder.statusText.setVisibility(0);
    }
  }

  public static View newView(Context paramContext)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903113, null);
    Holder localHolder = new Holder();
    localHolder.imageView = ((ImageView)localView.findViewById(2131493057));
    localHolder.retryButton = ((Button)localView.findViewById(2131493060));
    localHolder.cancelButton = ((Button)localView.findViewById(2131493061));
    localHolder.progressBar = ((ProgressBar)localView.findViewById(2131493059));
    localHolder.statusText = ((TextView)localView.findViewById(2131493058));
    localView.setTag(localHolder);
    return localView;
  }

  public static class Holder
  {
    Button cancelButton;
    ImageView imageView;
    ProgressBar progressBar;
    Button retryButton;
    TextView statusText;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.PendingMediaRowAdapter
 * JD-Core Version:    0.6.0
 */