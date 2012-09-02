package com.instagram.android.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.instagram.android.InstagramAutoUpdater;
import com.instagram.android.activity.SimpleWebViewActivity;
import com.instagram.android.model.SimpleMenuItem;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.api.request.UpdateAvatarHelper;
import com.instagram.api.request.WebUrlHelper;
import com.instagram.util.FragmentUtil;

class UserOptionsFragment$2
  implements AdapterView.OnItemClickListener
{
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    switch (((SimpleMenuItem)paramAdapterView.getItemAtPosition(paramInt)).getStringResId())
    {
    default:
    case 2131230963:
    case 2131230971:
    case 2131230967:
    case 2131230968:
    case 2131230865:
    case 2131230866:
    case 2131230970:
    case 2131231017:
    case 2131231056:
    case 2131230973:
    case 2131230969:
    }
    while (true)
    {
      return;
      FragmentUtil.navigateTo(this.this$0.getFragmentManager(), new FindFriendsFragment(), null);
      continue;
      new IgDialogBuilder(this.this$0.getActivity()).setMessage(this.this$0.getString(2131231016)).setPositiveButton(2131230971, new UserOptionsFragment.2.2(this)).setNeutralButton(2131230921, new UserOptionsFragment.2.1(this)).create().show();
      continue;
      FragmentUtil.navigateTo(this.this$0.getFragmentManager(), new LikedFeedFragment(), null);
      continue;
      SimpleWebViewActivity.show(this.this$0.getActivity(), WebUrlHelper.expandPath("/accounts/edit_inapp/", true), true, this.this$0.getString(2131230968));
      UserDetailFragment.setCurrentUserNeedsReload();
      continue;
      new UserOptionsFragment.2.3(this, true).checkForUpdates();
      continue;
      String str = this.this$0.getActivity().getPackageName();
      try
      {
        str = this.this$0.getActivity().getPackageManager().getPackageInfo(this.this$0.getActivity().getPackageName(), 0).versionName;
        label300: StringBuffer localStringBuffer = new StringBuffer();
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = str;
        localStringBuffer.append(String.format("\n\n\nInstagram version: %s", arrayOfObject1));
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Build.VERSION.RELEASE;
        localStringBuffer.append(String.format("\nAndroid version: %s", arrayOfObject2));
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = Build.BRAND;
        arrayOfObject3[1] = Build.DEVICE;
        localStringBuffer.append(String.format("\nDevice: %s %s", arrayOfObject3));
        Intent localIntent = new Intent("android.intent.action.SEND");
        localIntent.setType("plain/text");
        String[] arrayOfString = new String[1];
        arrayOfString[0] = "android-beta@instagram.com";
        localIntent.putExtra("android.intent.extra.EMAIL", arrayOfString);
        localIntent.putExtra("android.intent.extra.SUBJECT", "Instagram Android Feedback");
        localIntent.putExtra("android.intent.extra.TEXT", localStringBuffer.toString());
        this.this$0.startActivity(Intent.createChooser(localIntent, "Send your feedback with:"));
        continue;
        UserOptionsFragment.access$000(this.this$0).showDialog();
        continue;
        FragmentUtil.navigateTo(this.this$0.getFragmentManager(), new PhotoPrivacyFragment(), null);
        continue;
        FragmentUtil.navigateTo(this.this$0.getFragmentManager(), new CameraSettingsFragment(), null);
        continue;
        FragmentUtil.navigateTo(this.this$0.getFragmentManager(), new AboutFragment(), null);
        continue;
        FragmentUtil.navigateTo(this.this$0.getFragmentManager(), new EditSharingSettingsFragment(), null);
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        break label300;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.UserOptionsFragment.2
 * JD-Core Version:    0.6.0
 */