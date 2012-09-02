package com.instagram.api;

import com.instagram.android.service.AppContext;
import com.instagram.foursquare.FoursquareAccount;

class BaseApiLoaderCallbacks$2
  implements SystemMessageHelper
{
  public void logoutAndUnlink()
  {
    FoursquareAccount.delete(AppContext.getContext());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.BaseApiLoaderCallbacks.2
 * JD-Core Version:    0.6.0
 */