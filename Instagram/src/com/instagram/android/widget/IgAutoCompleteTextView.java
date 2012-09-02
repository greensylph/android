package com.instagram.android.widget;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class IgAutoCompleteTextView extends AutoCompleteTextView
{
  public IgAutoCompleteTextView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public IgAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public IgAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  private int getReplaceIndex(Editable paramEditable)
  {
    int i = -1 + paramEditable.length();
    if ((i >= 0) && (!Character.isWhitespace(paramEditable.charAt(i))))
      if (((paramEditable.charAt(i) != '@') && (paramEditable.charAt(i) != '#')) || ((i != 0) && (!Character.isWhitespace(paramEditable.charAt(i - 1)))));
    while (true)
    {
      return i;
      i--;
      break;
      i = 0;
    }
  }

  private void init()
  {
    if ((Build.MODEL.equalsIgnoreCase("DROID3")) || (Build.MODEL.equalsIgnoreCase("DROID4")) || (Build.MODEL.equalsIgnoreCase("DROID BIONIC")));
    while (true)
    {
      return;
      setRawInputType(0xFFFEFFFF & getInputType());
    }
  }

  public boolean enoughToFilter()
  {
    int i = 0;
    Editable localEditable = getText();
    if (localEditable.length() <= 1);
    label119: 
    while (true)
    {
      return i;
      if (Character.isWhitespace(localEditable.charAt(-1 + localEditable.length())))
        continue;
      for (int j = -2 + localEditable.length(); ; j--)
      {
        if ((j < 0) || (Character.isWhitespace(localEditable.charAt(j))))
          break label119;
        if (((localEditable.charAt(j) != '@') && (localEditable.charAt(j) != '#')) || ((j != 0) && (!Character.isWhitespace(localEditable.charAt(j - 1)))))
          continue;
        i = 1;
        break;
      }
    }
  }

  protected void performFiltering(CharSequence paramCharSequence, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = -1 + paramCharSequence.length(); (i >= 0) && (paramCharSequence.charAt(i) != '@') && (paramCharSequence.charAt(i) != '#'); i--)
      localStringBuilder.insert(0, paramCharSequence.charAt(i));
    if (i > -1)
      localStringBuilder.insert(0, paramCharSequence.charAt(i));
    super.performFiltering(localStringBuilder.toString(), paramInt);
  }

  protected void replaceText(CharSequence paramCharSequence)
  {
    Editable localEditable1 = getText();
    StringBuilder localStringBuilder = new StringBuilder(localEditable1.length());
    setText(localStringBuilder.append(localEditable1).delete(getReplaceIndex(localEditable1), localStringBuilder.length()).append(paramCharSequence).append(' '));
    Editable localEditable2 = getText();
    Selection.setSelection(localEditable2, localEditable2.length());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.IgAutoCompleteTextView
 * JD-Core Version:    0.6.0
 */