package com.instagram.android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchEditText extends EditText
  implements TextView.OnEditorActionListener, TextWatcher
{
  private Drawable mClearButton;
  private boolean mClearButtonEnabled = true;
  private boolean mClearButtonShown;
  private SearchEditTextListener mListener;

  public SearchEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  private void init()
  {
    addTextChangedListener(this);
    setOnEditorActionListener(this);
    this.mClearButton = getCompoundDrawables()[2];
    setCompoundDrawables(null, null, null, null);
    if (!isTextSelectionAllowed())
      setOnLongClickListener(new View.OnLongClickListener()
      {
        public boolean onLongClick(View paramView)
        {
          return true;
        }
      });
  }

  private boolean isEnterActionKey(int paramInt)
  {
    if ((paramInt == 23) || (paramInt == 66) || (paramInt == 160));
    for (int i = 1; ; i = 0)
      return i;
  }

  private boolean isTextSelectionAllowed()
  {
    int i = 1;
    try
    {
      Class.forName("com.htc.textselection.HtcTextSelectionManager");
      i = 0;
      label10: return i;
    }
    catch (Exception localException)
    {
      break label10;
    }
  }

  private String trim(Editable paramEditable)
  {
    return paramEditable.toString().trim();
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void clearFocus()
  {
    setFocusableInTouchMode(false);
    super.clearFocus();
    setFocusableInTouchMode(true);
  }

  public String getSearchString()
  {
    return trim(getText());
  }

  public void hideSoftKeyboard()
  {
    ((InputMethodManager)getContext().getSystemService("input_method")).hideSoftInputFromWindow(getWindowToken(), 0);
  }

  public boolean isClearButtonEnabled()
  {
    return this.mClearButtonEnabled;
  }

  public boolean isTextEmpty()
  {
    return TextUtils.isEmpty(getSearchString());
  }

  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 6) || (paramInt == 3))
    {
      hideSoftKeyboard();
      if (this.mListener != null)
        this.mListener.onSearchSubmitted(this, getSearchString());
    }
    for (int i = 1; ; i = 0)
      return i;
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((!TextUtils.isEmpty(getText())) && (paramKeyEvent.getAction() == 0) && (isEnterActionKey(paramInt)))
    {
      hideSoftKeyboard();
      if (this.mListener != null)
        this.mListener.onSearchSubmitted(this, getSearchString());
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public boolean onKeyPreIme(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      clearFocus();
      hideSoftKeyboard();
    }
    for (boolean bool = true; ; bool = super.onKeyPreIme(paramInt, paramKeyEvent))
      return bool;
  }

  public boolean onPreDraw()
  {
    boolean bool1 = false;
    boolean bool2 = isTextEmpty();
    boolean bool3;
    if (this.mClearButtonShown == bool2)
      if (!bool2)
      {
        bool3 = true;
        this.mClearButtonShown = bool3;
        if ((!this.mClearButtonShown) || (!this.mClearButtonEnabled))
          break label58;
        setCompoundDrawables(null, null, this.mClearButton, null);
      }
    while (true)
    {
      return bool1;
      bool3 = false;
      break;
      label58: setCompoundDrawables(null, null, null, null);
      continue;
      bool1 = super.onPreDraw();
    }
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mListener != null)
      this.mListener.onSearchTextChanged(this, paramCharSequence, paramInt1, paramInt2, paramInt3);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    if ((this.mClearButtonShown) && (paramMotionEvent.getAction() == i) && (paramMotionEvent.getX() > getWidth() - getPaddingRight() - this.mClearButton.getIntrinsicWidth()))
    {
      setText("");
      requestFocus();
      showSoftKeyboard();
    }
    if (!isTextSelectionAllowed())
    {
      requestFocus();
      showSoftKeyboard();
    }
    while (true)
    {
      return i;
      boolean bool = super.onTouchEvent(paramMotionEvent);
    }
  }

  public void setClearButtonEnabled(boolean paramBoolean)
  {
    this.mClearButtonEnabled = paramBoolean;
  }

  public void setOnFilterTextListener(SearchEditTextListener paramSearchEditTextListener)
  {
    this.mListener = paramSearchEditTextListener;
  }

  public void showSoftKeyboard()
  {
    ((InputMethodManager)getContext().getSystemService("input_method")).toggleSoftInput(2, 1);
  }

  public static abstract class SearchEditTextListener
  {
    public abstract void onSearchSubmitted(SearchEditText paramSearchEditText, String paramString);

    public void onSearchTextChanged(SearchEditText paramSearchEditText, CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.SearchEditText
 * JD-Core Version:    0.6.0
 */