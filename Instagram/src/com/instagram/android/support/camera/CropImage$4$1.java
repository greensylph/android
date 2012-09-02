package com.instagram.android.support.camera;

import android.widget.Toast;
import com.android.gallery.R.string;
import java.util.ArrayList;

class CropImage$4$1
  implements Runnable
{
  public void run()
  {
    CropImage localCropImage = this.this$1.this$0;
    boolean bool;
    if (this.this$1.mNumFaces > 1)
      bool = true;
    while (true)
    {
      localCropImage.mWaitingToPick = bool;
      if (this.this$1.mNumFaces > 0)
      {
        for (int i = 0; i < this.this$1.mNumFaces; i++)
          CropImage.4.access$1600(this.this$1, this.this$1.mFaces[i]);
        bool = false;
        continue;
      }
      CropImage.4.access$1700(this.this$1);
    }
    CropImage.access$800(this.this$1.this$0).invalidate();
    if (CropImage.access$800(this.this$1.this$0).mHighlightViews.size() == 1)
    {
      this.this$1.this$0.mCrop = ((HighlightView)CropImage.access$800(this.this$1.this$0).mHighlightViews.get(0));
      this.this$1.this$0.mCrop.setFocus(true);
    }
    if (this.this$1.mNumFaces > 1)
      Toast.makeText(this.this$1.this$0, R.string.multiface_crop_help, 0).show();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.CropImage.4.1
 * JD-Core Version:    0.6.0
 */