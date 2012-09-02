package com.instagram.android.adapter.row;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.instagram.android.model.User;
import com.instagram.android.model.User.UserAction;
import com.instagram.android.service.AppContext;
import com.instagram.api.request.FollowStatusUpdateRequest;

public class FriendRequestHeaderRowAdapter
{
  public static void bindView(Holder paramHolder, User paramUser, Context paramContext, LoaderManager paramLoaderManager)
  {
    View localView = paramHolder.self;
    if (paramUser.isIncomingRequestPending());
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      TextView localTextView = paramHolder.textview;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramUser.getUsername();
      localTextView.setText(paramContext.getString(2131230979, arrayOfObject));
      paramHolder.approve.setOnClickListener(new View.OnClickListener(paramLoaderManager, paramUser, paramHolder)
      {
        public void onClick(View paramView)
        {
          new FollowStatusUpdateRequest(AppContext.getContext(), this.val$loaderManager, User.UserAction.UserActionApprove, this.val$user).perform();
          FriendRequestHeaderRowAdapter.Holder.access$300(this.val$holder).setVisibility(8);
        }
      });
      paramHolder.ignore.setOnClickListener(new View.OnClickListener(paramLoaderManager, paramUser, paramHolder)
      {
        public void onClick(View paramView)
        {
          new FollowStatusUpdateRequest(AppContext.getContext(), this.val$loaderManager, User.UserAction.UserActionIgnore, this.val$user).perform();
          FriendRequestHeaderRowAdapter.Holder.access$300(this.val$holder).setVisibility(8);
        }
      });
      return;
    }
  }

  public static View createHolderForView(View paramView)
  {
    Holder localHolder = new Holder();
    Holder.access$002(localHolder, (TextView)paramView.findViewById(2131493035));
    Holder.access$102(localHolder, (Button)paramView.findViewById(2131493036));
    Holder.access$202(localHolder, (Button)paramView.findViewById(2131493037));
    Holder.access$302(localHolder, paramView);
    paramView.setTag(localHolder);
    return paramView;
  }

  public static class Holder
  {
    private Button approve;
    private Button ignore;
    private View self;
    private TextView textview;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.FriendRequestHeaderRowAdapter
 * JD-Core Version:    0.6.0
 */