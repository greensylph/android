package com.instagram.camera;

import com.instagram.android.Log;

public class Exif
{
  private static final String TAG = "CameraExif";

  public static int getOrientation(byte[] paramArrayOfByte)
  {
    int i = 1;
    int j = 0;
    if (paramArrayOfByte == null)
      return j;
    int k = 0;
    int m = 0;
    label15: int i6;
    int i7;
    while (k + 3 < paramArrayOfByte.length)
    {
      i6 = k + 1;
      if ((0xFF & paramArrayOfByte[k]) != 255)
        break label463;
      i7 = 0xFF & paramArrayOfByte[i6];
      if (i7 == 255)
      {
        k = i6;
        continue;
      }
      k = i6 + 1;
      if ((i7 == 216) || (i7 == i))
        continue;
      if ((i7 != 217) && (i7 != 218))
        break label141;
    }
    while (true)
    {
      if (m > 8)
      {
        int n = pack(paramArrayOfByte, k, 4, false);
        if ((n != 1229531648) && (n != 1296891946))
        {
          Log.e("CameraExif", "Invalid byte order");
          break;
          label141: int i8 = pack(paramArrayOfByte, k, 2, false);
          if ((i8 < 2) || (k + i8 > paramArrayOfByte.length))
          {
            Log.e("CameraExif", "Invalid length");
            break;
          }
          if ((i7 == 225) && (i8 >= 8) && (pack(paramArrayOfByte, k + 2, 4, false) == 1165519206) && (pack(paramArrayOfByte, k + 6, 2, false) == 0))
          {
            k += 8;
            m = i8 - 8;
            continue;
          }
          k += i8;
          m = 0;
          break label15;
        }
        if (n == 1229531648);
        int i1;
        while (true)
        {
          i1 = 2 + pack(paramArrayOfByte, k + 4, 4, i);
          if ((i1 >= 10) && (i1 <= m))
            break label292;
          Log.e("CameraExif", "Invalid offset");
          break;
          i = 0;
        }
        label292: int i2 = k + i1;
        int i3 = m - i1;
        int i5;
        for (int i4 = pack(paramArrayOfByte, i2 - 2, 2, i); ; i4 = i5)
        {
          i5 = i4 - 1;
          if ((i4 <= 0) || (i3 < 12))
            break label452;
          if (pack(paramArrayOfByte, i2, 2, i) == 274)
            switch (pack(paramArrayOfByte, i2 + 8, 2, i))
            {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7:
            default:
              Log.i("CameraExif", "Unsupported orientation");
              break;
            case 3:
              j = 180;
              break;
            case 6:
              j = 90;
              break;
            case 8:
              j = 270;
              break;
            }
          i2 += 12;
          i3 -= 12;
        }
      }
      label452: Log.i("CameraExif", "Orientation not found");
      break;
      label463: k = i6;
    }
  }

  private static int pack(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = 1;
    if (paramBoolean)
    {
      paramInt1 += paramInt2 - 1;
      i = -1;
    }
    int j = 0;
    int m;
    for (int k = paramInt2; ; k = m)
    {
      m = k - 1;
      if (k <= 0)
        break;
      j = j << 8 | 0xFF & paramArrayOfByte[paramInt1];
      paramInt1 += i;
    }
    return j;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.Exif
 * JD-Core Version:    0.6.0
 */