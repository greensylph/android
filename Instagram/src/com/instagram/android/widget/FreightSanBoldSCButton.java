package com.instagram.android.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import com.instagram.android.text.method.AllCapsTransformationMethod;

public class FreightSanBoldSCButton extends Button
{
  private static AllCapsTransformationMethod mMethod;
  private static Typeface mTypeface;

  public FreightSanBoldSCButton(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public FreightSanBoldSCButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public FreightSanBoldSCButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  private AllCapsTransformationMethod getAllCapsTransformation()
  {
    if (mMethod == null)
      mMethod = new AllCapsTransformationMethod(getContext());
    return mMethod;
  }

  private Typeface getCustomTypeface()
  {
    if (mTypeface == null)
      mTypeface = Typeface.createFromAsset(getContext().getAssets(), "FreigSanBolSC.ttf");
    return mTypeface;
  }

  private void init()
  {
    setTypeface(getCustomTypeface());
    setTransformationMethod(getAllCapsTransformation());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.FreightSanBoldSCButton
 * JD-Core Version:    0.6.0
 */