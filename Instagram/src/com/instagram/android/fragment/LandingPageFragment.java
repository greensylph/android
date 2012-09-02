package com.instagram.android.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import com.instagram.android.InstagramApplication;
import com.instagram.android.adapter.LandingPageFeedAdapter;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.android.widget.LoadMoreButton.LoadMoreInterface;
import com.instagram.util.NoopUtil;
import java.io.IOException;

public class LandingPageFragment extends ListFragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory, LoadMoreButton.LoadMoreInterface
{
  private static final String STATIC_IMAGES_PATH = "static_images";
  private static final String TAG = "LandingPageFragment";
  protected LandingPageFeedAdapter mAdapter;
  private int mOldOrientation;

  private void loadImages()
  {
    LoadImagesTask localLoadImagesTask = new LoadImagesTask(getActivity(), getAdapter());
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "static_images";
    localLoadImagesTask.execute(arrayOfString);
  }

  private void startFragment(Fragment paramFragment)
  {
    FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
    localFragmentTransaction.replace(2131492901, paramFragment);
    localFragmentTransaction.setTransition(4097);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new ActionBarConfigurer()
    {
      public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
      {
        ImageView localImageView;
        if ((LandingPageFragment.this.getArguments() != null) && (LandingPageFragment.this.getArguments().getBoolean("branding")))
        {
          localImageView = new ImageView(LandingPageFragment.this.getActivity());
          localImageView.setImageDrawable(LandingPageFragment.this.getActivity().getResources().getDrawable(2130837506));
          FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2, 19);
          localLayoutParams.setMargins((int)LandingPageFragment.this.getResources().getDimension(2131427341), 0, 0, 0);
          localImageView.setLayoutParams(localLayoutParams);
        }
        while (true)
        {
          return localImageView;
          localImageView = null;
        }
      }

      public String getTitle()
      {
        return null;
      }

      public boolean isLoading()
      {
        return false;
      }

      public boolean showBackButton()
      {
        if (LandingPageFragment.this.getFragmentManager().getBackStackEntryCount() > 0);
        for (int i = 1; ; i = 0)
          return i;
      }

      public boolean showRefreshButton()
      {
        return true;
      }
    };
  }

  protected LandingPageFeedAdapter getAdapter()
  {
    if (this.mAdapter == null)
    {
      this.mAdapter = new LandingPageFeedAdapter();
      this.mAdapter.setPathPrefix("static_images");
    }
    return this.mAdapter;
  }

  public boolean hasMoreItems()
  {
    return false;
  }

  public boolean isLoadMoreVisible()
  {
    return false;
  }

  public boolean isLoading()
  {
    return false;
  }

  public void loadMore()
  {
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    InstagramApplication.detectWriteableFileSystem();
    FragmentActivity localFragmentActivity = getActivity();
    localFragmentActivity.findViewById(2131492971).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        LoginFragment localLoginFragment = new LoginFragment();
        LandingPageFragment.this.startFragment(localLoginFragment);
      }
    });
    localFragmentActivity.findViewById(2131492970).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        SignUpFragment localSignUpFragment = new SignUpFragment();
        LandingPageFragment.this.startFragment(localSignUpFragment);
      }
    });
    if (InstagramApplication.getFailedToLoadLibrary())
    {
      NoopUtil.report("failed_to_load_library");
      new IgDialogBuilder(getActivity()).setCancelable(false).setTitle(2131231068).setMessage(2131231067).setPositiveButton(2131231003, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          LandingPageFragment.this.getActivity().finish();
        }
      }).create().show();
    }
    if (InstagramApplication.getFailedToLoadFilesystem())
    {
      NoopUtil.report("failed_to_write_to_fs");
      new IgDialogBuilder(getActivity()).setMessage(2131231071).setPositiveButton(2131230955, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          LandingPageFragment.this.getActivity().finish();
        }
      }).create().show();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setListAdapter(getAdapter());
    loadImages();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903086, paramViewGroup, false);
    if ((InstagramApplication.getFailedToLoadLibrary()) || (InstagramApplication.getFailedToLoadFilesystem()))
      localView.findViewById(2131492980).setVisibility(8);
    return localView;
  }

  public void onStart()
  {
    this.mOldOrientation = getActivity().getRequestedOrientation();
    getActivity().setRequestedOrientation(1);
    super.onStart();
  }

  public void onStop()
  {
    getActivity().setRequestedOrientation(this.mOldOrientation);
    super.onStop();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    if (Build.VERSION.SDK_INT < 14)
      getListView().setScrollingCacheEnabled(false);
  }

  private static class LoadImagesTask extends AsyncTask<String, Void, String[]>
  {
    private LandingPageFeedAdapter mAdapter;
    private Context mContext;

    public LoadImagesTask(Context paramContext, LandingPageFeedAdapter paramLandingPageFeedAdapter)
    {
      this.mContext = paramContext.getApplicationContext();
      this.mAdapter = paramLandingPageFeedAdapter;
    }

    protected String[] doInBackground(String[] paramArrayOfString)
    {
      String str = paramArrayOfString[0];
      Object localObject = null;
      try
      {
        String[] arrayOfString = this.mContext.getAssets().list(str);
        localObject = arrayOfString;
        label22: return localObject;
      }
      catch (IOException localIOException)
      {
        break label22;
      }
    }

    protected void onPostExecute(String[] paramArrayOfString)
    {
      this.mAdapter.setRows(paramArrayOfString);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.LandingPageFragment
 * JD-Core Version:    0.6.0
 */