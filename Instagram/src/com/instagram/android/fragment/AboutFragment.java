package com.instagram.android.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.instagram.android.adapter.SimplePreferenceAdapter;
import com.instagram.android.model.GroupingHeader;
import com.instagram.android.model.SimpleMenuItem;
import com.instagram.api.request.UpdateAvatarHelper;
import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends ListFragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  private static final String LOG_TAG = "AboutFragment";
  private SimplePreferenceAdapter mAdapter;
  private UpdateAvatarHelper mUpdateAvatarHelper;

  private void createFooter(LayoutInflater paramLayoutInflater, View paramView)
  {
    ListView localListView = (ListView)paramView.findViewById(16908298);
    try
    {
      str = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
      View localView = paramLayoutInflater.inflate(2130903123, null);
      TextView localTextView = (TextView)localView.findViewById(2131493098);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = str;
      localTextView.setText(String.format("Instagram: v%s", arrayOfObject));
      localListView.addFooterView(localView);
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        String str = "unknown";
    }
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new AboutFragment.1(this);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    getListView().setOnItemClickListener(new AboutFragment.2(this));
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    this.mUpdateAvatarHelper.onActivityResult(paramInt1, paramInt2, paramIntent);
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new SimpleMenuItem(2131231031));
    localArrayList.add(new GroupingHeader(2131231031));
    localArrayList.add(new SimpleMenuItem(2131231032));
    localArrayList.add(new SimpleMenuItem(2131231033));
    localArrayList.add(new GroupingHeader(2131231034));
    localArrayList.add(new SimpleMenuItem(2131231034));
    localArrayList.add(new SimpleMenuItem(2131231035));
    localArrayList.add(new SimpleMenuItem(2131231036));
    this.mAdapter = new SimplePreferenceAdapter(getActivity());
    this.mAdapter.addItems(localArrayList);
    setListAdapter(this.mAdapter);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903087, paramViewGroup, false);
    localView.setBackgroundColor(getActivity().getResources().getColor(2131165210));
    createFooter(paramLayoutInflater, localView);
    return localView;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.AboutFragment
 * JD-Core Version:    0.6.0
 */