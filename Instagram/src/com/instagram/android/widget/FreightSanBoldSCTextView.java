package com.instagram.android.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.instagram.android.text.method.AllCapsTransformationMethod;

public class FreightSanBoldSCTextView extends TextView
{
  private static AllCapsTransformationMethod mMethod;
  private static Typeface mTypeface;

  public FreightSanBoldSCTextView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public FreightSanBoldSCTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public FreightSanBoldSCTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
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
 * Qualified Name:     com.instagram.android.widget.FreightSanBoldSCTextView
 * JD-Core Version:    0.6.0
 */