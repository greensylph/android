package com.instagram.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Iterator;

public class SimpleProxyView extends LinearLayout
{
  private View.OnClickListener mListener;
  private ArrayList<View.OnClickListener> mListeners = new ArrayList();
  private boolean mProxy = true;

  public SimpleProxyView(Context paramContext)
  {
    super(paramContext);
    super.setOnClickListener(new ProxyOnClickListener());
  }

  public SimpleProxyView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    super.setOnClickListener(new ProxyOnClickListener());
  }

  public SimpleProxyView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    super.setOnClickListener(new ProxyOnClickListener());
  }

  public void addAdditionalOnClickListeners(View.OnClickListener paramOnClickListener)
  {
    this.mListeners.add(paramOnClickListener);
  }

  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mListener = paramOnClickListener;
  }

  public void setProxyToOnClickListener(boolean paramBoolean)
  {
    this.mProxy = paramBoolean;
  }

  class ProxyOnClickListener
    implements View.OnClickListener
  {
    ProxyOnClickListener()
    {
    }

    public void onClick(View paramView)
    {
      Iterator localIterator = SimpleProxyView.this.mListeners.iterator();
      while (localIterator.hasNext())
        ((View.OnClickListener)localIterator.next()).onClick(paramView);
      if ((SimpleProxyView.this.mListener != null) && (SimpleProxyView.this.mProxy))
        SimpleProxyView.this.mListener.onClick(paramView);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.SimpleProxyView
 * JD-Core Version:    0.6.0
 */