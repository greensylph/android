package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;

public class FlagHelperBuilder
{
  private Context mContext;
  private FlagHelper.FlagType mFlagType;
  private FragmentManager mFragmentManager;
  private String mItemId;
  private LoaderManager mLoaderManager;

  public FlagHelper createFlagHelper()
  {
    return new FlagHelper(this.mContext, this.mFlagType, this.mItemId, this.mLoaderManager, this.mFragmentManager);
  }

  public FlagHelperBuilder setContext(Context paramContext)
  {
    this.mContext = paramContext;
    return this;
  }

  public FlagHelperBuilder setFlagType(FlagHelper.FlagType paramFlagType)
  {
    this.mFlagType = paramFlagType;
    return this;
  }

  public FlagHelperBuilder setFragmentManager(FragmentManager paramFragmentManager)
  {
    this.mFragmentManager = paramFragmentManager;
    return this;
  }

  public FlagHelperBuilder setItemId(String paramString)
  {
    this.mItemId = paramString;
    return this;
  }

  public FlagHelperBuilder setLoaderManager(LoaderManager paramLoaderManager)
  {
    this.mLoaderManager = paramLoaderManager;
    return this;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.FlagHelperBuilder
 * JD-Core Version:    0.6.0
 */