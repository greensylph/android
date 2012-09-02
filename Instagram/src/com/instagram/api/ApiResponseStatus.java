package com.instagram.api;

public enum ApiResponseStatus
{
  static
  {
    ApiResponseStatusObjectNotFound = new ApiResponseStatus("ApiResponseStatusObjectNotFound", 2);
    ApiResponseStatusError = new ApiResponseStatus("ApiResponseStatusError", 3);
    ApiResponseStatus[] arrayOfApiResponseStatus = new ApiResponseStatus[4];
    arrayOfApiResponseStatus[0] = ApiResponseStatusLoading;
    arrayOfApiResponseStatus[1] = ApiResponseStatusOk;
    arrayOfApiResponseStatus[2] = ApiResponseStatusObjectNotFound;
    arrayOfApiResponseStatus[3] = ApiResponseStatusError;
    $VALUES = arrayOfApiResponseStatus;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.ApiResponseStatus
 * JD-Core Version:    0.6.0
 */