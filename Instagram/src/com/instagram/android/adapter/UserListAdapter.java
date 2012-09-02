package com.instagram.android.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.instagram.android.adapter.row.UserRowAdapter;
import com.instagram.android.adapter.row.UserRowAdapter.Holder;
import com.instagram.android.model.User;
import com.instagram.android.service.AppContext;
import java.util.Iterator;
import java.util.List;

public class UserListAdapter extends NoResultsEnhancedAdapter
{
  private LoaderManager mLoaderManager;
  private ReceivedStatusUpdateHandler mRefreshStatusHandler = new ReceivedStatusUpdateHandler(null);
  private boolean mShowFollowButtons;
  private BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      UserListAdapter.this.mRefreshStatusHandler.removeMessages(0);
      UserListAdapter.this.mRefreshStatusHandler.sendEmptyMessageDelayed(0, 300L);
    }
  };

  public UserListAdapter(Context paramContext)
  {
    super(paramContext);
    registerDataSetObserver(new DataSetObserver()
    {
      private void registerUser(User paramUser)
      {
        LocalBroadcastManager.getInstance(AppContext.getContext()).registerReceiver(UserListAdapter.this.receiver, new IntentFilter(User.getUserFollowUpdateBroadcastId(paramUser.getId())));
      }

      public void onChanged()
      {
        LocalBroadcastManager.getInstance(AppContext.getContext()).unregisterReceiver(UserListAdapter.this.receiver);
        Iterator localIterator = UserListAdapter.this.getUsers().iterator();
        while (localIterator.hasNext())
          registerUser((User)(User)localIterator.next());
      }

      public void onInvalidated()
      {
      }
    });
  }

  protected void bindView(View paramView, Context paramContext, int paramInt)
  {
    UserRowAdapter.bindView((UserRowAdapter.Holder)paramView.getTag(), (User)getItem(paramInt), this.mShowFollowButtons, this.mLoaderManager);
  }

  protected View getNoResultsView(View paramView, ViewGroup paramViewGroup)
  {
    View localView = LayoutInflater.from(getContext()).inflate(2130903112, null);
    ((TextView)localView.findViewById(2131493056)).setText(2131231059);
    return localView;
  }

  public List<User> getUsers()
  {
    return this.mObjects;
  }

  protected View newView(Context paramContext, int paramInt, ViewGroup paramViewGroup)
  {
    return UserRowAdapter.newView(paramContext);
  }

  public void onDestroy()
  {
    LocalBroadcastManager.getInstance(AppContext.getContext()).unregisterReceiver(this.receiver);
  }

  public void setLoaderManager(LoaderManager paramLoaderManager)
  {
    this.mLoaderManager = paramLoaderManager;
  }

  public void setShowFollowButtons(boolean paramBoolean)
  {
    this.mShowFollowButtons = paramBoolean;
  }

  private class ReceivedStatusUpdateHandler extends Handler
  {
    private ReceivedStatusUpdateHandler()
    {
    }

    public void handleMessage(Message paramMessage)
    {
      UserListAdapter.this.notifyDataSetChanged();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.UserListAdapter
 * JD-Core Version:    0.6.0
 */