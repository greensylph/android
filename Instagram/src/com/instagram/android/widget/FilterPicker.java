package com.instagram.android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import com.instagram.android.gl.NativeFilter;
import com.instagram.android.service.AppContext;
import com.instagram.util.FilterTileUtil;

public class FilterPicker extends HorizontalScrollView
  implements IgRadioGroup.OnCheckedChangeListener
{
  public static final String TAG = "FilterPicker";
  private NativeFilter[] mFilters;
  private OnFilterChangedListener mListener;
  private NativeFilter mSelectedFilter;

  public FilterPicker(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  private void addFilterTiles(IgRadioGroup paramIgRadioGroup)
  {
    NativeFilter[] arrayOfNativeFilter = this.mFilters;
    int i = arrayOfNativeFilter.length;
    for (int j = 0; j < i; j++)
    {
      FilterTile localFilterTile = FilterTile.fromFilter(arrayOfNativeFilter[j]);
      if (localFilterTile == null)
        continue;
      paramIgRadioGroup.addView(localFilterTile);
    }
  }

  private void init()
  {
    this.mFilters = FilterTileUtil.getFilters();
    this.mSelectedFilter = this.mFilters[0];
    setBackgroundResource(2130837608);
    setHorizontalFadingEdgeEnabled(false);
    setHorizontalScrollBarEnabled(false);
    IgRadioGroup localIgRadioGroup = new IgRadioGroup(getContext());
    localIgRadioGroup.setOrientation(0);
    localIgRadioGroup.setOnCheckedChangeListener(this);
    localIgRadioGroup.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
    addFilterTiles(localIgRadioGroup);
    addView(localIgRadioGroup);
    ((FilterTile)localIgRadioGroup.findViewWithTag(this.mSelectedFilter)).setChecked(true);
  }

  public NativeFilter getSelectedFilter()
  {
    return this.mSelectedFilter;
  }

  public void onCheckedChanged(IgRadioGroup paramIgRadioGroup, int paramInt)
  {
    if (this.mListener != null)
    {
      this.mSelectedFilter = ((NativeFilter)((FilterTile)paramIgRadioGroup.findViewById(paramIgRadioGroup.getCheckedRadioButtonId())).getTag());
      this.mListener.onFilterChanged(this.mSelectedFilter);
    }
  }

  public void setOnFilterChangedListener(OnFilterChangedListener paramOnFilterChangedListener)
  {
    this.mListener = paramOnFilterChangedListener;
  }

  private static class FilterTile extends CompoundButton
  {
    public FilterTile(Context paramContext)
    {
      super();
      setOnClickListener(new FilterPicker.FilterTile.1(this));
    }

    public static FilterTile fromFilter(NativeFilter paramNativeFilter)
    {
      Context localContext = AppContext.getContext();
      Resources localResources = localContext.getResources();
      FilterTile localFilterTile = null;
      int i = FilterTileUtil.getResId(localContext, paramNativeFilter, "default");
      int j = FilterTileUtil.getResId(localContext, paramNativeFilter, "selected");
      if ((i != 0) && (j != 0))
      {
        localFilterTile = new FilterTile(localContext);
        localFilterTile.setTag(paramNativeFilter);
        StateListDrawable localStateListDrawable = new StateListDrawable();
        int[] arrayOfInt = new int[1];
        arrayOfInt[0] = 16842912;
        localStateListDrawable.addState(arrayOfInt, localResources.getDrawable(j));
        localStateListDrawable.addState(StateSet.WILD_CARD, localResources.getDrawable(i));
        localFilterTile.setButtonDrawable(17170445);
        localFilterTile.setBackgroundDrawable(localStateListDrawable);
        localFilterTile.setDrawingCacheEnabled(false);
      }
      return localFilterTile;
    }

    public void toggle()
    {
      if (!isChecked())
        super.toggle();
    }
  }

  public static abstract interface OnFilterChangedListener
  {
    public abstract void onFilterChanged(NativeFilter paramNativeFilter);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.FilterPicker
 * JD-Core Version:    0.6.0
 */