package com.instagram.android.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.view.View;
import android.view.ViewGroup;
import com.instagram.android.adapter.row.SuggestedUserRowAdapter;
import com.instagram.android.adapter.row.SuggestedUserRowAdapter.Holder;
import com.instagram.android.model.SuggestedUser;
import java.util.ArrayList;
import java.util.List;

public class SuggestedUserAdapter extends EnhancedArrayAdapter
{
  private boolean mCanNavigateToUserProfiles;
  private FragmentManager mFragmentManager;
  private LoaderManager mLoaderManager;
  private List<SuggestedUser> mSuggestedUsers;

  public SuggestedUserAdapter(Context paramContext, LoaderManager paramLoaderManager, FragmentManager paramFragmentManager, boolean paramBoolean)
  {
    super(paramContext);
    this.mLoaderManager = paramLoaderManager;
    this.mCanNavigateToUserProfiles = paramBoolean;
    this.mFragmentManager = paramFragmentManager;
  }

  protected void bindView(View paramView, Context paramContext, int paramInt)
  {
    SuggestedUserRowAdapter.bindView((SuggestedUserRowAdapter.Holder)paramView.getTag(), (SuggestedUser)getItem(paramInt), getContext(), this, this.mFragmentManager, true, this.mCanNavigateToUserProfiles, this.mLoaderManager);
  }

  public List<SuggestedUser> getSuggestedUsers()
  {
    return this.mSuggestedUsers;
  }

  public boolean isEnabled(int paramInt)
  {
    return false;
  }

  protected View newView(Context paramContext, int paramInt, ViewGroup paramViewGroup)
  {
    return SuggestedUserRowAdapter.newView(paramContext);
  }

  public void setSuggestedUsers(ArrayList<SuggestedUser> paramArrayList)
  {
    this.mSuggestedUsers = paramArrayList;
    this.mObjects.clear();
    this.mObjects.addAll(paramArrayList);
    notifyDataSetChanged();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.SuggestedUserAdapter
 * JD-Core Version:    0.6.0
 */