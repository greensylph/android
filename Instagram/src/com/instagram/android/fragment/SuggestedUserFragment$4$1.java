package com.instagram.android.fragment;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.instagram.android.activity.MainTabActivity;

class SuggestedUserFragment$4$1
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    Intent localIntent = new Intent(this.this$1.this$0.getActivity(), MainTabActivity.class);
    localIntent.setFlags(67108864);
    this.this$1.this$0.startActivity(localIntent);
    this.this$1.this$0.getActivity().finish();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SuggestedUserFragment.4.1
 * JD-Core Version:    0.6.0
 */