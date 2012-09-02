package com.instagram.android.support.camera;

import android.graphics.Bitmap;
import java.util.concurrent.CountDownLatch;

class CropImage$1$1
  implements Runnable
{
  public void run()
  {
    if ((this.val$b != CropImage.access$200(this.this$1.this$0)) && (this.val$b != null))
    {
      CropImage.access$800(this.this$1.this$0).setImageBitmapResetBase(this.val$b, true);
      CropImage.access$200(this.this$1.this$0).recycle();
      CropImage.access$202(this.this$1.this$0, this.val$b);
    }
    if (CropImage.access$800(this.this$1.this$0).getScale() == 1.0F)
      CropImage.access$800(this.this$1.this$0).center(true, true);
    this.val$latch.countDown();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.CropImage.1.1
 * JD-Core Version:    0.6.0
 */