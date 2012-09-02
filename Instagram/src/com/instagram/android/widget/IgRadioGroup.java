package com.instagram.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup.LayoutParams;

public class IgRadioGroup extends LinearLayout
{
  private int mCheckedId = -1;
  private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
  private OnCheckedChangeListener mOnCheckedChangeListener;
  private PassThroughHierarchyChangeListener mPassThroughListener;
  private boolean mProtectFromCheckedChange = false;

  public IgRadioGroup(Context paramContext)
  {
    super(paramContext);
    setOrientation(1);
    init();
  }

  private void init()
  {
    this.mChildOnCheckedChangeListener = new CheckedStateTracker(null);
    this.mPassThroughListener = new PassThroughHierarchyChangeListener(null);
    super.setOnHierarchyChangeListener(this.mPassThroughListener);
  }

  private void setCheckedId(int paramInt)
  {
    this.mCheckedId = paramInt;
    if (this.mOnCheckedChangeListener != null)
      this.mOnCheckedChangeListener.onCheckedChanged(this, this.mCheckedId);
  }

  private void setCheckedStateForView(int paramInt, boolean paramBoolean)
  {
    View localView = findViewById(paramInt);
    if ((localView != null) && ((localView instanceof CompoundButton)))
      ((CompoundButton)localView).setChecked(paramBoolean);
  }

  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramView instanceof CompoundButton))
    {
      CompoundButton localCompoundButton = (CompoundButton)paramView;
      if (localCompoundButton.isChecked())
      {
        this.mProtectFromCheckedChange = true;
        if (this.mCheckedId != -1)
          setCheckedStateForView(this.mCheckedId, false);
        this.mProtectFromCheckedChange = false;
        setCheckedId(localCompoundButton.getId());
      }
    }
    super.addView(paramView, paramInt, paramLayoutParams);
  }

  public void check(int paramInt)
  {
    if ((paramInt != -1) && (paramInt == this.mCheckedId));
    while (true)
    {
      return;
      if (this.mCheckedId != -1)
        setCheckedStateForView(this.mCheckedId, false);
      if (paramInt != -1)
        setCheckedStateForView(paramInt, true);
      setCheckedId(paramInt);
    }
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof RadioGroup.LayoutParams;
  }

  public void clearCheck()
  {
    check(-1);
  }

  protected LinearLayout.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2);
  }

  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }

  public int getCheckedRadioButtonId()
  {
    return this.mCheckedId;
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
    if (this.mCheckedId != -1)
    {
      this.mProtectFromCheckedChange = true;
      setCheckedStateForView(this.mCheckedId, true);
      this.mProtectFromCheckedChange = false;
      setCheckedId(this.mCheckedId);
    }
  }

  public void setOnCheckedChangeListener(OnCheckedChangeListener paramOnCheckedChangeListener)
  {
    this.mOnCheckedChangeListener = paramOnCheckedChangeListener;
  }

  public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener paramOnHierarchyChangeListener)
  {
    PassThroughHierarchyChangeListener.access$202(this.mPassThroughListener, paramOnHierarchyChangeListener);
  }

  private class CheckedStateTracker
    implements CompoundButton.OnCheckedChangeListener
  {
    private CheckedStateTracker()
    {
    }

    public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
    {
      if (IgRadioGroup.this.mProtectFromCheckedChange);
      while (true)
      {
        return;
        IgRadioGroup.access$302(IgRadioGroup.this, true);
        if (IgRadioGroup.this.mCheckedId != -1)
          IgRadioGroup.this.setCheckedStateForView(IgRadioGroup.this.mCheckedId, false);
        IgRadioGroup.access$302(IgRadioGroup.this, false);
        int i = paramCompoundButton.getId();
        IgRadioGroup.this.setCheckedId(i);
      }
    }
  }

  public static class LayoutParams extends LinearLayout.LayoutParams
  {
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }

    public LayoutParams(int paramInt1, int paramInt2, float paramFloat)
    {
      super(paramInt2, paramFloat);
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }

    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }

    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }

    protected void setBaseAttributes(TypedArray paramTypedArray, int paramInt1, int paramInt2)
    {
      if (paramTypedArray.hasValue(paramInt1))
      {
        this.width = paramTypedArray.getLayoutDimension(paramInt1, "layout_width");
        if (!paramTypedArray.hasValue(paramInt2))
          break label48;
      }
      label48: for (this.height = paramTypedArray.getLayoutDimension(paramInt2, "layout_height"); ; this.height = -2)
      {
        return;
        this.width = -2;
        break;
      }
    }
  }

  public static abstract interface OnCheckedChangeListener
  {
    public abstract void onCheckedChanged(IgRadioGroup paramIgRadioGroup, int paramInt);
  }

  private class PassThroughHierarchyChangeListener
    implements ViewGroup.OnHierarchyChangeListener
  {
    private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

    private PassThroughHierarchyChangeListener()
    {
    }

    public void onChildViewAdded(View paramView1, View paramView2)
    {
      if ((paramView1 == IgRadioGroup.this) && ((paramView2 instanceof CompoundButton)))
      {
        if (paramView2.getId() == -1)
          paramView2.setId(paramView2.hashCode());
        ((CompoundButton)paramView2).setOnCheckedChangeListener(IgRadioGroup.this.mChildOnCheckedChangeListener);
      }
      if (this.mOnHierarchyChangeListener != null)
        this.mOnHierarchyChangeListener.onChildViewAdded(paramView1, paramView2);
    }

    public void onChildViewRemoved(View paramView1, View paramView2)
    {
      if ((paramView1 == IgRadioGroup.this) && ((paramView2 instanceof CompoundButton)))
        ((CompoundButton)paramView2).setOnCheckedChangeListener(null);
      if (this.mOnHierarchyChangeListener != null)
        this.mOnHierarchyChangeListener.onChildViewRemoved(paramView1, paramView2);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.IgRadioGroup
 * JD-Core Version:    0.6.0
 */