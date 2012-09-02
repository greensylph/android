package com.instagram.twitter;

import android.content.Context;
import android.os.AsyncTask;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.BasicAuthorization;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterXAuth
{
  public static void asyncConnect(Context paramContext, String paramString1, String paramString2, Listener paramListener)
  {
    new AsyncTask(paramString1, paramString2, paramContext, paramListener)
    {
      protected AccessToken doInBackground(Void[] paramArrayOfVoid)
      {
        Twitter localTwitter = new TwitterFactory(new ConfigurationBuilder().setOAuthConsumerKey(TwitterConstants.getConsumerKey()).setOAuthConsumerSecret(TwitterConstants.getConsumerSecret()).build()).getInstance(new BasicAuthorization(this.val$username, this.val$password));
        Object localObject = null;
        try
        {
          AccessToken localAccessToken = localTwitter.getOAuthAccessToken();
          localObject = localAccessToken;
          return localObject;
        }
        catch (TwitterException localTwitterException)
        {
          while (true)
            localTwitterException.printStackTrace();
        }
      }

      protected void onPostExecute(AccessToken paramAccessToken)
      {
        if (paramAccessToken != null);
        for (int i = 1; ; i = 0)
        {
          TwitterAccount localTwitterAccount = null;
          if (i != 0)
            localTwitterAccount = TwitterAccount.store(this.val$context, paramAccessToken.getToken(), paramAccessToken.getTokenSecret(), paramAccessToken.getScreenName());
          this.val$listener.onComplete(localTwitterAccount);
          return;
        }
      }
    }
    .execute(new Void[0]);
  }

  public static abstract interface Listener
  {
    public abstract void onComplete(TwitterAccount paramTwitterAccount);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.twitter.TwitterXAuth
 * JD-Core Version:    0.6.0
 */