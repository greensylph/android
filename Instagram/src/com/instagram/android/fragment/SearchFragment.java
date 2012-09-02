package com.instagram.android.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.instagram.android.widget.SearchEditText;
import com.instagram.android.widget.SearchEditText.SearchEditTextListener;

public abstract class SearchFragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  public static final String BUNDLE_KEY_SAVED_SEARCH_STRING = "saved_search_string";
  private static final String LOG_TAG = "SearchFragment";
  protected static final int MODE_TAGS = 0;
  protected static final int MODE_USERS = 1;
  public static final String SEARCH_TAGS_FRAGMENT = "SEARCH_TAGS_FRAGMENT";
  public static final String SEARCH_USERS_FRAGMENT = "SEARCH_USERS_FRAGMENT";
  protected static int sLastSeenView = 1;
  private Activity mActivity;
  private FragmentManager mFragmentManager;
  protected boolean mIsLoading = false;
  private String mLastPerformedQuery;
  private LoaderManager mLoaderManager;
  private String mSavedSearchString;
  private SearchEditText mSearchEditText = null;
  private final SearchEditText.SearchEditTextListener mSearchEditTextListener = new SearchFragment.1(this);
  private View mView;

  private View configureHeader(View paramView)
  {
    this.mSearchEditText = ((SearchEditText)paramView.findViewById(2131493076));
    this.mSearchEditText.setOnFilterTextListener(this.mSearchEditTextListener);
    this.mSearchEditText.setHint(getHintResource());
    if (this.mSavedSearchString != null)
    {
      this.mSearchEditText.setText(this.mSavedSearchString);
      this.mSavedSearchString = null;
    }
    initSwitcher(paramView, getMode());
    return paramView;
  }

  private void initSwitcher(View paramView, int paramInt)
  {
    int i = 1;
    Button localButton1 = (Button)paramView.findViewById(2131492987);
    Button localButton2;
    if (paramInt == 0)
    {
      int j = i;
      localButton1.setSelected(j);
      localButton1.setOnClickListener(new SearchFragment.2(this, paramInt));
      localButton2 = (Button)paramView.findViewById(2131492986);
      if (paramInt != i)
        break label84;
    }
    while (true)
    {
      localButton2.setSelected(i);
      localButton2.setOnClickListener(new SearchFragment.3(this, paramInt));
      return;
      int k = 0;
      break;
      label84: i = 0;
    }
  }

  private View initializeView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    this.mView = paramLayoutInflater.inflate(2130903078, paramViewGroup, false);
    return this.mView;
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new SearchFragment.4(this);
  }

  public Activity getActivity()
  {
    return this.mActivity;
  }

  protected abstract BaseAdapter getAdapter();

  protected LoaderManager getCompositeLoaderManager()
  {
    return this.mLoaderManager;
  }

  public FragmentManager getFragmentManager()
  {
    return this.mFragmentManager;
  }

  protected abstract int getHintResource();

  protected abstract AdapterView.OnItemClickListener getItemClickListener();

  protected ListView getListView()
  {
    return (ListView)this.mView.findViewById(16908298);
  }

  protected abstract int getMode();

  protected View getView()
  {
    return this.mView;
  }

  public void hideSoftKeyboard()
  {
    this.mSearchEditText.hideSoftKeyboard();
  }

  protected void maybeShowKeyboard()
  {
    if (getAdapter().isEmpty())
    {
      this.mSearchEditText.requestFocus();
      this.mSearchEditText.showSoftKeyboard();
    }
    while (true)
    {
      return;
      this.mSearchEditText.hideSoftKeyboard();
    }
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    getListView().setOnItemClickListener(getItemClickListener());
  }

  public void onAttach(Activity paramActivity)
  {
    this.mActivity = paramActivity;
  }

  public void onCreate(Bundle paramBundle)
  {
    if (paramBundle != null)
      this.mSavedSearchString = paramBundle.getString(getMode() + "saved_search_string");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mView = initializeView(paramLayoutInflater, paramViewGroup);
    ((ListView)this.mView.findViewById(16908298)).addHeaderView(configureHeader(paramLayoutInflater.inflate(2130903117, null)), null, false);
    return this.mView;
  }

  public void onDetach()
  {
    this.mActivity = null;
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    if (this.mSearchEditText != null)
      paramBundle.putString(getMode() + "saved_search_string", this.mSearchEditText.getSearchString());
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    getListView().setAdapter(getAdapter());
    View localView = this.mView;
    if (sLastSeenView == getMode());
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  protected abstract void performQuery(String paramString);

  public void setFragmentManager(FragmentManager paramFragmentManager)
  {
    this.mFragmentManager = paramFragmentManager;
  }

  public void setLoaderManager(LoaderManager paramLoaderManager)
  {
    this.mLoaderManager = paramLoaderManager;
  }

  protected void setSelection(int paramInt)
  {
    getListView().setSelection(paramInt);
  }

  public void show()
  {
    sLastSeenView = getMode();
    CompositeSearchFragment.selectedFragment = this;
    this.mView.setVisibility(0);
    maybeShowKeyboard();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SearchFragment
 * JD-Core Version:    0.6.0
 */