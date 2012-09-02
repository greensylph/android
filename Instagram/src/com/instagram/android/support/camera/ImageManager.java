package com.instagram.android.support.camera;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import com.instagram.android.support.camera.gallery.BaseImageList;
import com.instagram.android.support.camera.gallery.IImage;
import com.instagram.android.support.camera.gallery.IImageList;
import com.instagram.android.support.camera.gallery.ImageList;
import com.instagram.android.support.camera.gallery.ImageListUber;
import com.instagram.android.support.camera.gallery.SingleImageList;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ImageManager
{
  public static final String CAMERA_IMAGE_BUCKET_ID;
  public static final String CAMERA_IMAGE_BUCKET_NAME;
  public static final int INCLUDE_DRM_IMAGES = 2;
  public static final int INCLUDE_IMAGES = 1;
  public static final int INCLUDE_VIDEOS = 4;
  public static final int SORT_ASCENDING = 1;
  public static final int SORT_DESCENDING = 2;
  private static final Uri STORAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
  private static final String TAG = "ImageManager";
  private static final Uri THUMB_URI = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
  private static final Uri VIDEO_STORAGE_URI = Uri.parse("content://media/external/video/media");

  static
  {
    CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera";
    CAMERA_IMAGE_BUCKET_ID = getBucketId(CAMERA_IMAGE_BUCKET_NAME);
  }

  // ERROR //
  public static Uri addImage(ContentResolver paramContentResolver, String paramString1, long paramLong, android.location.Location paramLocation, String paramString2, String paramString3, android.graphics.Bitmap paramBitmap, byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 10
    //   3: new 60	java/lang/StringBuilder
    //   6: dup
    //   7: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   10: aload 5
    //   12: invokevirtual 79	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   15: ldc 99
    //   17: invokevirtual 79	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: aload 6
    //   22: invokevirtual 79	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   28: astore 11
    //   30: new 71	java/io/File
    //   33: dup
    //   34: aload 5
    //   36: invokespecial 102	java/io/File:<init>	(Ljava/lang/String;)V
    //   39: astore 12
    //   41: aload 12
    //   43: invokevirtual 106	java/io/File:exists	()Z
    //   46: ifne +9 -> 55
    //   49: aload 12
    //   51: invokevirtual 109	java/io/File:mkdirs	()Z
    //   54: pop
    //   55: new 111	java/io/FileOutputStream
    //   58: dup
    //   59: new 71	java/io/File
    //   62: dup
    //   63: aload 5
    //   65: aload 6
    //   67: invokespecial 114	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   70: invokespecial 117	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   73: astore 19
    //   75: aload 7
    //   77: ifnull +146 -> 223
    //   80: aload 7
    //   82: getstatic 123	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   85: bipush 75
    //   87: aload 19
    //   89: invokevirtual 129	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   92: pop
    //   93: aload 9
    //   95: iconst_0
    //   96: iconst_0
    //   97: iastore
    //   98: aload 19
    //   100: invokestatic 135	com/instagram/android/support/camera/Util:closeSilently	(Ljava/io/Closeable;)V
    //   103: new 137	android/content/ContentValues
    //   106: dup
    //   107: bipush 7
    //   109: invokespecial 140	android/content/ContentValues:<init>	(I)V
    //   112: astore 20
    //   114: aload 20
    //   116: ldc 142
    //   118: aload_1
    //   119: invokevirtual 145	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   122: aload 20
    //   124: ldc 147
    //   126: aload 6
    //   128: invokevirtual 145	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   131: aload 20
    //   133: ldc 149
    //   135: lload_2
    //   136: invokestatic 155	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   139: invokevirtual 158	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   142: aload 20
    //   144: ldc 160
    //   146: ldc 162
    //   148: invokevirtual 145	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   151: aload 20
    //   153: ldc 164
    //   155: aload 9
    //   157: iconst_0
    //   158: iaload
    //   159: invokestatic 169	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   162: invokevirtual 172	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   165: aload 20
    //   167: ldc 174
    //   169: aload 11
    //   171: invokevirtual 145	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   174: aload 4
    //   176: ifnull +33 -> 209
    //   179: aload 20
    //   181: ldc 176
    //   183: aload 4
    //   185: invokevirtual 182	android/location/Location:getLatitude	()D
    //   188: invokestatic 187	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   191: invokevirtual 190	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Double;)V
    //   194: aload 20
    //   196: ldc 192
    //   198: aload 4
    //   200: invokevirtual 195	android/location/Location:getLongitude	()D
    //   203: invokestatic 187	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   206: invokevirtual 190	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Double;)V
    //   209: aload_0
    //   210: getstatic 43	com/instagram/android/support/camera/ImageManager:STORAGE_URI	Landroid/net/Uri;
    //   213: aload 20
    //   215: invokevirtual 201	android/content/ContentResolver:insert	(Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
    //   218: astore 16
    //   220: aload 16
    //   222: areturn
    //   223: aload 19
    //   225: aload 8
    //   227: invokevirtual 207	java/io/OutputStream:write	([B)V
    //   230: aload 9
    //   232: iconst_0
    //   233: aload 11
    //   235: invokestatic 211	com/instagram/android/support/camera/ImageManager:getExifOrientation	(Ljava/lang/String;)I
    //   238: iastore
    //   239: goto -141 -> 98
    //   242: astore 13
    //   244: aload 19
    //   246: astore 10
    //   248: ldc 32
    //   250: aload 13
    //   252: invokestatic 217	android/util/Log:w	(Ljava/lang/String;Ljava/lang/Throwable;)I
    //   255: pop
    //   256: aconst_null
    //   257: astore 16
    //   259: aload 10
    //   261: invokestatic 135	com/instagram/android/support/camera/Util:closeSilently	(Ljava/io/Closeable;)V
    //   264: goto -44 -> 220
    //   267: astore 17
    //   269: ldc 32
    //   271: aload 17
    //   273: invokestatic 217	android/util/Log:w	(Ljava/lang/String;Ljava/lang/Throwable;)I
    //   276: pop
    //   277: aconst_null
    //   278: astore 16
    //   280: aload 10
    //   282: invokestatic 135	com/instagram/android/support/camera/Util:closeSilently	(Ljava/io/Closeable;)V
    //   285: goto -65 -> 220
    //   288: astore 14
    //   290: aload 10
    //   292: invokestatic 135	com/instagram/android/support/camera/Util:closeSilently	(Ljava/io/Closeable;)V
    //   295: aload 14
    //   297: athrow
    //   298: astore 14
    //   300: aload 19
    //   302: astore 10
    //   304: goto -14 -> 290
    //   307: astore 17
    //   309: aload 19
    //   311: astore 10
    //   313: goto -44 -> 269
    //   316: astore 13
    //   318: goto -70 -> 248
    //
    // Exception table:
    //   from	to	target	type
    //   80	98	242	java/io/FileNotFoundException
    //   223	239	242	java/io/FileNotFoundException
    //   30	75	267	java/io/IOException
    //   30	75	288	finally
    //   248	256	288	finally
    //   269	277	288	finally
    //   80	98	298	finally
    //   223	239	298	finally
    //   80	98	307	java/io/IOException
    //   223	239	307	java/io/IOException
    //   30	75	316	java/io/FileNotFoundException
  }

  private static boolean checkFsWritable()
  {
    int i = 0;
    String str = Environment.getExternalStorageDirectory().toString() + "/DCIM";
    File localFile1 = new File(str);
    if ((!localFile1.isDirectory()) && (!localFile1.mkdirs()));
    while (true)
    {
      return i;
      File localFile2 = new File(str, ".probe");
      try
      {
        if (localFile2.exists())
          localFile2.delete();
        if (!localFile2.createNewFile())
          continue;
        localFile2.delete();
        i = 1;
      }
      catch (IOException localIOException)
      {
      }
    }
  }

  public static void ensureOSXCompatibleFolder()
  {
    File localFile = new File(Environment.getExternalStorageDirectory().toString() + "/DCIM/100ANDRO");
    if ((!localFile.exists()) && (!localFile.mkdir()))
      Log.e("ImageManager", "create NNNAAAAA file: " + localFile.getPath() + " failed");
  }

  public static String getBucketId(String paramString)
  {
    return String.valueOf(paramString.toLowerCase().hashCode());
  }

  public static ImageListParam getEmptyImageListParam()
  {
    ImageListParam localImageListParam = new ImageListParam();
    localImageListParam.mIsEmptyImageList = true;
    return localImageListParam;
  }

  public static int getExifOrientation(String paramString)
  {
    int i = 0;
    Object localObject = null;
    try
    {
      ExifInterface localExifInterface = new ExifInterface(paramString);
      localObject = localExifInterface;
      int j;
      if (localObject != null)
      {
        j = localObject.getAttributeInt("Orientation", -1);
        if (j == -1);
      }
      switch (j)
      {
      case 4:
      case 5:
      case 7:
      default:
        return i;
      case 6:
      case 3:
      case 8:
      }
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        Log.e("ImageManager", "cannot read exif", localIOException);
        continue;
        i = 90;
        continue;
        i = 180;
        continue;
        i = 270;
      }
    }
  }

  public static ImageListParam getImageListParam(DataLocation paramDataLocation, int paramInt1, int paramInt2, String paramString)
  {
    ImageListParam localImageListParam = new ImageListParam();
    localImageListParam.mLocation = paramDataLocation;
    localImageListParam.mInclusion = paramInt1;
    localImageListParam.mSort = paramInt2;
    localImageListParam.mBucketId = paramString;
    return localImageListParam;
  }

  public static ImageListParam getSingleImageListParam(Uri paramUri)
  {
    ImageListParam localImageListParam = new ImageListParam();
    localImageListParam.mSingleImageUri = paramUri;
    return localImageListParam;
  }

  public static boolean hasStorage()
  {
    return hasStorage(true);
  }

  public static boolean hasStorage(boolean paramBoolean)
  {
    boolean bool = true;
    String str = Environment.getExternalStorageState();
    if ("mounted".equals(str))
      if (!paramBoolean);
    for (bool = checkFsWritable(); ; bool = false)
      do
        return bool;
      while ((!paramBoolean) && ("mounted_ro".equals(str)));
  }

  public static boolean isImage(IImage paramIImage)
  {
    return isImageMimeType(paramIImage.getMimeType());
  }

  public static boolean isImageMimeType(String paramString)
  {
    return paramString.startsWith("image/");
  }

  public static boolean isMediaScannerScanning(ContentResolver paramContentResolver)
  {
    boolean bool = false;
    Uri localUri = MediaStore.getMediaScannerUri();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "volume";
    Cursor localCursor = query(paramContentResolver, localUri, arrayOfString, null, null, null);
    if (localCursor != null)
    {
      if (localCursor.getCount() == 1)
      {
        localCursor.moveToFirst();
        bool = "external".equals(localCursor.getString(0));
      }
      localCursor.close();
    }
    return bool;
  }

  static boolean isSingleImageMode(String paramString)
  {
    if ((!paramString.startsWith(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString())) && (!paramString.startsWith(MediaStore.Images.Media.INTERNAL_CONTENT_URI.toString())));
    for (int i = 1; ; i = 0)
      return i;
  }

  public static boolean isVideo(IImage paramIImage)
  {
    return false;
  }

  public static IImageList makeEmptyImageList()
  {
    return makeImageList(null, getEmptyImageListParam());
  }

  public static IImageList makeImageList(ContentResolver paramContentResolver, Uri paramUri, int paramInt)
  {
    String str1;
    IImageList localIImageList;
    if (paramUri != null)
    {
      str1 = paramUri.toString();
      if (!str1.startsWith("content://drm"))
        break label41;
      localIImageList = makeImageList(paramContentResolver, DataLocation.ALL, 2, paramInt, null);
    }
    while (true)
    {
      return localIImageList;
      str1 = "";
      break;
      label41: if (str1.startsWith("content://media/external/video"))
      {
        localIImageList = makeImageList(paramContentResolver, DataLocation.EXTERNAL, 4, paramInt, null);
        continue;
      }
      if (isSingleImageMode(str1))
      {
        localIImageList = makeSingleImageList(paramContentResolver, paramUri);
        continue;
      }
      String str2 = paramUri.getQueryParameter("bucketId");
      localIImageList = makeImageList(paramContentResolver, DataLocation.ALL, 1, paramInt, str2);
    }
  }

  public static IImageList makeImageList(ContentResolver paramContentResolver, DataLocation paramDataLocation, int paramInt1, int paramInt2, String paramString)
  {
    return makeImageList(paramContentResolver, getImageListParam(paramDataLocation, paramInt1, paramInt2, paramString));
  }

  public static IImageList makeImageList(ContentResolver paramContentResolver, ImageListParam paramImageListParam)
  {
    DataLocation localDataLocation = paramImageListParam.mLocation;
    int i = paramImageListParam.mInclusion;
    int j = paramImageListParam.mSort;
    String str = paramImageListParam.mBucketId;
    Uri localUri = paramImageListParam.mSingleImageUri;
    Object localObject;
    if ((paramImageListParam.mIsEmptyImageList) || (paramContentResolver == null))
      localObject = new EmptyImageList(null);
    while (true)
    {
      return localObject;
      if (localUri != null)
      {
        localObject = new SingleImageList(paramContentResolver, localUri);
        continue;
      }
      boolean bool = hasStorage(false);
      ArrayList localArrayList = new ArrayList();
      if ((bool) && (localDataLocation != DataLocation.INTERNAL) && ((i & 0x1) != 0))
        localArrayList.add(new ImageList(paramContentResolver, STORAGE_URI, j, str));
      if (((localDataLocation == DataLocation.INTERNAL) || (localDataLocation == DataLocation.ALL)) && ((i & 0x1) != 0))
        localArrayList.add(new ImageList(paramContentResolver, MediaStore.Images.Media.INTERNAL_CONTENT_URI, j, str));
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        BaseImageList localBaseImageList = (BaseImageList)localIterator.next();
        if (!localBaseImageList.isEmpty())
          continue;
        localBaseImageList.close();
        localIterator.remove();
      }
      if (localArrayList.size() == 1)
      {
        localObject = (BaseImageList)localArrayList.get(0);
        continue;
      }
      localObject = new ImageListUber((IImageList[])localArrayList.toArray(new IImageList[localArrayList.size()]), j);
    }
  }

  public static IImageList makeSingleImageList(ContentResolver paramContentResolver, Uri paramUri)
  {
    return makeImageList(paramContentResolver, getSingleImageListParam(paramUri));
  }

  private static Cursor query(ContentResolver paramContentResolver, Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    Object localObject = null;
    if (paramContentResolver == null);
    while (true)
    {
      return localObject;
      try
      {
        Cursor localCursor = paramContentResolver.query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2);
        localObject = localCursor;
      }
      catch (UnsupportedOperationException localUnsupportedOperationException)
      {
      }
    }
  }

  public static enum DataLocation
  {
    static
    {
      INTERNAL = new DataLocation("INTERNAL", 1);
      EXTERNAL = new DataLocation("EXTERNAL", 2);
      ALL = new DataLocation("ALL", 3);
      DataLocation[] arrayOfDataLocation = new DataLocation[4];
      arrayOfDataLocation[0] = NONE;
      arrayOfDataLocation[1] = INTERNAL;
      arrayOfDataLocation[2] = EXTERNAL;
      arrayOfDataLocation[3] = ALL;
      $VALUES = arrayOfDataLocation;
    }
  }

  private static class EmptyImageList
    implements IImageList
  {
    public void close()
    {
    }

    public HashMap<String, String> getBucketIds()
    {
      return new HashMap();
    }

    public int getCount()
    {
      return 0;
    }

    public IImage getImageAt(int paramInt)
    {
      return null;
    }

    public IImage getImageForUri(Uri paramUri)
    {
      return null;
    }

    public int getImageIndex(IImage paramIImage)
    {
      throw new UnsupportedOperationException();
    }

    public boolean isEmpty()
    {
      return true;
    }

    public boolean removeImage(IImage paramIImage)
    {
      return false;
    }

    public boolean removeImageAt(int paramInt)
    {
      return false;
    }
  }

  public static class ImageListParam
    implements Parcelable
  {
    public static final Parcelable.Creator CREATOR = new ImageManager.ImageListParam.1();
    public String mBucketId;
    public int mInclusion;
    public boolean mIsEmptyImageList;
    public ImageManager.DataLocation mLocation;
    public Uri mSingleImageUri;
    public int mSort;

    public ImageListParam()
    {
    }

    private ImageListParam(Parcel paramParcel)
    {
      this.mLocation = ImageManager.DataLocation.values()[paramParcel.readInt()];
      this.mInclusion = paramParcel.readInt();
      this.mSort = paramParcel.readInt();
      this.mBucketId = paramParcel.readString();
      this.mSingleImageUri = ((Uri)paramParcel.readParcelable(null));
      if (paramParcel.readInt() != 0);
      for (boolean bool = true; ; bool = false)
      {
        this.mIsEmptyImageList = bool;
        return;
      }
    }

    public int describeContents()
    {
      return 0;
    }

    public String toString()
    {
      Object[] arrayOfObject = new Object[6];
      arrayOfObject[0] = this.mLocation;
      arrayOfObject[1] = Integer.valueOf(this.mInclusion);
      arrayOfObject[2] = Integer.valueOf(this.mSort);
      arrayOfObject[3] = this.mBucketId;
      arrayOfObject[4] = Boolean.valueOf(this.mIsEmptyImageList);
      arrayOfObject[5] = this.mSingleImageUri;
      return String.format("ImageListParam{loc=%s,inc=%d,sort=%d,bucket=%s,empty=%b,single=%s}", arrayOfObject);
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.mLocation.ordinal());
      paramParcel.writeInt(this.mInclusion);
      paramParcel.writeInt(this.mSort);
      paramParcel.writeString(this.mBucketId);
      paramParcel.writeParcelable(this.mSingleImageUri, paramInt);
      if (this.mIsEmptyImageList);
      for (int i = 1; ; i = 0)
      {
        paramParcel.writeInt(i);
        return;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.ImageManager
 * JD-Core Version:    0.6.0
 */