package com.instagram.android.support.camera;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.gallery.R.drawable;

class HighlightView
{
  public static final int GROW_BOTTOM_EDGE = 16;
  public static final int GROW_LEFT_EDGE = 2;
  public static final int GROW_NONE = 1;
  public static final int GROW_RIGHT_EDGE = 4;
  public static final int GROW_TOP_EDGE = 8;
  public static final int MOVE = 32;
  private static final String TAG = "HighlightView";
  private boolean mCircle = false;
  View mContext;
  RectF mCropRect;
  Rect mDrawRect;
  private final Paint mFocusPaint = new Paint();
  boolean mHidden;
  private RectF mImageRect;
  private float mInitialAspectRatio;
  boolean mIsFocused;
  private boolean mMaintainAspectRatio = false;
  Matrix mMatrix;
  private int mMinimumDimension;
  private ModifyMode mMode = ModifyMode.None;
  private final Paint mNoFocusPaint = new Paint();
  private final Paint mOutlinePaint = new Paint();
  private Drawable mResizeDrawableDiagonal;
  private Drawable mResizeDrawableHeight;
  private Drawable mResizeDrawableWidth;

  public HighlightView(View paramView)
  {
    this.mContext = paramView;
  }

  private Rect computeLayout()
  {
    RectF localRectF = new RectF(this.mCropRect.left, this.mCropRect.top, this.mCropRect.right, this.mCropRect.bottom);
    this.mMatrix.mapRect(localRectF);
    return new Rect(Math.round(localRectF.left), Math.round(localRectF.top), Math.round(localRectF.right), Math.round(localRectF.bottom));
  }

  private void init()
  {
    Resources localResources = this.mContext.getResources();
    this.mResizeDrawableWidth = localResources.getDrawable(R.drawable.camera_crop_handle);
    this.mResizeDrawableHeight = localResources.getDrawable(R.drawable.camera_crop_handle);
    this.mResizeDrawableDiagonal = localResources.getDrawable(R.drawable.indicator_autocrop);
  }

  protected void draw(Canvas paramCanvas)
  {
    if (this.mHidden);
    while (true)
    {
      return;
      paramCanvas.save();
      Path localPath = new Path();
      if (!hasFocus())
      {
        this.mOutlinePaint.setColor(-1112874);
        paramCanvas.drawRect(this.mDrawRect, this.mOutlinePaint);
        continue;
      }
      Rect localRect = new Rect();
      this.mContext.getDrawingRect(localRect);
      if (this.mCircle)
      {
        float f1 = this.mDrawRect.width();
        float f2 = this.mDrawRect.height();
        localPath.addCircle(this.mDrawRect.left + f1 / 2.0F, this.mDrawRect.top + f2 / 2.0F, f1 / 2.0F, Path.Direction.CW);
        this.mOutlinePaint.setColor(-14430724);
        label143: paramCanvas.clipPath(localPath, Region.Op.DIFFERENCE);
        if (!hasFocus())
          break label372;
      }
      label372: for (Paint localPaint = this.mFocusPaint; ; localPaint = this.mNoFocusPaint)
      {
        paramCanvas.drawRect(localRect, localPaint);
        paramCanvas.restore();
        paramCanvas.drawPath(localPath, this.mOutlinePaint);
        if (this.mMode == ModifyMode.Move)
          break;
        if (!this.mCircle)
          break label381;
        int i6 = this.mResizeDrawableDiagonal.getIntrinsicWidth();
        int i7 = this.mResizeDrawableDiagonal.getIntrinsicHeight();
        int i8 = (int)Math.round(Math.cos(0.7853981633974483D) * (this.mDrawRect.width() / 2.0D));
        int i9 = i8 + (this.mDrawRect.left + this.mDrawRect.width() / 2) - i6 / 2;
        int i10 = this.mDrawRect.top + this.mDrawRect.height() / 2 - i8 - i7 / 2;
        this.mResizeDrawableDiagonal.setBounds(i9, i10, i9 + this.mResizeDrawableDiagonal.getIntrinsicWidth(), i10 + this.mResizeDrawableDiagonal.getIntrinsicHeight());
        this.mResizeDrawableDiagonal.draw(paramCanvas);
        break;
        localPath.addRect(new RectF(this.mDrawRect), Path.Direction.CW);
        this.mOutlinePaint.setColor(-14430724);
        break label143;
      }
      label381: int i = 1 + this.mDrawRect.left;
      int j = 1 + this.mDrawRect.right;
      int k = 4 + this.mDrawRect.top;
      int m = 3 + this.mDrawRect.bottom;
      int n = this.mResizeDrawableWidth.getIntrinsicWidth() / 2;
      int i1 = this.mResizeDrawableWidth.getIntrinsicHeight() / 2;
      int i2 = this.mResizeDrawableHeight.getIntrinsicHeight() / 2;
      int i3 = this.mResizeDrawableHeight.getIntrinsicWidth() / 2;
      int i4 = this.mDrawRect.left + (this.mDrawRect.right - this.mDrawRect.left) / 2;
      int i5 = this.mDrawRect.top + (this.mDrawRect.bottom - this.mDrawRect.top) / 2;
      this.mResizeDrawableWidth.setBounds(i - n, i5 - i1, i + n, i5 + i1);
      this.mResizeDrawableWidth.draw(paramCanvas);
      this.mResizeDrawableWidth.setBounds(j - n, i5 - i1, j + n, i5 + i1);
      this.mResizeDrawableWidth.draw(paramCanvas);
      this.mResizeDrawableHeight.setBounds(i4 - i3, k - i2, i4 + i3, k + i2);
      this.mResizeDrawableHeight.draw(paramCanvas);
      this.mResizeDrawableHeight.setBounds(i4 - i3, m - i2, i4 + i3, m + i2);
      this.mResizeDrawableHeight.draw(paramCanvas);
    }
  }

  public Rect getCropRect()
  {
    return new Rect((int)this.mCropRect.left, (int)this.mCropRect.top, (int)this.mCropRect.right, (int)this.mCropRect.bottom);
  }

  public int getHit(float paramFloat1, float paramFloat2)
  {
    Rect localRect = computeLayout();
    int i = 1;
    if (this.mCircle)
    {
      float f1 = paramFloat1 - localRect.centerX();
      float f2 = paramFloat2 - localRect.centerY();
      int m = (int)Math.sqrt(f1 * f1 + f2 * f2);
      int n = this.mDrawRect.width() / 2;
      if (Math.abs(m - n) <= 20.0F)
        if (Math.abs(f2) > Math.abs(f1))
          if (f2 < 0.0F)
            i = 8;
      while (true)
      {
        return i;
        i = 16;
        continue;
        if (f1 < 0.0F)
        {
          i = 2;
          continue;
        }
        i = 4;
        continue;
        if (m < n)
        {
          i = 32;
          continue;
        }
        i = 1;
      }
    }
    int j;
    if ((paramFloat2 >= localRect.top - 20.0F) && (paramFloat2 < 20.0F + localRect.bottom))
    {
      j = 1;
      label183: if ((paramFloat1 < localRect.left - 20.0F) || (paramFloat1 >= 20.0F + localRect.right))
        break label358;
    }
    label358: for (int k = 1; ; k = 0)
    {
      if ((Math.abs(localRect.left - paramFloat1) < 20.0F) && (j != 0))
        i |= 2;
      if ((Math.abs(localRect.right - paramFloat1) < 20.0F) && (j != 0))
        i |= 4;
      if ((Math.abs(localRect.top - paramFloat2) < 20.0F) && (k != 0))
        i |= 8;
      if ((Math.abs(localRect.bottom - paramFloat2) < 20.0F) && (k != 0))
        i |= 16;
      if ((i != 1) || (!localRect.contains((int)paramFloat1, (int)paramFloat2)))
        break;
      i = 32;
      break;
      j = 0;
      break label183;
    }
  }

  void growBy(float paramFloat1, float paramFloat2)
  {
    float f1 = 25.0F;
    RectF localRectF;
    if (this.mMaintainAspectRatio)
    {
      if (paramFloat1 != 0.0F)
        paramFloat2 = paramFloat1 / this.mInitialAspectRatio;
    }
    else
    {
      float f2 = paramFloat2;
      localRectF = new RectF(this.mCropRect);
      if ((f2 < 0.0F) && (localRectF.width() + 2.0F * f2 > this.mImageRect.width()))
      {
        f2 = (this.mImageRect.width() - localRectF.width()) / 2.0F;
        if (this.mMaintainAspectRatio)
          paramFloat2 = f2 / this.mInitialAspectRatio;
      }
      if ((paramFloat2 > 0.0F) && (localRectF.height() + 2.0F * paramFloat2 > this.mImageRect.height()))
      {
        paramFloat2 = (this.mImageRect.height() - localRectF.height()) / 2.0F;
        if (this.mMaintainAspectRatio)
          f2 = paramFloat2 * this.mInitialAspectRatio;
      }
      if (((f2 < 0.0F) || (paramFloat2 < 0.0F)) && ((localRectF.width() - 2.0F * f2 < this.mMinimumDimension) || (localRectF.height() - 2.0F * paramFloat2 < this.mMinimumDimension)))
      {
        f2 = (this.mMinimumDimension - localRectF.width()) / 2.0F;
        paramFloat2 = (this.mMinimumDimension - localRectF.height()) / 2.0F;
        localRectF.inset(-f2, -paramFloat2);
      }
      localRectF.inset(-f2, -paramFloat2);
      if (localRectF.width() < f1)
        localRectF.inset(-(f1 - localRectF.width()) / 2.0F, 0.0F);
      if (this.mMaintainAspectRatio)
        f1 /= this.mInitialAspectRatio;
      if (localRectF.height() < f1)
        localRectF.inset(0.0F, -(f1 - localRectF.height()) / 2.0F);
      if (localRectF.left >= this.mImageRect.left)
        break label493;
      localRectF.offset(this.mImageRect.left - localRectF.left, 0.0F);
      label357: if (localRectF.top >= this.mImageRect.top)
        break label532;
      localRectF.offset(0.0F, this.mImageRect.top - localRectF.top);
    }
    while (true)
    {
      if ((localRectF.width() >= this.mMinimumDimension) && (localRectF.height() >= this.mMinimumDimension) && (localRectF.width() <= this.mImageRect.width()) && (localRectF.height() <= this.mImageRect.height()))
      {
        this.mCropRect.set(localRectF);
        this.mDrawRect = computeLayout();
      }
      this.mContext.invalidate();
      return;
      if (paramFloat2 == 0.0F)
        break;
      (paramFloat2 * this.mInitialAspectRatio);
      break;
      label493: if (localRectF.right <= this.mImageRect.right)
        break label357;
      localRectF.offset(-(localRectF.right - this.mImageRect.right), 0.0F);
      break label357;
      label532: if (localRectF.bottom <= this.mImageRect.bottom)
        continue;
      localRectF.offset(0.0F, -(localRectF.bottom - this.mImageRect.bottom));
    }
  }

  void handleMotion(int paramInt, float paramFloat1, float paramFloat2)
  {
    int i = -1;
    Rect localRect = computeLayout();
    if (paramInt == 1);
    while (true)
    {
      return;
      if (paramInt != 32)
        break;
      moveBy(paramFloat1 * (this.mCropRect.width() / localRect.width()), paramFloat2 * (this.mCropRect.height() / localRect.height()));
    }
    if ((paramInt & 0x6) == 0)
      paramFloat1 = 0.0F;
    if ((paramInt & 0x18) == 0)
      paramFloat2 = 0.0F;
    float f1 = paramFloat1 * (this.mCropRect.width() / localRect.width());
    float f2 = paramFloat2 * (this.mCropRect.height() / localRect.height());
    int j;
    label125: float f3;
    if ((paramInt & 0x2) != 0)
    {
      j = i;
      f3 = f1 * j;
      if ((paramInt & 0x8) == 0)
        break label161;
    }
    while (true)
    {
      growBy(f3, f2 * i);
      break;
      j = 1;
      break label125;
      label161: i = 1;
    }
  }

  public boolean hasFocus()
  {
    return this.mIsFocused;
  }

  public void invalidate()
  {
    this.mDrawRect = computeLayout();
  }

  void moveBy(float paramFloat1, float paramFloat2)
  {
    Rect localRect = new Rect(this.mDrawRect);
    this.mCropRect.offset(paramFloat1, paramFloat2);
    this.mCropRect.offset(Math.max(0.0F, this.mImageRect.left - this.mCropRect.left), Math.max(0.0F, this.mImageRect.top - this.mCropRect.top));
    this.mCropRect.offset(Math.min(0.0F, this.mImageRect.right - this.mCropRect.right), Math.min(0.0F, this.mImageRect.bottom - this.mCropRect.bottom));
    this.mDrawRect = computeLayout();
    localRect.union(this.mDrawRect);
    localRect.inset(-10, -10);
    this.mContext.invalidate(localRect);
  }

  public void setFocus(boolean paramBoolean)
  {
    this.mIsFocused = paramBoolean;
  }

  public void setHidden(boolean paramBoolean)
  {
    this.mHidden = paramBoolean;
  }

  public void setMode(ModifyMode paramModifyMode)
  {
    if (paramModifyMode != this.mMode)
    {
      this.mMode = paramModifyMode;
      this.mContext.invalidate();
    }
  }

  public void setup(Matrix paramMatrix, Rect paramRect, RectF paramRectF, boolean paramBoolean1, boolean paramBoolean2, float paramFloat)
  {
    if (paramBoolean1)
      paramBoolean2 = true;
    this.mMatrix = new Matrix(paramMatrix);
    this.mCropRect = paramRectF;
    this.mImageRect = new RectF(paramRect);
    this.mMaintainAspectRatio = paramBoolean2;
    this.mCircle = paramBoolean1;
    this.mInitialAspectRatio = (this.mCropRect.width() / this.mCropRect.height());
    if (this.mImageRect.width() < this.mImageRect.height());
    for (this.mMinimumDimension = (int)(paramFloat * (int)this.mImageRect.width()); ; this.mMinimumDimension = (int)(paramFloat * (int)this.mImageRect.height()))
    {
      this.mDrawRect = computeLayout();
      this.mFocusPaint.setARGB(125, 50, 50, 50);
      this.mNoFocusPaint.setARGB(125, 50, 50, 50);
      this.mOutlinePaint.setStrokeWidth(3.0F);
      this.mOutlinePaint.setStyle(Paint.Style.STROKE);
      this.mOutlinePaint.setAntiAlias(true);
      this.mMode = ModifyMode.None;
      init();
      return;
    }
  }

  static enum ModifyMode
  {
    static
    {
      Move = new ModifyMode("Move", 1);
      Grow = new ModifyMode("Grow", 2);
      ModifyMode[] arrayOfModifyMode = new ModifyMode[3];
      arrayOfModifyMode[0] = None;
      arrayOfModifyMode[1] = Move;
      arrayOfModifyMode[2] = Grow;
      $VALUES = arrayOfModifyMode;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.HighlightView
 * JD-Core Version:    0.6.0
 */