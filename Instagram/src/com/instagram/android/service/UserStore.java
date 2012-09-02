package com.instagram.android.service;

import android.content.Context;
import com.instagram.android.Log;
import com.instagram.android.model.User;
import java.util.concurrent.ConcurrentHashMap;

public class UserStore extends ConcurrentHashMap<String, User>
{
  public static final String TAG = "UserStore";
  public static final String USER_STORE_SERVICE = "com.instagram.android.service.userstore";
  private static boolean sAllowCurrentUserUpdate;
  private ConcurrentHashMap<String, User> mNameIndexStore = new ConcurrentHashMap();

  public static void allowCurrentUserUpdate()
  {
    sAllowCurrentUserUpdate = true;
  }

  public static UserStore getInstance(Context paramContext)
  {
    UserStore localUserStore = (UserStore)paramContext.getSystemService("com.instagram.android.service.userstore");
    if (localUserStore == null)
      localUserStore = (UserStore)paramContext.getApplicationContext().getSystemService("com.instagram.android.service.userstore");
    if (localUserStore == null)
      throw new IllegalStateException("UserStore not available");
    return localUserStore;
  }

  private boolean isCurrentUser(User paramUser)
  {
    return paramUser.equals(AuthHelper.getInstance().getCurrentUser());
  }

  private static boolean takeAllowCurrentUserUpdate()
  {
    boolean bool = sAllowCurrentUserUpdate;
    sAllowCurrentUserUpdate = false;
    return bool;
  }

  public User findByUserName(String paramString)
  {
    return (User)this.mNameIndexStore.get(paramString);
  }

  public User put(String paramString, User paramUser)
  {
    User localUser = (User)super.put(paramString, paramUser);
    if (isCurrentUser(paramUser))
      takeAllowCurrentUserUpdate();
    if ((paramUser != null) && (paramUser.getUsername() != null))
      this.mNameIndexStore.put(paramUser.getUsername(), paramUser);
    return localUser;
  }

  public User update(String paramString, User paramUser)
  {
    User localUser = (User)get(paramUser.getId());
    if (isCurrentUser(paramUser))
    {
      if (takeAllowCurrentUserUpdate())
        Log.d("UserStore", "Allowing update of current user");
    }
    else
    {
      if (localUser == null)
        break label45;
      localUser.updateFields(paramUser);
    }
    while (true)
    {
      return localUser;
      label45: put(paramUser.getId(), paramUser);
      localUser = paramUser;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.UserStore
 * JD-Core Version:    0.6.0
 */