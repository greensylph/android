package com.instagram.api;

import com.instagram.facebook.FacebookAccount;

class BaseApiLoaderCallbacks$1
  implements SystemMessageHelper
{
  public void logoutAndUnlink()
  {
    FacebookAccount.deleteCredentials(true);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.BaseApiLoaderCallbacks.1
 * JD-Core Version:    0.6.0
 */