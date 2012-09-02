package com.facebook.android;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FbDialog extends Dialog
{
  static final float[] DIMENSIONS_DIFF_LANDSCAPE;
  static final float[] DIMENSIONS_DIFF_PORTRAIT;
  static final String DISPLAY_STRING = "touch";
  static final int FB_BLUE = -9599820;
  static final String FB_ICON = "icon.png";
  static final FrameLayout.LayoutParams FILL;
  static final int MARGIN = 4;
  static final int PADDING = 2;
  private FrameLayout mContent;
  private ImageView mCrossImage;
  private Facebook.DialogListener mListener;
  private ProgressDialog mSpinner;
  private String mUrl;
  private WebView mWebView;

  static
  {
    float[] arrayOfFloat1 = new float[2];
    arrayOfFloat1[0] = 20.0F;
    arrayOfFloat1[1] = 60.0F;
    DIMENSIONS_DIFF_LANDSCAPE = arrayOfFloat1;
    float[] arrayOfFloat2 = new float[2];
    arrayOfFloat2[0] = 40.0F;
    arrayOfFloat2[1] = 60.0F;
    DIMENSIONS_DIFF_PORTRAIT = arrayOfFloat2;
    FILL = new FrameLayout.LayoutParams(-1, -1);
  }

  public FbDialog(Context paramContext, String paramString, Facebook.DialogListener paramDialogListener)
  {
    super(paramContext, 16973840);
    this.mUrl = paramString;
    this.mListener = paramDialogListener;
  }

  private void createCrossImage()
  {
    this.mCrossImage = new ImageView(getContext());
    this.mCrossImage.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        FbDialog.this.mListener.onCancel();
        FbDialog.this.dismiss();
      }
    });
    Drawable localDrawable = getContext().getResources().getDrawable(R.drawable.close);
    this.mCrossImage.setImageDrawable(localDrawable);
    this.mCrossImage.setVisibility(4);
  }

  private void setUpWebView(int paramInt)
  {
    LinearLayout localLinearLayout = new LinearLayout(getContext());
    this.mWebView = new WebView(getContext());
    this.mWebView.setVerticalScrollBarEnabled(false);
    this.mWebView.setHorizontalScrollBarEnabled(false);
    this.mWebView.setWebViewClient(new FbWebViewClient(null));
    this.mWebView.getSettings().setJavaScriptEnabled(true);
    this.mWebView.loadUrl(this.mUrl);
    this.mWebView.setLayoutParams(FILL);
    this.mWebView.setVisibility(4);
    localLinearLayout.setPadding(paramInt, paramInt, paramInt, paramInt);
    localLinearLayout.addView(this.mWebView);
    this.mContent.addView(localLinearLayout);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mSpinner = new ProgressDialog(getContext());
    this.mSpinner.requestWindowFeature(1);
    this.mSpinner.setMessage("Loading...");
    requestWindowFeature(1);
    this.mContent = new FrameLayout(getContext());
    createCrossImage();
    setUpWebView(this.mCrossImage.getDrawable().getIntrinsicWidth() / 2);
    this.mContent.addView(this.mCrossImage, new ViewGroup.LayoutParams(-2, -2));
    addContentView(this.mContent, new ViewGroup.LayoutParams(-1, -1));
  }

  private class FbWebViewClient extends WebViewClient
  {
    private FbWebViewClient()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      super.onPageFinished(paramWebView, paramString);
      FbDialog.this.mSpinner.dismiss();
      FbDialog.this.mContent.setBackgroundColor(0);
      FbDialog.this.mWebView.setVisibility(0);
      FbDialog.this.mCrossImage.setVisibility(0);
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      Util.logd("Facebook-WebView", "Webview loading URL: " + paramString);
      super.onPageStarted(paramWebView, paramString, paramBitmap);
      FbDialog.this.mSpinner.show();
    }

    public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
      FbDialog.this.mListener.onError(new DialogError(paramString1, paramInt, paramString2));
      FbDialog.this.dismiss();
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      int i = 1;
      Util.logd("Facebook-WebView", "Redirect URL: " + paramString);
      String str;
      if (paramString.startsWith("fbconnect://success"))
      {
        Bundle localBundle = Util.parseUrl(paramString);
        str = localBundle.getString("error");
        if (str == null)
          str = localBundle.getString("error_type");
        if (str == null)
        {
          FbDialog.this.mListener.onComplete(localBundle);
          FbDialog.this.dismiss();
        }
      }
      while (true)
      {
        return i;
        if ((str.equals("access_denied")) || (str.equals("OAuthAccessDeniedException")))
        {
          FbDialog.this.mListener.onCancel();
          break;
        }
        FbDialog.this.mListener.onFacebookError(new FacebookError(str));
        break;
        if (paramString.startsWith("fbconnect://cancel"))
        {
          FbDialog.this.mListener.onCancel();
          FbDialog.this.dismiss();
          continue;
        }
        if (paramString.contains("touch"))
        {
          i = 0;
          continue;
        }
        FbDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.facebook.android.FbDialog
 * JD-Core Version:    0.6.0
 */