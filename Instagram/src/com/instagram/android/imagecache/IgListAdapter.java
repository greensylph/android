package com.instagram.android.imagecache;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class IgListAdapter extends BaseAdapter
{
  public static String[] items;
  private Context mContext;
  private boolean mIsGrid = false;

  static
  {
    String[] arrayOfString = new String[44];
    arrayOfString[0] = "http://distilleryimage6.instagram.com/05971f645e8511e1b9f1123138140926_7.jpg";
    arrayOfString[1] = "http://distilleryimage11.s3.amazonaws.com/1167184c5c0711e19e4a12313813ffc0_7.jpg";
    arrayOfString[2] = "http://distilleryimage10.instagram.com/07171d9c5c4d11e18bb812313804a181_7.jpg";
    arrayOfString[3] = "http://distilleryimage3.s3.amazonaws.com/a86b2fde5cee11e180c9123138016265_7.jpg";
    arrayOfString[4] = "http://distilleryimage9.s3.amazonaws.com/32d3388e569f11e1b9f1123138140926_7.jpg";
    arrayOfString[5] = "http://distilleryimage8.s3.amazonaws.com/dcb9f2425bd211e19896123138142014_7.jpg";
    arrayOfString[6] = "http://distilleryimage7.s3.amazonaws.com/1682809c5a6c11e19e4a12313813ffc0_7.jpg";
    arrayOfString[7] = "http://distilleryimage9.s3.amazonaws.com/aadab9845a4011e18bb812313804a181_7.jpg";
    arrayOfString[8] = "http://distilleryimage1.s3.amazonaws.com/a3826efe56a111e1abb01231381b65e3_7.jpg";
    arrayOfString[9] = "http://distilleryimage2.s3.amazonaws.com/a31e7bdc586411e1a87612313804ec91_7.jpg";
    arrayOfString[10] = "http://distilleryimage8.s3.amazonaws.com/ed0d1a4056d011e19896123138142014_7.jpg";
    arrayOfString[11] = "http://distilleryimage5.s3.amazonaws.com/53334e9a59e511e19896123138142014_7.jpg";
    arrayOfString[12] = "http://distilleryimage11.s3.amazonaws.com/15d0d91a58ef11e19e4a12313813ffc0_7.jpg";
    arrayOfString[13] = "http://distilleryimage10.s3.amazonaws.com/65e9999e59c811e180c9123138016265_7.jpg";
    arrayOfString[14] = "http://distilleryimage8.s3.amazonaws.com/971c1170575911e1abb01231381b65e3_7.jpg";
    arrayOfString[15] = "http://distilleryimage11.s3.amazonaws.com/386a2274420111e1abb01231381b65e3_7.jpg";
    arrayOfString[16] = "http://distilleryimage0.s3.amazonaws.com/7524761424c511e19e4a12313813ffc0_7.jpg";
    arrayOfString[17] = "http://distilleryimage7.s3.amazonaws.com/3f97e8545cd411e19896123138142014_7.jpg";
    arrayOfString[18] = "http://distilleryimage8.s3.amazonaws.com/cda70a465c9611e1a87612313804ec91_7.jpg";
    arrayOfString[19] = "http://distilleryimage2.s3.amazonaws.com/11f00a30572511e180c9123138016265_7.jpg";
    arrayOfString[20] = "http://distilleryimage2.s3.amazonaws.com/51072a7e59b311e180c9123138016265_7.jpg";
    arrayOfString[21] = "http://distilleryimage11.s3.amazonaws.com/0e66f64e5e4111e1a87612313804ec91_7.jpg";
    arrayOfString[22] = "http://distilleryimage4.s3.amazonaws.com/936d8a185b3211e1b9f1123138140926_7.jpg";
    arrayOfString[23] = "http://distilleryimage7.s3.amazonaws.com/8f9afc485cba11e19896123138142014_7.jpg";
    arrayOfString[24] = "http://distilleryimage0.s3.amazonaws.com/2ed4a1245d2911e18bb812313804a181_7.jpg";
    arrayOfString[25] = "http://distilleryimage5.s3.amazonaws.com/4f522b505b6311e19896123138142014_7.jpg";
    arrayOfString[26] = "http://distilleryimage2.s3.amazonaws.com/9526c27e59fd11e1b9f1123138140926_7.jpg";
    arrayOfString[27] = "http://distilleryimage7.s3.amazonaws.com/63023ba05b8f11e1a87612313804ec91_7.jpg";
    arrayOfString[28] = "http://distilleryimage6.s3.amazonaws.com/183edf1e5b4a11e19896123138142014_7.jpg";
    arrayOfString[29] = "http://distilleryimage10.s3.amazonaws.com/c79171365b6111e19896123138142014_7.jpg";
    arrayOfString[30] = "http://distilleryimage9.s3.amazonaws.com/d0a28df659df11e19896123138142014_7.jpg";
    arrayOfString[31] = "http://distilleryimage10.s3.amazonaws.com/5d9579445a2111e1b9f1123138140926_7.jpg";
    arrayOfString[32] = "http://distilleryimage6.s3.amazonaws.com/de314cdc5b3811e18bb812313804a181_7.jpg";
    arrayOfString[33] = "http://distilleryimage11.s3.amazonaws.com/153816905a5611e1b9f1123138140926_7.jpg";
    arrayOfString[34] = "http://distilleryimage6.s3.amazonaws.com/554a68845b6211e1b9f1123138140926_7.jpg";
    arrayOfString[35] = "http://distilleryimage4.s3.amazonaws.com/734219de5a6a11e1b9f1123138140926_7.jpg";
    arrayOfString[36] = "http://distilleryimage9.s3.amazonaws.com/97884f505b5211e19e4a12313813ffc0_7.jpg";
    arrayOfString[37] = "http://distilleryimage2.s3.amazonaws.com/e29b0542536811e19896123138142014_7.jpg";
    arrayOfString[38] = "http://distilleryimage8.s3.amazonaws.com/73fe74b6551611e1abb01231381b65e3_7.jpg";
    arrayOfString[39] = "http://distilleryimage2.s3.amazonaws.com/bc4fb27e4fb311e1a87612313804ec91_7.jpg";
    arrayOfString[40] = "http://distilleryimage11.s3.amazonaws.com/b0a24952506511e19896123138142014_7.jpg";
    arrayOfString[41] = "http://distilleryimage1.s3.amazonaws.com/096862844f8411e19e4a12313813ffc0_7.jpg";
    arrayOfString[42] = "http://distilleryimage7.s3.amazonaws.com/bbe52212508311e19896123138142014_7.jpg";
    arrayOfString[43] = "http://distillery.s3.amazonaws.com/media/2011/10/08/43c875e7024643c28a2e4f65e8f3c30b_7.jpg";
    items = arrayOfString;
  }

  public IgListAdapter(Context paramContext)
  {
    this.mContext = paramContext;
  }

  private View getGridView(int paramInt, View paramView)
  {
    Holder2 localHolder2;
    if (paramView == null)
    {
      paramView = LayoutInflater.from(this.mContext).inflate(R.layout.row_grid_image, null);
      localHolder2 = new Holder2();
      localHolder2.igImageView = ((IgImageView)paramView.findViewById(R.id.row_image_igimageview));
      paramView.setTag(localHolder2);
    }
    while (true)
    {
      localHolder2.igImageView.setUrl(getUrlForRow(paramInt));
      return paramView;
      localHolder2 = (Holder2)paramView.getTag();
    }
  }

  private View getNormalView(int paramInt, View paramView)
  {
    Holder localHolder;
    if (paramView == null)
    {
      paramView = LayoutInflater.from(this.mContext).inflate(R.layout.row_image, null);
      localHolder = new Holder();
      localHolder.igImageView = ((IgProgressImageView)paramView.findViewById(R.id.row_image_igimageview));
      paramView.setTag(localHolder);
    }
    while (true)
    {
      localHolder.igImageView.setUrl(getUrlForRow(paramInt));
      return paramView;
      localHolder = (Holder)paramView.getTag();
    }
  }

  public int getCount()
  {
    return 180;
  }

  public Object getItem(int paramInt)
  {
    return null;
  }

  public long getItemId(int paramInt)
  {
    return 0L;
  }

  public String getUrlForRow(int paramInt)
  {
    String str = items[(paramInt % items.length)];
    if (this.mIsGrid)
      str = str.replace("_7.jpg", "_5.jpg");
    return str;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (this.mIsGrid);
    for (View localView = getGridView(paramInt, paramView); ; localView = getNormalView(paramInt, paramView))
      return localView;
  }

  public void setIsGrid(boolean paramBoolean)
  {
    this.mIsGrid = paramBoolean;
  }

  static class Holder2
  {
    IgImageView igImageView;
  }

  static class Holder
  {
    IgProgressImageView igImageView;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.IgListAdapter
 * JD-Core Version:    0.6.0
 */