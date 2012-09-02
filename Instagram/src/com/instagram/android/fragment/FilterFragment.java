package com.instagram.android.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.instagram.android.Log;
import com.instagram.android.Preferences;
import com.instagram.android.activity.ActivityKeyEventDelegate;
import com.instagram.android.gl.FilterController;
import com.instagram.android.gl.FilterController.RenderCallbacks;
import com.instagram.android.gl.FilterGLSurfaceView;
import com.instagram.android.gl.NativeBridge;
import com.instagram.android.gl.NativeBridge.ImageProcessorDelegate;
import com.instagram.android.gl.NativeFilter;
import com.instagram.android.gl.NativeRenderer;
import com.instagram.android.service.AppContext;
import com.instagram.android.task.SaveImageTask;
import com.instagram.android.task.UploadImageTask;
import com.instagram.android.widget.ActionBarHighlightButton;
import com.instagram.android.widget.FilterPicker;
import com.instagram.android.widget.FilterPicker.OnFilterChangedListener;
import com.instagram.android.widget.IgProgressDialog;
import com.instagram.android.widget.TiltShiftButton;
import com.instagram.util.BitmapHelper;
import com.instagram.util.CameraUsageReportingUtil;
import com.instagram.util.FileUtil;
import com.instagram.util.GalleryUtil;
import com.instagram.util.ViewUtil;
import java.io.File;

public class FilterFragment extends Fragment
  implements ActivityKeyEventDelegate, NativeBridge.ImageProcessorDelegate
{
  public static final String ARGUMENTS_FILE_PATH = "filePath";
  public static final String ARGUMENTS_IS_SHARE_INTENT = "isShareIntent";
  public static final String ARGUMENTS_MEDIA_SOURCE = "mediaSource";
  public static final String ARGUMENTS_MIRROR_MEDIA = "mirrorMedia";
  private static final String BUNDLE_KEY_LUX_ENABLED = "luxEnabled";
  private static final String BUNDLE_KEY_TILT_SHIFT_ENABLED = "tiltEnabled";
  private static final String BUNDLE_KEY_TILT_SHIFT_MODE = "tiltEnabled";
  private static final int REMOVE_TILTSHIFT_OVERLAY = 3232;
  private static final int RENDER_FINISHED_ACTION_SAVE_IMAGE = 0;
  private static final int RENDER_FINISHED_ACTION_UPLOAD_BITMAP = 1;
  private static final int REQUEST_CROP_PHOTO = 1;
  private static final int REQUEST_METADATA = 2;
  private static final int REQUEST_PICK_FROM_GALLERY = 0;
  private static final int SHOW_LOADING_MESSAGE = 3233;
  private static final String TAG = "FilterActivity";
  public static final int TILT_SHIFT_MODE_CIRCLE = 1;
  public static final int TILT_SHIFT_MODE_LINE;
  private boolean mBordersEnabled;
  private FilterController mController;
  private NativeFilter mCurrentFilter;
  private boolean mFilterChanged = false;
  private FilterGLSurfaceView mFilterView;
  private File mGalleryTempFile = null;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      if (paramMessage.what == 3232)
        if ((FilterFragment.this.mPopupWindow != null) && (FilterFragment.this.mPopupWindow.isShowing()))
          FilterFragment.this.mPopupWindow.dismiss();
      while (true)
      {
        super.handleMessage(paramMessage);
        return;
        if (paramMessage.what != 3233)
          continue;
        if (FilterFragment.this.mProgressDialog != null)
        {
          FilterFragment.this.mProgressDialog.dismiss();
          FilterFragment.access$102(FilterFragment.this, null);
        }
        FilterFragment.access$102(FilterFragment.this, new IgProgressDialog(FilterFragment.this.getActivity()));
        FilterFragment.this.mProgressDialog.setMessage(FilterFragment.this.getString(2131230889));
        FilterFragment.this.mProgressDialog.setCancelable(false);
        FilterFragment.this.mProgressDialog.show();
      }
    }
  };
  private boolean mLuxEnabled;
  private PopupWindow mPopupWindow;
  private IgProgressDialog mProgressDialog = null;
  private NativeRenderer mRenderer;
  private boolean mShouldCloseOnStart = false;
  private boolean mTiltShiftEnabled = false;
  private int mTiltShiftMode = 0;
  private TiltShiftButton mTiltshiftButton;

  private int getTiltShiftButtonState(boolean paramBoolean, int paramInt)
  {
    int i = 1;
    if (paramBoolean)
      if (paramInt != i);
    while (true)
    {
      return i;
      i = 2;
      continue;
      i = 0;
    }
  }

  private boolean hasBordersEnabled()
  {
    return Preferences.getInstance(getActivity()).getHasBordersEnabled();
  }

  private boolean isImageFromGallery()
  {
    Bundle localBundle = getArguments();
    if ((localBundle != null) && (localBundle.getInt("mediaSource") == 0));
    for (int i = 1; ; i = 0)
      return i;
  }

  private boolean isShareIntent()
  {
    int i = 0;
    Bundle localBundle = getArguments();
    if ((localBundle != null) && (localBundle.getBoolean("isShareIntent", false)))
      i = 1;
    return i;
  }

  public static FilterFragment newInstance(int paramInt, boolean paramBoolean1, String paramString, boolean paramBoolean2)
  {
    FilterFragment localFilterFragment = new FilterFragment();
    Bundle localBundle = new Bundle();
    localBundle.putInt("mediaSource", paramInt);
    localBundle.putString("filePath", paramString);
    localBundle.putBoolean("mirrorMedia", paramBoolean2);
    localBundle.putBoolean("isShareIntent", paramBoolean1);
    localFilterFragment.setArguments(localBundle);
    return localFilterFragment;
  }

  private void performFinalRenders()
  {
    renderFullSizeBitmap();
  }

  private void renderBitmapForUpload()
  {
    this.mController.renderToBitmap(this.mHandler, new OffscreenRenderCallbacks(1), 612);
  }

  private void renderFullSizeBitmap()
  {
    this.mController.renderToBitmap(this.mHandler, new OffscreenRenderCallbacks(0), NativeBridge.RENDER_SIZE_UNSPECIFIED);
  }

  private void setBordersEnabled(boolean paramBoolean)
  {
    this.mBordersEnabled = paramBoolean;
    Preferences.getInstance(getActivity()).setHasBordersEnabled(this.mBordersEnabled);
    this.mController.setBordersEnabled(this.mBordersEnabled);
  }

  private void setFilterChanged(boolean paramBoolean)
  {
    this.mFilterChanged = paramBoolean;
  }

  private void setLuxEnabled(boolean paramBoolean)
  {
    this.mLuxEnabled = paramBoolean;
    this.mController.setLuxEnabled(this.mLuxEnabled);
  }

  private void setTiltShiftMode(int paramInt)
  {
    this.mTiltShiftMode = paramInt;
    NativeBridge.setTiltShiftMode(paramInt);
    this.mTiltshiftButton.setState(getTiltShiftButtonState(this.mTiltShiftEnabled, this.mTiltShiftMode));
  }

  private void startTiltShiftWithMode(int paramInt)
  {
    this.mFilterView.setRenderMode(1);
    this.mController.setTiltShiftEnabled(true);
    this.mTiltShiftEnabled = true;
    setTiltShiftMode(paramInt);
    transitionTiltshiftAnimation();
    this.mPopupWindow.dismiss();
    this.mPopupWindow = null;
    this.mHandler.removeMessages(3232);
  }

  private boolean takeFilterChanged()
  {
    boolean bool = this.mFilterChanged;
    this.mFilterChanged = false;
    return bool;
  }

  private void transitionTiltshiftAnimation()
  {
    this.mHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        NativeBridge.tiltShiftFadeInMaskHighlight();
        FilterFragment.this.mHandler.postDelayed(new FilterFragment.13.1(this), 500L);
      }
    }
    , 100L);
  }

  public boolean getBordersEnabled()
  {
    return this.mBordersEnabled;
  }

  public int getCurrentFilter()
  {
    return this.mCurrentFilter.getId();
  }

  public boolean getLuxEnabled()
  {
    return this.mLuxEnabled;
  }

  public Bitmap getMasterTextureBitmap()
  {
    Bundle localBundle = getArguments();
    Bitmap localBitmap;
    if (localBundle == null)
    {
      Log.e("FilterActivity", "getMasterTextureBitmap called without arguments (no file!)");
      localBitmap = null;
    }
    label116: 
    while (true)
    {
      return localBitmap;
      localBitmap = null;
      if (localBundle.containsKey("filePath"))
      {
        Uri localUri = Uri.parse(localBundle.getString("filePath"));
        localBitmap = BitmapHelper.largestSquareBitmap(getActivity().getContentResolver(), localUri);
      }
      while (true)
      {
        if (localBitmap == null)
          break label116;
        Log.d("FilterActivity", "Master texture bitmap " + localBitmap.getWidth() + "x" + localBitmap.getHeight());
        break;
        Log.e("FilterActivity", "No intent extra providing file path/uri for master texture bitmap");
      }
    }
  }

  public boolean getMirrorMasterTexture()
  {
    return getArguments().getBoolean("mirrorMedia");
  }

  public boolean getTiltShiftEnabled()
  {
    return this.mTiltShiftEnabled;
  }

  public int getTiltShiftMode()
  {
    return this.mTiltShiftMode;
  }

  public float getTiltShiftOriginX()
  {
    return this.mFilterView.getTiltShiftOriginX();
  }

  public float getTiltShiftOriginY()
  {
    return this.mFilterView.getTiltShiftOriginY();
  }

  public PopupWindow getTiltShiftPopupWindow()
  {
    if (this.mPopupWindow == null)
    {
      View localView = LayoutInflater.from(getActivity()).inflate(2130903096, null);
      this.mPopupWindow = new PopupWindow(localView, -2, -2);
      localView.setVisibility(0);
      localView.findViewById(2131493004).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          FilterFragment.this.startTiltShiftWithMode(1);
        }
      });
      localView.findViewById(2131493005).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          FilterFragment.this.startTiltShiftWithMode(0);
        }
      });
      localView.findViewById(2131493006).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          FilterFragment.this.mFilterView.setRenderMode(0);
          FilterFragment.this.mController.setTiltShiftEnabled(false);
          FilterFragment.access$1202(FilterFragment.this, false);
          FilterFragment.this.mPopupWindow.dismiss();
          FilterFragment.access$202(FilterFragment.this, null);
          FilterFragment.this.mHandler.removeMessages(3232);
          FilterFragment.this.mTiltshiftButton.setState(FilterFragment.this.getTiltShiftButtonState(FilterFragment.this.mTiltShiftEnabled, FilterFragment.this.mTiltShiftMode));
        }
      });
    }
    return this.mPopupWindow;
  }

  public float getTiltShiftRadius()
  {
    return this.mFilterView.getTiltShiftRadius();
  }

  public float getTiltShiftTheta()
  {
    return this.mFilterView.getTiltShiftTheta();
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 0) && (paramInt2 != -1))
      this.mShouldCloseOnStart = true;
    while (true)
    {
      return;
      if (paramInt2 == -1)
        switch (paramInt1)
        {
        default:
          break;
        case 0:
          GalleryUtil.handleActivityResult(this, paramIntent, this.mGalleryTempFile, 1);
          break;
        case 1:
          Bundle localBundle = getArguments();
          localBundle.putString("filePath", paramIntent.getAction());
          localBundle.putInt("mediaSource", 0);
          break;
        case 2:
          getActivity().setResult(-1);
          getActivity().finish();
          continue;
        }
    }
  }

  public boolean onBackPressed()
  {
    int i = 0;
    if (isShareIntent());
    while (true)
    {
      return i;
      if (!isImageFromGallery())
        continue;
      this.mGalleryTempFile = FileUtil.generateTempFile(AppContext.getContext());
      GalleryUtil.show(this, 0, this.mGalleryTempFile);
      i = 1;
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    NativeBridge.setMasterTextureDelegate(this);
    this.mCurrentFilter = null;
    this.mBordersEnabled = hasBordersEnabled();
    if (paramBundle != null)
    {
      this.mLuxEnabled = paramBundle.getBoolean("luxEnabled");
      this.mTiltShiftEnabled = paramBundle.getBoolean("tiltEnabled");
      this.mTiltShiftMode = paramBundle.getInt("tiltEnabled");
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903069, paramViewGroup, false);
    this.mFilterView = ((FilterGLSurfaceView)localView.findViewById(2131492947));
    if (ViewUtil.getScreenHeightPixels(getActivity()) == 320)
    {
      ViewGroup.LayoutParams localLayoutParams = this.mFilterView.getLayoutParams();
      localLayoutParams.width = 215;
      localLayoutParams.height = 215;
      this.mFilterView.setLayoutParams(localLayoutParams);
    }
    this.mRenderer = this.mFilterView.getRenderer();
    this.mController = new FilterController()
    {
      public FilterGLSurfaceView getFilterView()
      {
        return FilterFragment.this.mFilterView;
      }
    };
    this.mFilterView.setFilterController(this.mController);
    if (!NativeBridge.getLuxSupported())
      ((LinearLayout)localView.findViewById(2131492888)).setWeightSum(8.0F);
    ActionBarHighlightButton localActionBarHighlightButton1 = (ActionBarHighlightButton)localView.findViewById(2131492889);
    localActionBarHighlightButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
      {
        FilterFragment.this.setBordersEnabled(paramBoolean);
      }
    });
    localActionBarHighlightButton1.setChecked(this.mBordersEnabled);
    this.mTiltshiftButton = ((TiltShiftButton)localView.findViewById(2131492890));
    ActionBarHighlightButton localActionBarHighlightButton2;
    if (NativeBridge.getTiltShiftSupported())
    {
      this.mTiltshiftButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (FilterFragment.this.mPopupWindow != null)
          {
            FilterFragment.this.mPopupWindow.dismiss();
            FilterFragment.access$202(FilterFragment.this, null);
            FilterFragment.this.mHandler.removeMessages(3232);
          }
          while (true)
          {
            return;
            int[] arrayOfInt = new int[2];
            FilterFragment.this.mTiltshiftButton.getLocationInWindow(arrayOfInt);
            FilterFragment.this.getTiltShiftPopupWindow().showAtLocation(FilterFragment.this.getView(), 0, arrayOfInt[0] - FilterFragment.this.getActivity().getResources().getDimensionPixelSize(2131427348), arrayOfInt[1] + FilterFragment.this.mTiltshiftButton.getHeight() + FilterFragment.this.getActivity().getResources().getDimensionPixelSize(2131427347));
            FilterFragment.this.getTiltShiftPopupWindow().update();
            FilterFragment.this.mHandler.sendEmptyMessageDelayed(3232, 3000L);
          }
        }
      });
      this.mTiltshiftButton.setVisibility(0);
      this.mTiltshiftButton.setState(getTiltShiftButtonState(this.mTiltShiftEnabled, this.mTiltShiftMode));
      FilterPicker localFilterPicker = (FilterPicker)localView.findViewById(2131492948);
      this.mCurrentFilter = localFilterPicker.getSelectedFilter();
      localFilterPicker.setOnFilterChangedListener(new FilterPicker.OnFilterChangedListener()
      {
        public void onFilterChanged(NativeFilter paramNativeFilter)
        {
          FilterFragment.access$702(FilterFragment.this, paramNativeFilter);
          FilterFragment.this.mController.useFilter(paramNativeFilter.getId());
        }
      });
      localView.findViewById(2131492893).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          paramView.setEnabled(false);
          if (FilterFragment.this.mFilterView != null)
            FilterFragment.this.mFilterView.requestRender();
          if (FilterFragment.this.mProgressDialog != null)
          {
            FilterFragment.this.mProgressDialog.dismiss();
            FilterFragment.access$102(FilterFragment.this, null);
          }
          FilterFragment.access$102(FilterFragment.this, new IgProgressDialog(FilterFragment.this.getActivity()));
          FilterFragment.this.mProgressDialog.setMessage(FilterFragment.this.getString(2131231051));
          FilterFragment.this.mProgressDialog.setCancelable(false);
          FilterFragment.this.mHandler.postDelayed(new FilterFragment.6.1(this), 500L);
          FilterFragment.this.performFinalRenders();
        }
      });
      localActionBarHighlightButton2 = (ActionBarHighlightButton)localView.findViewById(2131492891);
      if (!NativeBridge.getLuxSupported())
        break label386;
      localActionBarHighlightButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
      {
        public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
        {
          FilterFragment.this.setLuxEnabled(paramBoolean);
        }
      });
      localActionBarHighlightButton2.setVisibility(0);
      label320: localView.findViewById(2131492892).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          FilterFragment.this.mController.rotateMasterTexture();
        }
      });
      if (!Preferences.getInstance(getActivity()).getHasAdvancedCameraEnabled())
        break label396;
      localView.findViewById(2131492887).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          FragmentManager localFragmentManager = FilterFragment.this.getFragmentManager();
          if (localFragmentManager == null);
          while (true)
          {
            return;
            if (localFragmentManager.getBackStackEntryCount() > 0)
            {
              localFragmentManager.popBackStack();
              continue;
            }
            FilterFragment.this.getActivity().onBackPressed();
          }
        }
      });
    }
    while (true)
    {
      return localView;
      this.mTiltshiftButton.setVisibility(8);
      break;
      label386: localActionBarHighlightButton2.setVisibility(8);
      break label320;
      label396: localView.findViewById(2131492887).setVisibility(8);
    }
  }

  public void onDestroy()
  {
    super.onDestroy();
    NativeBridge.setMasterTextureDelegate(null);
    if ((this.mProgressDialog != null) && (this.mProgressDialog.isShowing()))
      this.mProgressDialog.dismiss();
    this.mProgressDialog = null;
    if ((this.mPopupWindow != null) && (this.mPopupWindow.isShowing()))
      this.mPopupWindow.dismiss();
  }

  public void onFinishLoadMasterTexture()
  {
    this.mHandler.removeMessages(3233);
    if ((this.mProgressDialog != null) && (this.mProgressDialog.isShowing()))
      this.mProgressDialog.dismiss();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return false;
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return false;
  }

  public void onPause()
  {
    super.onPause();
    this.mFilterView.onPause();
  }

  public void onResume()
  {
    super.onResume();
    this.mFilterView.onResume();
    CameraUsageReportingUtil.didOpenFilters();
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putBoolean("luxEnabled", this.mLuxEnabled);
    paramBundle.putBoolean("tiltEnabled", this.mTiltShiftEnabled);
    paramBundle.putInt("tiltEnabled", this.mTiltShiftMode);
  }

  public void onStart()
  {
    super.onStart();
    if (this.mShouldCloseOnStart)
    {
      this.mShouldCloseOnStart = false;
      FragmentManager localFragmentManager = getFragmentManager();
      if (localFragmentManager.getBackStackEntryCount() <= 0)
        break label33;
      localFragmentManager.popBackStack();
    }
    while (true)
    {
      return;
      label33: getActivity().finish();
    }
  }

  public void onStartLoadMasterTexture()
  {
    this.mHandler.sendEmptyMessageDelayed(3233, 1000L);
  }

  private class OffscreenRenderCallbacks
    implements FilterController.RenderCallbacks
  {
    private final int mAction;

    public OffscreenRenderCallbacks(int arg2)
    {
      int i;
      this.mAction = i;
    }

    public void renderFinished(Bitmap paramBitmap)
    {
      Log.d("FilterActivity", "Render finished with action: " + this.mAction);
      if (this.mAction == 0)
      {
        FragmentActivity localFragmentActivity = FilterFragment.this.getActivity();
        ContentResolver localContentResolver = null;
        if (localFragmentActivity != null)
          localContentResolver = localFragmentActivity.getContentResolver();
        FilterFragment.OffscreenRenderCallbacks.1 local1 = new FilterFragment.OffscreenRenderCallbacks.1(this, localContentResolver);
        Bitmap[] arrayOfBitmap2 = new Bitmap[1];
        arrayOfBitmap2[0] = paramBitmap;
        local1.execute(arrayOfBitmap2);
      }
      while (true)
      {
        Log.d("FilterActivity", "Render finished");
        return;
        if (this.mAction != 1)
          continue;
        int i = FilterFragment.this.getArguments().getInt("mediaSource");
        FilterFragment.OffscreenRenderCallbacks.2 local2 = new FilterFragment.OffscreenRenderCallbacks.2(this, FilterFragment.this.getActivity(), i);
        Bitmap[] arrayOfBitmap1 = new Bitmap[1];
        arrayOfBitmap1[0] = paramBitmap;
        local2.execute(arrayOfBitmap1);
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.FilterFragment
 * JD-Core Version:    0.6.0
 */