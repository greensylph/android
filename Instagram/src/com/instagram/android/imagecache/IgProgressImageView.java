package com.instagram.android.imagecache;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;
import java.io.InputStream;

public class IgProgressImageView extends ViewAnimator
{
  public static final int INDEX_IMAGE = 0;
  public static final int INDEX_PROGRESS_BAR = 1;
  public static final int INDEX_TEXT_VIEW = 2;
  public static final int PROGRESS_BAR_HEIGHT_DIP = 10;
  private IgImageView mIgImageView;
  private ProgressBar mProgressBar;
  private TextView mTextView;

  public IgProgressImageView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public IgProgressImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  private IgImageView getIgImageView()
  {
    if (this.mIgImageView == null)
    {
      this.mIgImageView = new IgImageView(getContext());
      this.mIgImageView.setScaleType(ImageView.ScaleType.FIT_XY);
      this.mIgImageView.setProgressListener(new IgProgressImageView.2(this));
      this.mIgImageView.setReportProgress(true);
      this.mIgImageView.setOnLoadListener(new IgProgressImageView.3(this));
    }
    return this.mIgImageView;
  }

  private ProgressBar getProgressBar()
  {
    if (this.mProgressBar == null)
    {
      this.mProgressBar = new ProgressBar(getContext(), null, 16842872);
      this.mProgressBar.setIndeterminate(false);
      this.mProgressBar.setMax(100);
    }
    return this.mProgressBar;
  }

  private TextView getTextView()
  {
    if (this.mTextView == null)
    {
      this.mTextView = new TextView(getContext());
      this.mTextView.setText(R.string.tap_to_reload);
      this.mTextView.setGravity(17);
      this.mTextView.setOnClickListener(new IgProgressImageView.1(this));
    }
    return this.mTextView;
  }

  protected void init()
  {
    removeAllViews();
    addView(getIgImageView(), 0, new FrameLayout.LayoutParams(-1, -1, 17));
    addView(getProgressBar(), 1, new FrameLayout.LayoutParams(-1, -2, 17));
    addView(getTextView(), 2, new FrameLayout.LayoutParams(-1, -2, 17));
  }

  public void loadBitmap(InputStream paramInputStream)
  {
    getIgImageView().loadBitmap(paramInputStream);
  }

  public void removeLoadCallback()
  {
    getIgImageView().removeLoadCallback();
  }

  public void setUrl(String paramString)
  {
    getIgImageView().setUrl(paramString);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.IgProgressImageView
 * JD-Core Version:    0.6.0
 */