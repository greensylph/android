package com.instagram.android.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import com.instagram.android.imagecache.IgAsyncTask;
import com.instagram.android.imagecache.IgImageView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LandingPageFeedAdapter extends BaseAdapter
  implements ListAdapter
{
  private static final int NUM_IMAGES_PER_ROW = 4;
  private String mPathPrefix = null;
  private List<List<String>> mRows = new ArrayList();

  private int calculateRowCount(int paramInt)
  {
    return (int)Math.ceil(paramInt / 4.0D);
  }

  public int getCount()
  {
    return this.mRows.size();
  }

  public List<String> getItem(int paramInt)
  {
    return (List)this.mRows.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return 0L;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
      paramView = ImageRowAdapter.createView(paramViewGroup);
    ImageRowAdapter.bindView(paramView, (ArrayList)getItem(paramInt));
    return paramView;
  }

  public void setPathPrefix(String paramString)
  {
    this.mPathPrefix = paramString;
  }

  public void setRows(String[] paramArrayOfString)
  {
    this.mRows.clear();
    if (paramArrayOfString != null)
    {
      Object localObject = null;
      ArrayList localArrayList = new ArrayList(calculateRowCount(paramArrayOfString.length));
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        if ((localObject == null) || (i % 4 == 0))
        {
          if (localObject != null)
            localArrayList.add(localObject);
          localObject = new ArrayList(4);
        }
        ((List)localObject).add(this.mPathPrefix + "/" + paramArrayOfString[i]);
      }
      localArrayList.add(localObject);
      this.mRows.addAll(localArrayList);
    }
    notifyDataSetChanged();
  }

  private static class AsyncImageBitmapTask extends IgAsyncTask<String, Void, Bitmap>
  {
    private final Context mContext;
    private String mFilePath;
    private final ImageView mImageView;

    public AsyncImageBitmapTask(ImageView paramImageView)
    {
      this.mImageView = paramImageView;
      this.mContext = paramImageView.getContext().getApplicationContext();
    }

    public static void load(ImageView paramImageView, String paramString)
    {
      paramImageView.setTag(paramString);
      AsyncImageBitmapTask localAsyncImageBitmapTask = new AsyncImageBitmapTask(paramImageView);
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramString;
      localAsyncImageBitmapTask.execute(arrayOfString);
    }

    protected Bitmap doInBackground(String[] paramArrayOfString)
    {
      Object localObject = null;
      this.mFilePath = paramArrayOfString[0];
      try
      {
        Bitmap localBitmap = BitmapFactory.decodeStream(this.mContext.getAssets().open(this.mFilePath));
        localObject = localBitmap;
        label31: return localObject;
      }
      catch (IOException localIOException)
      {
        break label31;
      }
    }

    protected void onPostExecute(Bitmap paramBitmap)
    {
      Object localObject = this.mImageView.getTag();
      if ((localObject != null) && ((localObject instanceof String)) && (localObject.equals(this.mFilePath)))
        this.mImageView.setImageBitmap(paramBitmap);
    }
  }

  private static class ImageRowAdapter
  {
    public static void bindView(View paramView, ArrayList<String> paramArrayList)
    {
      Context localContext = paramView.getContext();
      Holder localHolder = (Holder)paramView.getTag();
      int i = paramArrayList.size();
      int j = 0;
      if (j < localHolder.imageViews.length)
      {
        IgImageView localIgImageView = localHolder.imageViews[j];
        if (j < i);
        for (String str = (String)paramArrayList.get(j); ; str = null)
        {
          localIgImageView.setImageDrawable(localContext.getResources().getDrawable(2130837813));
          if (str != null)
            LandingPageFeedAdapter.AsyncImageBitmapTask.load(localIgImageView, str);
          j++;
          break;
        }
      }
    }

    public static View createView(ViewGroup paramViewGroup)
    {
      View localView = LayoutInflater.from(paramViewGroup.getContext()).inflate(2130903104, null);
      Holder localHolder = new Holder();
      localHolder.imageViews[0] = ((IgImageView)localView.findViewById(2131493039));
      localHolder.imageViews[1] = ((IgImageView)localView.findViewById(2131493041));
      localHolder.imageViews[2] = ((IgImageView)localView.findViewById(2131493043));
      localHolder.imageViews[3] = ((IgImageView)localView.findViewById(2131493045));
      localView.setTag(localHolder);
      return localView;
    }

    public static class Holder
    {
      IgImageView[] imageViews = new IgImageView[4];
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.LandingPageFeedAdapter
 * JD-Core Version:    0.6.0
 */