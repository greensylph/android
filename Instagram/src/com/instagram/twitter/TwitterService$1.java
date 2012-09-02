package com.instagram.twitter;

import com.instagram.android.Log;
import twitter4j.TwitterAdapter;
import twitter4j.User;

class TwitterService$1 extends TwitterAdapter
{
  public void createdFriendship(User paramUser)
  {
    Log.d("TwitterService", "Created friendship with: " + paramUser.getName());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.twitter.TwitterService.1
 * JD-Core Version:    0.6.0
 */