package com.loopj.android.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

public class AsyncHttpResponseHandler
{
  private static final int FAILURE_MESSAGE = 1;
  private static final int FINISH_MESSAGE = 3;
  private static final int START_MESSAGE = 2;
  private static final int SUCCESS_MESSAGE;
  private Handler handler;

  public AsyncHttpResponseHandler()
  {
    if (Looper.myLooper() != null)
      this.handler = new AsyncHttpResponseHandler.1(this);
  }

  protected void handleFailureMessage(Throwable paramThrowable)
  {
    onFailure(paramThrowable);
  }

  protected void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return;
      handleSuccessMessage((String)paramMessage.obj);
      continue;
      handleFailureMessage((Throwable)paramMessage.obj);
      continue;
      onStart();
      continue;
      onFinish();
    }
  }

  protected void handleSuccessMessage(String paramString)
  {
    onSuccess(paramString);
  }

  protected Message obtainMessage(int paramInt, Object paramObject)
  {
    Message localMessage;
    if (this.handler != null)
      localMessage = this.handler.obtainMessage(paramInt, paramObject);
    while (true)
    {
      return localMessage;
      localMessage = new Message();
      localMessage.what = paramInt;
      localMessage.obj = paramObject;
    }
  }

  public void onFailure(Throwable paramThrowable)
  {
  }

  public void onFinish()
  {
  }

  public void onStart()
  {
  }

  public void onSuccess(String paramString)
  {
  }

  protected void sendFailureMessage(Throwable paramThrowable)
  {
    sendMessage(obtainMessage(1, paramThrowable));
  }

  protected void sendFinishMessage()
  {
    sendMessage(obtainMessage(3, null));
  }

  protected void sendMessage(Message paramMessage)
  {
    if (this.handler != null)
      this.handler.sendMessage(paramMessage);
    while (true)
    {
      return;
      handleMessage(paramMessage);
    }
  }

  void sendResponseMessage(HttpResponse paramHttpResponse)
  {
    StatusLine localStatusLine = paramHttpResponse.getStatusLine();
    if (localStatusLine.getStatusCode() >= 300)
      sendFailureMessage(new HttpResponseException(localStatusLine.getStatusCode(), localStatusLine.getReasonPhrase()));
    while (true)
    {
      return;
      BufferedHttpEntity localBufferedHttpEntity = null;
      try
      {
        HttpEntity localHttpEntity = paramHttpResponse.getEntity();
        if (localHttpEntity != null)
          localBufferedHttpEntity = new BufferedHttpEntity(localHttpEntity);
        sendSuccessMessage(EntityUtils.toString(localBufferedHttpEntity));
      }
      catch (IOException localIOException)
      {
        sendFailureMessage(localIOException);
      }
    }
  }

  protected void sendStartMessage()
  {
    sendMessage(obtainMessage(2, null));
  }

  protected void sendSuccessMessage(String paramString)
  {
    sendMessage(obtainMessage(0, paramString));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.loopj.android.http.AsyncHttpResponseHandler
 * JD-Core Version:    0.6.0
 */