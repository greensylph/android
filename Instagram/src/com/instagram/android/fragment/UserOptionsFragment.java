package com.instagram.android.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.instagram.android.adapter.SimplePreferenceAdapter;
import com.instagram.android.model.GroupingHeader;
import com.instagram.android.model.SimpleMenuItem;
import com.instagram.api.request.UpdateAvatarHelper;
import java.util.ArrayList;
import java.util.List;

public class UserOptionsFragment extends ListFragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  private static final String LOG_TAG = "UserOptionsFragment";
  private SimplePreferenceAdapter mAdapter;
  private UpdateAvatarHelper mUpdateAvatarHelper;

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new UserOptionsFragment.1(this);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    getListView().setOnItemClickListener(new UserOptionsFragment.2(this));
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    this.mUpdateAvatarHelper.onActivityResult(paramInt1, paramInt2, paramIntent);
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mUpdateAvatarHelper = new UpdateAvatarHelper(this, paramBundle);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new SimpleMenuItem(2131230963));
    localArrayList.add(new SimpleMenuItem(2131230967));
    localArrayList.add(new GroupingHeader(2131231030));
    localArrayList.add(new SimpleMenuItem(2131230968));
    localArrayList.add(new SimpleMenuItem(2131230969));
    localArrayList.add(new SimpleMenuItem(2131230970));
    localArrayList.add(new SimpleMenuItem(2131231017));
    localArrayList.add(new GroupingHeader(2131231030));
    if (Build.VERSION.SDK_INT >= 9)
      localArrayList.add(new SimpleMenuItem(2131231056));
    localArrayList.add(new SimpleMenuItem(2131230973));
    localArrayList.add(new SimpleMenuItem(2131230971));
    this.mAdapter = new SimplePreferenceAdapter(getActivity());
    this.mAdapter.addItems(localArrayList);
    setListAdapter(this.mAdapter);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903087, paramViewGroup, false);
    localView.setBackgroundColor(getActivity().getResources().getColor(2131165210));
    return localView;
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.UserOptionsFragment
 * JD-Core Version:    0.6.0
 */