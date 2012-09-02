package com.instagram.twitter;

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService
{
  private static final String TAG = "TwitterService";

  public static void followInstagram(TwitterAccount paramTwitterAccount)
  {
    AsyncTwitter localAsyncTwitter = new AsyncTwitterFactory(getConfiguration(paramTwitterAccount)).getInstance();
    localAsyncTwitter.addListener(new TwitterService.1());
    localAsyncTwitter.createFriendship("instagram");
  }

  private static Configuration getConfiguration(TwitterAccount paramTwitterAccount)
  {
    return new ConfigurationBuilder().setOAuthConsumerKey(TwitterConstants.getConsumerKey()).setOAuthConsumerSecret(TwitterConstants.getConsumerSecret()).setOAuthAccessToken(paramTwitterAccount.getToken()).setOAuthAccessTokenSecret(paramTwitterAccount.getSecret()).build();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.twitter.TwitterService
 * JD-Core Version:    0.6.0
 */