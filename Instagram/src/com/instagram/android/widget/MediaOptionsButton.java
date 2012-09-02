package com.instagram.android.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.instagram.android.fragment.LocationFeedFragment;
import com.instagram.android.fragment.ProgressDialogFragment;
import com.instagram.android.location.Venue;
import com.instagram.android.model.Media;
import com.instagram.android.model.Media.FeedItemLocationType;
import com.instagram.android.model.User;
import com.instagram.android.service.AuthHelper;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiResponse;
import com.instagram.api.request.FlagHelper;
import com.instagram.api.request.FlagHelper.FlagType;
import com.instagram.api.request.FlagHelperBuilder;
import com.instagram.api.request.PermalinkRequest;
import com.instagram.util.FragmentUtil;
import java.util.ArrayList;

public class MediaOptionsButton extends FrameLayout
{
  private Context mActivityContext;
  private FlagHelper mFlagHelper;
  private FragmentManager mFragmentManager;
  private Handler mHandler = new Handler();
  private boolean mIsMediaAuthor;
  private LoaderManager mLoaderManager;
  private Media mMedia;
  private CharSequence[] mMenuOptions = null;
  private View.OnClickListener mOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramView)
    {
      MediaOptionsButton.this.showMenu();
    }
  };
  private PermalinkProgressnDialogFragment mProgressDialog;

  public MediaOptionsButton(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public MediaOptionsButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public MediaOptionsButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  private static boolean checkIsMediaAuthor(Media paramMedia)
  {
    User localUser = AuthHelper.getInstance().getCurrentUser();
    if ((localUser != null) && (paramMedia.getUser().getId().equals(localUser.getId())));
    for (int i = 1; ; i = 0)
      return i;
  }

  private boolean getIsMediaAuthor()
  {
    return this.mIsMediaAuthor;
  }

  private boolean getMediaHasValidLocation()
  {
    if ((this.mMedia.getVenue() != null) && (this.mMedia.getFeedItemLocationType() == Media.FeedItemLocationType.Foursquare));
    for (int i = 1; ; i = 0)
      return i;
  }

  private CharSequence[] getMenuOptions()
  {
    Resources localResources;
    ArrayList localArrayList;
    if (this.mMenuOptions == null)
    {
      localResources = getContext().getResources();
      localArrayList = new ArrayList();
      if (!getIsMediaAuthor())
        break label124;
      localArrayList.add(localResources.getString(2131230942));
      localArrayList.add(localResources.getString(2131230896));
      if (getMediaHasValidLocation())
      {
        String str2 = localResources.getString(2131230858);
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.mMedia.getVenue().name;
        localArrayList.add(String.format(str2, arrayOfObject2));
      }
    }
    while (true)
    {
      this.mMenuOptions = new CharSequence[localArrayList.size()];
      localArrayList.toArray(this.mMenuOptions);
      return this.mMenuOptions;
      label124: if (getMediaHasValidLocation())
      {
        String str1 = localResources.getString(2131230858);
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = this.mMedia.getVenue().name;
        localArrayList.add(String.format(str1, arrayOfObject1));
      }
      localArrayList.add(localResources.getString(2131230940));
    }
  }

  private void init()
  {
    setOnClickListener(this.mOnClickListener);
  }

  private void openSendAction(CharSequence paramCharSequence)
  {
  }

  private void setIsMediaAuthor(boolean paramBoolean)
  {
    this.mIsMediaAuthor = paramBoolean;
  }

  private void showMenu()
  {
    new IgDialogBuilder(this.mActivityContext).setMessage(2131230939).setItems(getMenuOptions(), new MenuClickListener(null)).create().show();
  }

  public void setActivityContext(Context paramContext)
  {
    this.mActivityContext = paramContext;
  }

  public void setFragmentManager(FragmentManager paramFragmentManager)
  {
    this.mFragmentManager = paramFragmentManager;
  }

  public void setLoaderManager(LoaderManager paramLoaderManager)
  {
    this.mLoaderManager = paramLoaderManager;
  }

  public void setMedia(Media paramMedia)
  {
    this.mMenuOptions = null;
    this.mMedia = paramMedia;
    setIsMediaAuthor(checkIsMediaAuthor(this.mMedia));
  }

  private final class MenuClickListener
    implements DialogInterface.OnClickListener
  {
    private MenuClickListener()
    {
    }

    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      paramDialogInterface.dismiss();
      CharSequence localCharSequence = MediaOptionsButton.this.getMenuOptions()[paramInt];
      if (MediaOptionsButton.this.getContext().getString(2131230896).equals(localCharSequence))
        new IgDialogBuilder(MediaOptionsButton.this.mActivityContext).setTitle(2131231061).setMessage(2131231062).setPositiveButton(2131230896, new MediaOptionsButton.MenuClickListener.2(this)).setCancelable(true).setNegativeButton(2131231063, new MediaOptionsButton.MenuClickListener.1(this)).create().show();
      while (true)
      {
        return;
        if (MediaOptionsButton.this.getContext().getString(2131230942).equals(localCharSequence))
        {
          MediaOptionsButton.access$502(MediaOptionsButton.this, new MediaOptionsButton.PermalinkProgressnDialogFragment());
          MediaOptionsButton.this.mProgressDialog.setCancelable(true);
          MediaOptionsButton.this.mProgressDialog.show(MediaOptionsButton.this.mFragmentManager);
          new PermalinkRequest(MediaOptionsButton.this.mMedia, MediaOptionsButton.this.mActivityContext, MediaOptionsButton.this.mLoaderManager, new MediaOptionsButton.PermalinkRequestCallbacks(MediaOptionsButton.this, null)).perform();
          continue;
        }
        if (MediaOptionsButton.this.getContext().getString(2131230940).equals(localCharSequence))
        {
          new FlagHelperBuilder().setContext(MediaOptionsButton.this.mActivityContext).setFlagType(FlagHelper.FlagType.Media).setItemId(MediaOptionsButton.this.mMedia.getId()).setLoaderManager(MediaOptionsButton.this.mLoaderManager).setFragmentManager(MediaOptionsButton.this.mFragmentManager).createFlagHelper().showFlaggingOptions();
          continue;
        }
        if ((!MediaOptionsButton.this.getMediaHasValidLocation()) || (!localCharSequence.toString().contains(MediaOptionsButton.this.mMedia.getVenue().name)))
          break;
        Bundle localBundle = new Bundle();
        localBundle.putString("com.instagram.android.fragment.LocationFeedFragment.ARGUMENT_LOCATION_MEDIA_ID", MediaOptionsButton.this.mMedia.getId());
        FragmentUtil.navigateTo(MediaOptionsButton.this.mFragmentManager, new LocationFeedFragment(), localBundle);
      }
      throw new UnsupportedOperationException("Menu item click not handled");
    }
  }

  public static class PermalinkProgressnDialogFragment extends ProgressDialogFragment
  {
    private static String TAG = "PermalinkProgressnDialogFragment";
    private boolean mWasCanceled;

    protected String getProgressMessage()
    {
      return getString(2131230889);
    }

    public boolean getWasCanceled()
    {
      return this.mWasCanceled;
    }

    public void onCancel(DialogInterface paramDialogInterface)
    {
      super.onCancel(paramDialogInterface);
      this.mWasCanceled = true;
    }

    public void show(FragmentManager paramFragmentManager)
    {
      super.show(paramFragmentManager, TAG);
    }
  }

  private class PermalinkRequestCallbacks extends AbstractStreamingApiCallbacks<String>
  {
    private PermalinkRequestCallbacks()
    {
    }

    protected void onRequestFail(ApiResponse<String> paramApiResponse)
    {
      MediaOptionsButton.this.mHandler.post(new MediaOptionsButton.PermalinkRequestCallbacks.2(this));
    }

    protected void onSuccess(String paramString)
    {
      MediaOptionsButton.this.mHandler.post(new MediaOptionsButton.PermalinkRequestCallbacks.1(this, paramString));
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.MediaOptionsButton
 * JD-Core Version:    0.6.0
 */