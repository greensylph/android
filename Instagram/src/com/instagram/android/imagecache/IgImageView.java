package com.instagram.android.imagecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import java.io.InputStream;

public class IgImageView extends ImageView
{
  private IgBitmapCache.BitmapCallback callback = new IgBitmapCache.BitmapCallback()
  {
    public void bindToTask(IgBitmapCache.LoadBitmapTask paramLoadBitmapTask)
    {
      if (paramLoadBitmapTask.getUrl().equals(IgImageView.this.mUrl))
        IgImageView.access$302(IgImageView.this, paramLoadBitmapTask);
    }

    public void reportError()
    {
      IgImageView.access$302(IgImageView.this, null);
      IgImageView.access$202(IgImageView.this, true);
      if (IgImageView.this.onLoadListener != null)
        IgImageView.this.onLoadListener.onLoad(null);
    }

    public void reportProgress(String paramString, int paramInt)
    {
      if ((!IgImageView.this.mLoaded) && (IgImageView.this.mUrl.equals(paramString)) && (IgImageView.this.mProgressListener != null))
        IgImageView.this.mProgressListener.onProgress(paramInt);
    }

    public void setBitmap(String paramString, Bitmap paramBitmap)
    {
      if (IgImageView.this.mUrl.equals(paramString))
      {
        IgImageView.access$202(IgImageView.this, true);
        IgImageView.this.setImageBitmap(paramBitmap);
        IgImageView.access$302(IgImageView.this, null);
        if (IgImageView.this.onLoadListener != null)
          IgImageView.this.onLoadListener.onLoad(paramBitmap);
      }
    }
  };
  private IgBitmapCache.LoadBitmapTask mLoadTask;
  private boolean mLoaded = false;
  private Drawable mOriginalDrawable;
  private OnProgressListener mProgressListener = new OnProgressListener()
  {
    public void onProgress(int paramInt)
    {
      if (IgImageView.this.mOriginalDrawable != null)
        IgImageView.this.setImageDrawable(IgImageView.this.mOriginalDrawable);
    }
  };
  private boolean mReportProgress = false;
  private String mUrl;
  private OnLoadListener onLoadListener;

  public IgImageView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public IgImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public IgImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  private void fetchBitmap(String paramString)
  {
    this.mLoaded = false;
    this.mUrl = paramString;
    IgBitmapCache.getInstance(getContext()).loadBitmap(paramString, this.callback, this.mReportProgress, true);
  }

  private void init()
  {
    this.mOriginalDrawable = getDrawable();
  }

  public String getUrl()
  {
    return this.mUrl;
  }

  void loadBitmap(InputStream paramInputStream)
  {
    this.mLoaded = true;
    Bitmap localBitmap = BitmapFactory.decodeStream(paramInputStream);
    if (localBitmap != null)
      setImageBitmap(localBitmap);
  }

  protected void onDetachedFromWindow()
  {
    removeLoadCallback();
    super.onDetachedFromWindow();
  }

  public void removeLoadCallback()
  {
    if (this.mLoadTask != null)
    {
      this.mLoadTask.removeCallback(this.callback);
      this.mLoadTask = null;
    }
  }

  public void setOnLoadListener(OnLoadListener paramOnLoadListener)
  {
    this.onLoadListener = paramOnLoadListener;
  }

  public void setProgressListener(OnProgressListener paramOnProgressListener)
  {
    this.mProgressListener = paramOnProgressListener;
  }

  public void setReportProgress(boolean paramBoolean)
  {
    this.mReportProgress = paramBoolean;
  }

  public void setUrl(String paramString)
  {
    removeLoadCallback();
    fetchBitmap(paramString);
  }

  static abstract interface OnLoadListener
  {
    public abstract void onLoad(Bitmap paramBitmap);
  }

  static abstract interface OnProgressListener
  {
    public abstract void onProgress(int paramInt);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.IgImageView
 * JD-Core Version:    0.6.0
 */