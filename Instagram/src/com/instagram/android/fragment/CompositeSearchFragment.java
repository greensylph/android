package com.instagram.android.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

public class CompositeSearchFragment extends Fragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  public static SearchTagsFragment searchTagsFragment;
  public static SearchUsersFragment searchUsersFragment;
  public static SearchFragment selectedFragment;
  private int mOldOrientation;

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return selectedFragment.getActionBarConfigurer();
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    searchTagsFragment.onActivityCreated(paramBundle);
    searchUsersFragment.onActivityCreated(paramBundle);
  }

  public void onAttach(Activity paramActivity)
  {
    if (searchTagsFragment == null)
      searchTagsFragment = new SearchTagsFragment();
    if (searchUsersFragment == null)
      searchUsersFragment = new SearchUsersFragment();
    if (selectedFragment == null)
      selectedFragment = searchUsersFragment;
    searchTagsFragment.onAttach(paramActivity);
    searchUsersFragment.onAttach(paramActivity);
    super.onAttach(paramActivity);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    searchTagsFragment.setLoaderManager(getLoaderManager());
    searchUsersFragment.setLoaderManager(getLoaderManager());
    searchTagsFragment.setFragmentManager(getFragmentManager());
    searchUsersFragment.setFragmentManager(getFragmentManager());
    searchTagsFragment.onCreate(paramBundle);
    searchUsersFragment.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    FrameLayout localFrameLayout = new FrameLayout(getActivity());
    View localView1 = searchTagsFragment.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    View localView2 = searchUsersFragment.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    localFrameLayout.addView(localView1);
    localFrameLayout.addView(localView2);
    return localFrameLayout;
  }

  public void onDestroy()
  {
    searchUsersFragment.onDestroy();
    super.onDestroy();
  }

  public void onDetach()
  {
    searchTagsFragment.onDetach();
    searchUsersFragment.onDetach();
    searchUsersFragment = null;
    searchTagsFragment = null;
    selectedFragment = null;
    super.onDetach();
  }

  public void onPause()
  {
    super.onPause();
    getActivity().setRequestedOrientation(this.mOldOrientation);
    getActivity().findViewById(2131492985).setVisibility(8);
    selectedFragment.hideSoftKeyboard();
  }

  public void onResume()
  {
    super.onResume();
    selectedFragment.getView().setVisibility(0);
    this.mOldOrientation = getActivity().getRequestedOrientation();
    getActivity().setRequestedOrientation(-1);
    getActivity().getWindow().setSoftInputMode(48);
    selectedFragment.maybeShowKeyboard();
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    searchTagsFragment.onSaveInstanceState(paramBundle);
    searchUsersFragment.onSaveInstanceState(paramBundle);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    searchTagsFragment.onViewCreated(paramView, paramBundle);
    searchUsersFragment.onViewCreated(paramView, paramBundle);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CompositeSearchFragment
 * JD-Core Version:    0.6.0
 */