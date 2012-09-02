package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.Comment;
import com.instagram.android.service.AutoCompleteHashtagService;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.util.JsonBuilder;
import com.instagram.util.LoaderUtil;
import com.instagram.util.RequestUtil;
import org.codehaus.jackson.JsonNode;

public class PostCommentRequest extends AbstractRequest
{
  private Comment mComment;
  private boolean mMarkedAsSpam = false;

  public PostCommentRequest(Context paramContext, LoaderManager paramLoaderManager)
  {
    super(paramContext, paramLoaderManager, LoaderUtil.getUniqueId(), null);
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  protected String getPath()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mComment.getMediaId();
    return String.format("media/%s/comment/", arrayOfObject);
  }

  public void handleErrorInBackground(ApiResponse paramApiResponse)
  {
    boolean bool = false;
    String str = null;
    if ((paramApiResponse != null) && (paramApiResponse.getRootNode() != null))
    {
      JsonNode localJsonNode = paramApiResponse.getRootNode().get("spam");
      if ((localJsonNode == null) || (!localJsonNode.getBooleanValue()))
        break label80;
      bool = true;
      if (paramApiResponse.getRootNode().get("message") == null)
        break label85;
    }
    label80: label85: for (str = paramApiResponse.getRootNode().get("message").getTextValue(); ; str = null)
    {
      this.mComment.updateFromCommentPostFailed(getContext(), bool, str);
      return;
      bool = false;
      break;
    }
  }

  public void perform(Comment paramComment)
  {
    this.mComment = paramComment;
    AutoCompleteHashtagService.addTagsFromText(paramComment.getText());
    String str = new JsonBuilder().put("comment_text", paramComment.getText()).toString();
    RequestUtil.setSignedBody(getParams(), str);
    perform();
  }

  public Object processInBackground(ApiResponse paramApiResponse)
  {
    JsonNode localJsonNode = paramApiResponse.getRootNode().get("comment");
    this.mComment.updateFromCommentPostSuccess(localJsonNode, getContext());
    return null;
  }

  public boolean shouldShowAlertForRequest(ApiResponse paramApiResponse)
  {
    return false;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.PostCommentRequest
 * JD-Core Version:    0.6.0
 */