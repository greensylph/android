package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import com.instagram.android.model.User;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.RequestParams;
import java.util.Iterator;

public class FollowAllRequest extends FetchBulkFollowingStatusRequest
{
  public FollowAllRequest(Context paramContext, LoaderManager paramLoaderManager, AbstractStreamingApiCallbacks<Object> paramAbstractStreamingApiCallbacks)
  {
    super(paramContext, paramLoaderManager, 0, paramAbstractStreamingApiCallbacks);
  }

  protected String getPath()
  {
    return "friendships/create_many/";
  }

  public void performForUsers(Iterable<User> paramIterable)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append(((User)localIterator.next()).getId());
      localStringBuilder.append(",");
    }
    if (localStringBuilder.length() > 0)
      localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
    getParams().put("user_ids", localStringBuilder.toString());
    perform();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.FollowAllRequest
 * JD-Core Version:    0.6.0
 */