package com.instagram.android.media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.instagram.android.location.Venue;
import java.io.File;

public class PendingMedia
{
  private static final int STATUS_PARTIAL = 0;
  public static final int STATUS_READY = 1;
  private static final String TAG = "PendingMedia";
  private String mCaption;
  private boolean mFacebookEnabled;
  private int mFilterType;
  private boolean mFoursquareEnabled;
  private final String mImageFilePath;
  private boolean mInProgress;
  private String mKey;
  private double mLatitude;
  private double mLongitude;
  private String mMediaId;
  private boolean mNeedsConfigure = true;
  private boolean mNeedsUpload = true;
  private Integer mSourceType;
  private int mStatus = 0;
  private final Bitmap mThumbnail;
  private final int mThumbnailSize;
  private boolean mTumblrEnabled;
  private boolean mTwitterEnabled;
  private Venue mVenue;

  private PendingMedia(String paramString, int paramInt)
  {
    this.mThumbnailSize = paramInt;
    this.mThumbnail = createThumbnail(paramString, paramInt);
    this.mImageFilePath = paramString;
    this.mKey = String.valueOf(System.currentTimeMillis());
  }

  public static PendingMedia create(String paramString, int paramInt)
  {
    if ((paramString != null) && (new File(paramString).exists()));
    for (PendingMedia localPendingMedia = new PendingMedia(paramString, paramInt); ; localPendingMedia = null)
      return localPendingMedia;
  }

  private Bitmap createThumbnail(String paramString, int paramInt)
  {
    Bitmap localBitmap1 = BitmapFactory.decodeFile(paramString);
    if (localBitmap1 != null);
    for (Bitmap localBitmap2 = Bitmap.createScaledBitmap(localBitmap1, paramInt, paramInt, true); ; localBitmap2 = null)
      return localBitmap2;
  }

  public void clearGeotag()
  {
    this.mLatitude = 0.0D;
    this.mLongitude = 0.0D;
  }

  public String getCaption()
  {
    return this.mCaption;
  }

  public String getDeviceTimestamp()
  {
    return this.mKey;
  }

  public Integer getFilterType()
  {
    return Integer.valueOf(this.mFilterType);
  }

  public String getImageFilePath()
  {
    return this.mImageFilePath;
  }

  public boolean getInProgress()
  {
    return this.mInProgress;
  }

  public String getKey()
  {
    return this.mKey;
  }

  public Double getLatitude()
  {
    return Double.valueOf(this.mLatitude);
  }

  public Double getLongitude()
  {
    return Double.valueOf(this.mLongitude);
  }

  public String getMediaId()
  {
    return this.mMediaId;
  }

  public boolean getNeedsConfigure()
  {
    return this.mNeedsConfigure;
  }

  public boolean getNeedsUpload()
  {
    return this.mNeedsUpload;
  }

  public Integer getSourceType()
  {
    return this.mSourceType;
  }

  public int getStatus()
  {
    return this.mStatus;
  }

  public Bitmap getThumbnail()
  {
    return this.mThumbnail;
  }

  public int getThumbnailSize()
  {
    return this.mThumbnailSize;
  }

  public Venue getVenue()
  {
    return this.mVenue;
  }

  public boolean isFacebookEnabled()
  {
    return this.mFacebookEnabled;
  }

  public boolean isFoursquareEnabled()
  {
    return this.mFoursquareEnabled;
  }

  public boolean isGeotagEnabled()
  {
    if ((this.mLatitude != 0.0D) && (this.mLongitude != 0.0D));
    for (int i = 1; ; i = 0)
      return i;
  }

  public boolean isTumblrEnabled()
  {
    return this.mTumblrEnabled;
  }

  public boolean isTwitterEnabled()
  {
    return this.mTwitterEnabled;
  }

  public void setCaption(String paramString)
  {
    this.mCaption = paramString;
  }

  public void setFilterType(int paramInt)
  {
    this.mFilterType = paramInt;
  }

  public void setInProgress(boolean paramBoolean)
  {
    this.mInProgress = paramBoolean;
  }

  public void setIsFacebookEnabled(boolean paramBoolean)
  {
    this.mFacebookEnabled = paramBoolean;
  }

  public void setIsFoursquareEnabled(boolean paramBoolean)
  {
    this.mFoursquareEnabled = paramBoolean;
  }

  public void setIsTumblrEnabled(boolean paramBoolean)
  {
    this.mTumblrEnabled = paramBoolean;
  }

  public void setIsTwitterEnabled(boolean paramBoolean)
  {
    this.mTwitterEnabled = paramBoolean;
  }

  public void setKey(String paramString)
  {
    this.mKey = paramString;
  }

  public void setLatitude(double paramDouble)
  {
    this.mLatitude = paramDouble;
  }

  public void setLongitude(double paramDouble)
  {
    this.mLongitude = paramDouble;
  }

  public void setMediaId(String paramString)
  {
    this.mMediaId = paramString;
  }

  public void setNeedsConfigure(boolean paramBoolean)
  {
    this.mNeedsConfigure = paramBoolean;
  }

  public void setNeedsUpload(boolean paramBoolean)
  {
    this.mNeedsUpload = paramBoolean;
  }

  public void setSourceType(int paramInt)
  {
    this.mSourceType = Integer.valueOf(paramInt);
  }

  public void setStatus(int paramInt)
  {
    this.mStatus = paramInt;
  }

  public void setVenue(Venue paramVenue)
  {
    this.mVenue = paramVenue;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.media.PendingMedia
 * JD-Core Version:    0.6.0
 */