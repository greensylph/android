package com.instagram.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import com.instagram.android.Preferences;
import com.instagram.android.loader.AsyncTaskDataLoader;
import com.instagram.android.service.ClickManager;
import com.instagram.api.request.AbstractRequest;
import com.instagram.api.request.LogoutTask;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class BaseApiLoaderCallbacks<T>
  implements LoaderManager.LoaderCallbacks<ApiResponse<T>>
{
  private final AbstractApiCallbacks<T> mApiCallbacks;
  protected final Context mContext;
  private AbstractRequest mRequest;

  public BaseApiLoaderCallbacks(AbstractApiCallbacks<T> paramAbstractApiCallbacks, Context paramContext, AbstractRequest paramAbstractRequest)
  {
    this.mApiCallbacks = paramAbstractApiCallbacks;
    this.mContext = paramContext.getApplicationContext();
    this.mRequest = paramAbstractRequest;
  }

  private SystemMessageHelper getSystemMessageHelper(String paramString)
  {
    Object localObject = null;
    if (paramString.equalsIgnoreCase("fb_needs_reauth"))
      localObject = new BaseApiLoaderCallbacks.1(this);
    while (true)
    {
      return localObject;
      if (!paramString.equalsIgnoreCase("foursquare_needs_reauth"))
        continue;
      localObject = new BaseApiLoaderCallbacks.2(this);
    }
  }

  public static void handleRequestServerErrorMessage(String paramString1, String paramString2)
  {
    Handler localHandler = ClickManager.getInstance().getHandler();
    FragmentManager localFragmentManager = ClickManager.getInstance().getCurrentFragmentManager();
    if ((localHandler != null) && (localFragmentManager != null))
      localHandler.post(new BaseApiLoaderCallbacks.3(paramString1, paramString2, localFragmentManager));
  }

  private void handleSystemMessage(HashMap<String, String> paramHashMap)
  {
    monitorenter;
    try
    {
      String str = (String)paramHashMap.get("key");
      SystemMessageHelper localSystemMessageHelper = getSystemMessageHelper(str);
      if (localSystemMessageHelper != null)
      {
        Float localFloat1 = Preferences.getInstance(getContext()).getSystemMessageTime(str);
        Float localFloat2 = Float.valueOf(Float.parseFloat((String)paramHashMap.get("time")));
        if ((localFloat1 == null) || (localFloat1.floatValue() < localFloat2.floatValue()))
        {
          Preferences.getInstance(getContext()).setSystemMessageTime(str, localFloat2);
          localSystemMessageHelper.logoutAndUnlink();
        }
      }
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public AbstractApiCallbacks<T> getApiCallbacks()
  {
    return this.mApiCallbacks;
  }

  public Context getContext()
  {
    return this.mContext;
  }

  public Loader<ApiResponse<T>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new AsyncTaskDataLoader(this.mContext);
  }

  public void onLoadFinished(Loader<ApiResponse<T>> paramLoader, ApiResponse<T> paramApiResponse)
  {
    if (paramApiResponse.isNetworkRequest())
    {
      paramApiResponse.setIsNetworkResponse(false);
      if (this.mApiCallbacks != null)
      {
        this.mApiCallbacks.onRequestFinished();
        if (paramApiResponse.getSystemMessages() != null)
        {
          Iterator localIterator = paramApiResponse.getSystemMessages().iterator();
          while (localIterator.hasNext())
            handleSystemMessage((HashMap)localIterator.next());
        }
        if ((paramApiResponse != null) && (paramApiResponse.getStatus() != null))
          break label92;
        paramApiResponse.setErrorStatusIfFailedToLoad();
        this.mApiCallbacks.onRequestFail(paramApiResponse);
      }
    }
    while (true)
    {
      return;
      label92: if (paramApiResponse.getStatus().equals("fail"))
      {
        if ((paramApiResponse.getErrorMessage() != null) && (paramApiResponse.getErrorMessage().equals("login_required")))
        {
          LogoutTask.executeClientLogout();
          continue;
        }
        if (this.mRequest.shouldShowAlertForRequest(paramApiResponse))
          handleRequestServerErrorMessage(paramApiResponse.getErrorTitle(), paramApiResponse.getErrorMessage());
      }
      if (paramApiResponse.isOk())
      {
        this.mApiCallbacks.onSuccess(paramApiResponse.getSuccessObject());
        continue;
      }
      paramApiResponse.setErrorStatusIfFailedToLoad();
      this.mApiCallbacks.onRequestFail(paramApiResponse);
      continue;
      if ((!paramApiResponse.isOk()) || (this.mApiCallbacks == null))
        continue;
      this.mApiCallbacks.onSuccess(paramApiResponse.getSuccessObject());
    }
  }

  public void onLoaderReset(Loader<ApiResponse<T>> paramLoader)
  {
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.BaseApiLoaderCallbacks
 * JD-Core Version:    0.6.0
 */