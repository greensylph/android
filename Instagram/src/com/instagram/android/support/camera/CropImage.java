package com.instagram.android.support.camera;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.System;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.android.gallery.R.id;
import com.android.gallery.R.layout;
import com.android.gallery.R.string;
import com.instagram.android.support.camera.gallery.IImage;
import com.instagram.android.support.camera.gallery.IImageList;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class CropImage extends MonitoredActivity
{
  private static final String TAG = "CropImage";
  private IImageList mAllImages;
  private int mAspectX;
  private int mAspectY;
  private Bitmap mBitmap;
  private boolean mCircleCrop = false;
  private ContentResolver mContentResolver;
  HighlightView mCrop;
  private boolean mDoFaceDetection = true;
  private final Handler mHandler = new Handler();
  private IImage mImage;
  private Uri mImageUri;
  private CropImageView mImageView;
  private int mLargestDimension;
  private Bitmap.CompressFormat mOutputFormat = Bitmap.CompressFormat.JPEG;
  private int mOutputX;
  private int mOutputY;
  Runnable mRunFaceDetection = new Runnable()
  {
    FaceDetector.Face[] mFaces = new FaceDetector.Face[3];
    Matrix mImageMatrix;
    int mNumFaces;
    float mScale = 1.0F;

    private void handleFace(FaceDetector.Face paramFace)
    {
      PointF localPointF = new PointF();
      int i = 2 * (int)(paramFace.eyesDistance() * this.mScale);
      paramFace.getMidPoint(localPointF);
      localPointF.x *= this.mScale;
      localPointF.y *= this.mScale;
      int j = (int)localPointF.x;
      int k = (int)localPointF.y;
      HighlightView localHighlightView = new HighlightView(CropImage.this.mImageView);
      Rect localRect = new Rect(0, 0, CropImage.this.mBitmap.getWidth(), CropImage.this.mBitmap.getHeight());
      RectF localRectF = new RectF(j, k, j, k);
      localRectF.inset(-i, -i);
      if (localRectF.left < 0.0F)
        localRectF.inset(-localRectF.left, -localRectF.left);
      if (localRectF.top < 0.0F)
        localRectF.inset(-localRectF.top, -localRectF.top);
      if (localRectF.right > localRect.right)
        localRectF.inset(localRectF.right - localRect.right, localRectF.right - localRect.right);
      if (localRectF.bottom > localRect.bottom)
        localRectF.inset(localRectF.bottom - localRect.bottom, localRectF.bottom - localRect.bottom);
      Matrix localMatrix = this.mImageMatrix;
      boolean bool1 = CropImage.this.mCircleCrop;
      if ((CropImage.this.mAspectX != 0) && (CropImage.this.mAspectY != 0));
      for (boolean bool2 = true; ; bool2 = false)
      {
        localHighlightView.setup(localMatrix, localRect, localRectF, bool1, bool2, 1.0F);
        CropImage.this.mImageView.add(localHighlightView);
        return;
      }
    }

    private void makeDefault()
    {
      HighlightView localHighlightView = new HighlightView(CropImage.this.mImageView);
      int i = CropImage.this.mBitmap.getWidth();
      int j = CropImage.this.mBitmap.getHeight();
      int k = CropImage.this.mImage.getWidth();
      int m = CropImage.this.mImage.getHeight();
      int n = (int)(0.37D * (int)(Runtime.getRuntime().maxMemory() / 4L));
      Log.d("CropImage", "Max number of pixels: " + n);
      int i1 = k;
      int i2 = m;
      while (i1 * i2 > n)
      {
        i1 /= 2;
        i2 /= 2;
      }
      float f = 0.0F;
      if (CropImage.this.mSmallestDimension > 0)
        f = i * CropImage.this.mSmallestDimension / i1 / i;
      if (f > 1.0F)
        f = 0.0F;
      Rect localRect = new Rect(0, 0, i, j);
      int i3 = Math.max((int)(f * i), 4 * Math.min(i, j) / 5);
      int i4 = i3;
      RectF localRectF;
      Matrix localMatrix;
      boolean bool1;
      if ((CropImage.this.mAspectX != 0) && (CropImage.this.mAspectY != 0))
      {
        if (CropImage.this.mAspectX > CropImage.this.mAspectY)
          i4 = i3 * CropImage.this.mAspectY / CropImage.this.mAspectX;
      }
      else
      {
        int i5 = (i - i3) / 2;
        int i6 = (j - i4) / 2;
        localRectF = new RectF(i5, i6, i5 + i3, i6 + i4);
        localMatrix = this.mImageMatrix;
        bool1 = CropImage.this.mCircleCrop;
        if ((CropImage.this.mAspectX == 0) || (CropImage.this.mAspectY == 0))
          break label411;
      }
      label411: for (boolean bool2 = true; ; bool2 = false)
      {
        localHighlightView.setup(localMatrix, localRect, localRectF, bool1, bool2, f);
        CropImage.this.mImageView.add(localHighlightView);
        return;
        i3 = i4 * CropImage.this.mAspectX / CropImage.this.mAspectY;
        break;
      }
    }

    private Bitmap prepareBitmap()
    {
      if (CropImage.this.mBitmap == null);
      Matrix localMatrix;
      for (Bitmap localBitmap = null; ; localBitmap = Bitmap.createBitmap(CropImage.this.mBitmap, 0, 0, CropImage.this.mBitmap.getWidth(), CropImage.this.mBitmap.getHeight(), localMatrix, true))
      {
        return localBitmap;
        if (CropImage.this.mBitmap.getWidth() > 256)
          this.mScale = (256.0F / CropImage.this.mBitmap.getWidth());
        localMatrix = new Matrix();
        localMatrix.setScale(this.mScale, this.mScale);
      }
    }

    public void run()
    {
      this.mImageMatrix = CropImage.this.mImageView.getImageMatrix();
      Bitmap localBitmap = prepareBitmap();
      this.mScale = (1.0F / this.mScale);
      if ((localBitmap != null) && (CropImage.this.mDoFaceDetection))
        this.mNumFaces = new FaceDetector(localBitmap.getWidth(), localBitmap.getHeight(), this.mFaces.length).findFaces(localBitmap, this.mFaces);
      if ((localBitmap != null) && (localBitmap != CropImage.this.mBitmap))
        localBitmap.recycle();
      CropImage.this.mHandler.post(new CropImage.4.1(this));
    }
  };
  private Uri mSaveUri = null;
  boolean mSaving;
  private boolean mScale;
  private boolean mScaleUp = true;
  private boolean mSetWallpaper = false;
  private int mSmallestDimension;
  boolean mWaitingToPick;

  private void onSaveClicked()
  {
    if (this.mCrop == null);
    Bitmap localBitmap2;
    while (true)
    {
      return;
      if (this.mSaving)
        break label979;
      this.mSaving = true;
      if ((this.mOutputX != 0) && (this.mOutputY != 0) && (!this.mScale))
      {
        localBitmap2 = Bitmap.createBitmap(this.mOutputX, this.mOutputY, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap2);
        Rect localRect3 = this.mCrop.getCropRect();
        Rect localRect4 = new Rect(0, 0, this.mOutputX, this.mOutputY);
        int i6 = (localRect3.width() - localRect4.width()) / 2;
        int i7 = (localRect3.height() - localRect4.height()) / 2;
        localRect3.inset(Math.max(0, i6), Math.max(0, i7));
        localRect4.inset(Math.max(0, -i6), Math.max(0, -i7));
        localCanvas.drawBitmap(this.mBitmap, localRect3, localRect4, null);
        this.mImageView.clear();
        this.mBitmap.recycle();
        this.mImageView.mHighlightViews.clear();
        Bundle localBundle1 = getIntent().getExtras();
        if ((localBundle1 == null) || ((localBundle1.getParcelable("data") == null) && (!localBundle1.getBoolean("return-data"))))
          break;
        Bundle localBundle2 = new Bundle();
        localBundle2.putParcelable("data", localBitmap2);
        setResult(-1, new Intent().setAction("inline-data").putExtras(localBundle2));
        finish();
        continue;
      }
      else
      {
        int i = this.mBitmap.getWidth();
        int j = this.mBitmap.getHeight();
        this.mBitmap.recycle();
        this.mImageView.clear();
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = Integer.valueOf(i);
        arrayOfObject1[1] = Integer.valueOf(j);
        Log.d("CropImage", String.format("Large thumb size: %dx%d", arrayOfObject1));
        int k = this.mImage.getWidth();
        int m = this.mImage.getHeight();
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(k);
        arrayOfObject2[1] = Integer.valueOf(m);
        Log.d("CropImage", String.format("Original size: %dx%d", arrayOfObject2));
        int n = (int)(0.37D * (int)(Runtime.getRuntime().maxMemory() / 4L));
        Log.d("CropImage", "Max number of pixels: " + n);
        Bitmap localBitmap1 = this.mImage.fullSizeBitmap(-1, n, true, true);
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = Integer.valueOf(localBitmap1.getWidth());
        arrayOfObject3[1] = Integer.valueOf(localBitmap1.getHeight());
        Log.d("CropImage", String.format("Full size bitmap: %dx%d", arrayOfObject3));
        Rect localRect1 = this.mCrop.getCropRect();
        int i1 = 0;
        int i2 = 0;
        if (localRect1.width() > localRect1.height())
          i1 = (int)(Math.ceil(localRect1.width() - localRect1.height()) / 2.0D);
        while (true)
        {
          Log.d("CropImage", "Crop insets x: " + i1 + ", y:" + i2);
          localRect1.inset(i1, i2);
          Object[] arrayOfObject4 = new Object[2];
          arrayOfObject4[0] = Integer.valueOf(localRect1.width());
          arrayOfObject4[1] = Integer.valueOf(localRect1.height());
          Log.d("CropImage", String.format("Crop rect: %dx%d", arrayOfObject4));
          int i3 = Math.min(i, j);
          float f1 = Math.min(localBitmap1.getWidth(), localBitmap1.getHeight()) / i3;
          Matrix localMatrix = new Matrix();
          localMatrix.postScale(f1, f1);
          RectF localRectF = new RectF(localRect1);
          localMatrix.mapRect(localRectF);
          Rect localRect2 = new Rect((int)localRectF.left, (int)localRectF.top, (int)localRectF.right, (int)localRectF.bottom);
          Object[] arrayOfObject5 = new Object[2];
          arrayOfObject5[0] = Float.valueOf(localRectF.width());
          arrayOfObject5[1] = Float.valueOf(localRectF.height());
          Log.d("CropImage", String.format("Post scale rect: %fx%f", arrayOfObject5));
          float f2 = Math.min(localRectF.width(), localRectF.height());
          int i4 = (int)Math.min(this.mLargestDimension, f2);
          Object[] arrayOfObject6 = new Object[1];
          arrayOfObject6[0] = Integer.valueOf(i4);
          Log.d("CropImage", String.format("Final dimension: %d", arrayOfObject6));
          localBitmap2 = Bitmap.createBitmap(i4, i4, Bitmap.Config.ARGB_8888);
          new Canvas(localBitmap2).drawBitmap(localBitmap1, localRect2, new Rect(0, 0, i4, i4), null);
          localBitmap1.recycle();
          break;
          i2 = (int)(Math.ceil(localRect1.height() - localRect1.width()) / 2.0D);
        }
      }
    }
    Bitmap localBitmap3 = localBitmap2;
    if (this.mSetWallpaper);
    for (int i5 = R.string.wallpaper; ; i5 = R.string.savingImage)
    {
      String str = getResources().getString(i5);
      2 local2 = new Runnable(localBitmap3)
      {
        public void run()
        {
          CropImage.this.saveOutput(this.val$b);
        }
      };
      Util.startBackgroundJob(this, null, str, local2, this.mHandler);
      break;
      label979: break;
    }
  }

  private void saveOutput(Bitmap paramBitmap)
  {
    OutputStream localOutputStream;
    if (this.mSaveUri != null)
      localOutputStream = null;
    while (true)
    {
      try
      {
        localOutputStream = this.mContentResolver.openOutputStream(this.mSaveUri);
        if (localOutputStream == null)
          continue;
        paramBitmap.compress(this.mOutputFormat, 95, localOutputStream);
        Util.closeSilently(localOutputStream);
        Bundle localBundle2 = new Bundle();
        setResult(-1, new Intent(this.mSaveUri.toString()).putExtras(localBundle2));
        this.mHandler.post(new Runnable(paramBitmap)
        {
          public void run()
          {
            CropImage.this.mImageView.clear();
            this.val$b.recycle();
          }
        });
        finish();
        return;
      }
      catch (IOException localIOException2)
      {
        Log.e("CropImage", "Cannot open file: " + this.mSaveUri, localIOException2);
        Util.closeSilently(localOutputStream);
        continue;
      }
      finally
      {
        Util.closeSilently(localOutputStream);
      }
      if (this.mSetWallpaper)
      {
        try
        {
          WallpaperManager.getInstance(this).setBitmap(paramBitmap);
          setResult(-1);
        }
        catch (IOException localIOException1)
        {
          Log.e("CropImage", "Failed to set wallpaper.", localIOException1);
          setResult(0);
        }
        continue;
      }
      Bundle localBundle1 = new Bundle();
      localBundle1.putString("rect", this.mCrop.getCropRect().toString());
      File localFile1 = new File(this.mImage.getDataPath());
      File localFile2 = new File(localFile1.getParent());
      int i = 0;
      String str1 = localFile1.getName();
      String str2 = str1.substring(0, str1.lastIndexOf("."));
      do
        i++;
      while (new File(localFile2.toString() + "/" + str2 + "-" + i + ".jpg").exists());
      try
      {
        int[] arrayOfInt = new int[1];
        Uri localUri = ImageManager.addImage(this.mContentResolver, this.mImage.getTitle(), this.mImage.getDateTaken(), null, localFile2.toString(), str2 + "-" + i + ".jpg", paramBitmap, null, arrayOfInt);
        setResult(-1, new Intent().setAction(localUri.toString()).putExtras(localBundle1));
      }
      catch (Exception localException)
      {
        Log.e("CropImage", "store image fail, continue anyway", localException);
      }
    }
  }

  private void startFaceDetection()
  {
    if (isFinishing());
    while (true)
    {
      return;
      this.mImageView.setImageBitmapResetBase(this.mBitmap, true);
      Util.startBackgroundJob(this, null, getResources().getString(R.string.runningFaceDetection), new Runnable()
      {
        public void run()
        {
          CountDownLatch localCountDownLatch = new CountDownLatch(1);
          Bitmap localBitmap = CropImage.this.mBitmap;
          CropImage.this.mHandler.post(new CropImage.1.1(this, localBitmap, localCountDownLatch));
          try
          {
            localCountDownLatch.await();
            CropImage.this.mRunFaceDetection.run();
            return;
          }
          catch (InterruptedException localInterruptedException)
          {
          }
          throw new RuntimeException(localInterruptedException);
        }
      }
      , this.mHandler);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mContentResolver = getContentResolver();
    requestWindowFeature(1);
    setContentView(R.layout.cropimage);
    Window localWindow = getWindow();
    if (Settings.System.getInt(getContentResolver(), "screen_brightness_mode", 0) == 1)
    {
      WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
      localLayoutParams.screenBrightness = 0.7F;
      localWindow.setAttributes(localLayoutParams);
    }
    this.mImageView = ((CropImageView)findViewById(R.id.image));
    if (Build.VERSION.SDK_INT >= 11)
      this.mImageView.setLayerType(1, null);
    Intent localIntent = getIntent();
    Bundle localBundle = localIntent.getExtras();
    this.mImageUri = localIntent.getData();
    if (localBundle != null)
    {
      if (localBundle.getString("circleCrop") != null)
      {
        this.mCircleCrop = true;
        this.mAspectX = 1;
        this.mAspectY = 1;
      }
      this.mSaveUri = ((Uri)localBundle.getParcelable("output"));
      if (this.mSaveUri == null)
        break label347;
      String str = localBundle.getString("outputFormat");
      if (str != null)
        this.mOutputFormat = Bitmap.CompressFormat.valueOf(str);
    }
    while (true)
    {
      this.mBitmap = ((Bitmap)localBundle.getParcelable("data"));
      this.mAspectX = localBundle.getInt("aspectX");
      this.mAspectY = localBundle.getInt("aspectY");
      this.mOutputX = localBundle.getInt("outputX");
      this.mOutputY = localBundle.getInt("outputY");
      this.mLargestDimension = localBundle.getInt("largestDimension");
      this.mSmallestDimension = localBundle.getInt("smallestDimension");
      this.mScale = localBundle.getBoolean("scale", true);
      this.mScaleUp = localBundle.getBoolean("scaleUpIfNeeded", true);
      this.mDoFaceDetection = false;
      LoadPhotoTask localLoadPhotoTask = new LoadPhotoTask(null);
      Uri[] arrayOfUri = new Uri[1];
      arrayOfUri[0] = this.mImageUri;
      localLoadPhotoTask.execute(arrayOfUri);
      return;
      label347: this.mSetWallpaper = localBundle.getBoolean("setWallpaper");
    }
  }

  protected void onDestroy()
  {
    if (this.mAllImages != null)
      this.mAllImages.close();
    super.onDestroy();
  }

  protected void onPause()
  {
    super.onPause();
  }

  private class LoadPhotoTask extends AsyncTask<Uri, Void, Void>
  {
    private ProgressDialog mProgressDialog;

    private LoadPhotoTask()
    {
    }

    // ERROR //
    protected Void doInBackground(Uri[] paramArrayOfUri)
    {
      // Byte code:
      //   0: aload_1
      //   1: iconst_0
      //   2: aaload
      //   3: astore_2
      //   4: aconst_null
      //   5: astore_3
      //   6: aload_0
      //   7: getfield 16	com/instagram/android/support/camera/CropImage$LoadPhotoTask:this$0	Lcom/instagram/android/support/camera/CropImage;
      //   10: invokestatic 37	com/instagram/android/support/camera/CropImage:access$100	(Lcom/instagram/android/support/camera/CropImage;)Landroid/content/ContentResolver;
      //   13: aload_2
      //   14: invokevirtual 43	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
      //   17: astore_3
      //   18: new 45	java/io/BufferedInputStream
      //   21: dup
      //   22: aload_3
      //   23: invokespecial 48	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
      //   26: astore 10
      //   28: sipush 4096
      //   31: newarray byte
      //   33: astore 11
      //   35: aload 10
      //   37: aload 11
      //   39: invokevirtual 54	java/io/InputStream:read	([B)I
      //   42: istore 12
      //   44: iload 12
      //   46: bipush 255
      //   48: if_icmpne -13 -> 35
      //   51: aload_3
      //   52: ifnull +7 -> 59
      //   55: aload_3
      //   56: invokevirtual 57	java/io/InputStream:close	()V
      //   59: aconst_null
      //   60: areturn
      //   61: astore 8
      //   63: aload_3
      //   64: ifnull -5 -> 59
      //   67: aload_3
      //   68: invokevirtual 57	java/io/InputStream:close	()V
      //   71: goto -12 -> 59
      //   74: astore 9
      //   76: goto -17 -> 59
      //   79: astore 6
      //   81: aload_3
      //   82: ifnull -23 -> 59
      //   85: aload_3
      //   86: invokevirtual 57	java/io/InputStream:close	()V
      //   89: goto -30 -> 59
      //   92: astore 7
      //   94: goto -35 -> 59
      //   97: astore 4
      //   99: aload_3
      //   100: ifnull +7 -> 107
      //   103: aload_3
      //   104: invokevirtual 57	java/io/InputStream:close	()V
      //   107: aload 4
      //   109: athrow
      //   110: astore 13
      //   112: goto -53 -> 59
      //   115: astore 5
      //   117: goto -10 -> 107
      //
      // Exception table:
      //   from	to	target	type
      //   6	44	61	java/io/FileNotFoundException
      //   67	71	74	java/io/IOException
      //   6	44	79	java/io/IOException
      //   85	89	92	java/io/IOException
      //   6	44	97	finally
      //   55	59	110	java/io/IOException
      //   103	107	115	java/io/IOException
    }

    protected void onPostExecute(Void paramVoid)
    {
      try
      {
        this.mProgressDialog.dismiss();
        if (CropImage.this.mBitmap == null)
        {
          CropImage.access$302(CropImage.this, ImageManager.makeImageList(CropImage.this.mContentResolver, CropImage.this.mImageUri, 1));
          CropImage.access$502(CropImage.this, CropImage.this.mAllImages.getImageForUri(CropImage.this.mImageUri));
          if (CropImage.this.mImage != null)
            CropImage.access$202(CropImage.this, CropImage.this.mImage.thumbBitmap(true));
        }
        if (CropImage.this.mBitmap == null)
        {
          CropImage.this.finish();
          return;
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        while (true)
        {
          continue;
          CropImage.this.getWindow().addFlags(1024);
          CropImage.this.findViewById(R.id.discard).setOnClickListener(new CropImage.LoadPhotoTask.1(this));
          CropImage.this.findViewById(R.id.save).setOnClickListener(new CropImage.LoadPhotoTask.2(this));
          CropImage.this.startFaceDetection();
        }
      }
    }

    protected void onPreExecute()
    {
      this.mProgressDialog = new ProgressDialog(CropImage.this);
      this.mProgressDialog.setMessage(CropImage.this.getString(R.string.wait));
      this.mProgressDialog.setIndeterminate(true);
      this.mProgressDialog.setProgressStyle(0);
      this.mProgressDialog.show();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.CropImage
 * JD-Core Version:    0.6.0
 */