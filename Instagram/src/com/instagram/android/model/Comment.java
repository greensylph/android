package com.instagram.android.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.SpannableStringBuilder;
import android.view.View;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.fragment.CommentThreadFragment;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.CustomObjectMapper;
import com.instagram.android.widget.ClickableNameSpan;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.api.request.AbstractRequest;
import com.instagram.util.JSONUtil;
import com.instagram.util.LoaderUtil;
import com.instagram.util.StringUtil;
import com.instagram.util.TimespanUtils;
import java.io.IOException;
import java.util.regex.Matcher;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class Comment
{
  public static String BROADCAST_EXTRA_USER_NAME;
  public static String BROADCAST_HASH_TAG_CLICKED;
  public static String BROADCAST_HASH_TAG_NAME;
  public static String BROADCAST_USER_NAME_LINK_CLICKED = "com.instagram.android.model.Comment.BROADCAST_USER_LINK_CLICKED";
  private String contentType;
  private SpannableStringBuilder mAnnotatedText;
  private CommentThreadFragment mCommentThreadFragment;
  private long mCreatedAt;
  private CharSequence mFormattedCommentText;
  private String mFormattedDate;
  private Media mMedia;
  private String mMediaId;
  private String mPk;
  private CommentPostedState mPostedState;
  private String mText;
  private CommentType mType;
  private User mUser;
  private String mWasMarkedAsSpamErrorMessage;

  static
  {
    BROADCAST_EXTRA_USER_NAME = "com.instagram.android.model.Comment.BROADCAST_EXTRA_USER_NAME";
    BROADCAST_HASH_TAG_CLICKED = "com.instagram.android.model.Comment.BROADCAST_HASH_TAG_CLICKED";
    BROADCAST_HASH_TAG_NAME = "com.instagram.android.model.Comment.BROADCAST_EXTRA_HASH_TAG_NAME";
  }

  private Comment()
  {
  }

  public Comment(String paramString, Media paramMedia, User paramUser, CommentThreadFragment paramCommentThreadFragment)
  {
    this.mText = paramString;
    this.mMedia = paramMedia;
    this.mMediaId = paramMedia.getId();
    this.mUser = paramUser;
    this.mPostedState = CommentPostedState.Posting;
    this.mCreatedAt = TimespanUtils.getCurrentTimeInSeconds();
    this.mCommentThreadFragment = paramCommentThreadFragment;
  }

  private void buildHashText(SpannableStringBuilder paramSpannableStringBuilder)
  {
    Matcher localMatcher = StringUtil.hashMatcher(this.mText);
    while (localMatcher.find())
      paramSpannableStringBuilder.setSpan(new ClickableNameSpan(false, localMatcher.group(1))
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent(Comment.BROADCAST_HASH_TAG_CLICKED);
          localIntent.putExtra(Comment.BROADCAST_HASH_TAG_NAME, StringUtils.remove(this.val$matchingText.toLowerCase(), "#"));
          LocalBroadcastManager.getInstance(AppContext.getContext()).sendBroadcast(localIntent);
        }
      }
      , localMatcher.start(1), localMatcher.end(1), 33);
  }

  private void buildMentionText(SpannableStringBuilder paramSpannableStringBuilder)
  {
    Matcher localMatcher = StringUtil.mentionMatcher(this.mText);
    while (localMatcher.find())
      paramSpannableStringBuilder.setSpan(new ClickableNameSpan(false, localMatcher.group(2))
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent(Comment.BROADCAST_USER_NAME_LINK_CLICKED);
          localIntent.putExtra(Comment.BROADCAST_EXTRA_USER_NAME, StringUtils.remove(this.val$matchingText.toLowerCase(), "@"));
          LocalBroadcastManager.getInstance(AppContext.getContext()).sendBroadcast(localIntent);
        }
      }
      , localMatcher.start(2), localMatcher.end(2), 33);
  }

  public static Comment fromJsonNode(CustomObjectMapper paramCustomObjectMapper, JsonNode paramJsonNode)
  {
    Comment localComment = new Comment();
    localComment.mUser = ((User)paramCustomObjectMapper.readValue(paramJsonNode.get("user"), User.class));
    localComment.mText = StringUtil.stripEmoji(paramJsonNode.get("text").asText());
    localComment.mMediaId = paramJsonNode.get("media_id").asText();
    localComment.mPk = paramJsonNode.get("pk").asText();
    localComment.mPostedState = CommentPostedState.Success;
    localComment.mType = CommentType.fromValue(paramJsonNode.get("type").asInt());
    localComment.mCreatedAt = paramJsonNode.get("created_at").asLong();
    localComment.getAnnotatedText();
    return localComment;
  }

  public static Comment fromJsonParser(JsonParser paramJsonParser)
    throws JsonParseException, IOException
  {
    Comment localComment = null;
    if (paramJsonParser.getCurrentToken() != JsonToken.VALUE_NULL)
      while (paramJsonParser.nextToken() != JsonToken.END_OBJECT)
      {
        String str = paramJsonParser.getCurrentName();
        if ((str != null) && (localComment == null))
        {
          localComment = new Comment();
          localComment.mPostedState = CommentPostedState.Success;
        }
        if ("user".equals(str))
        {
          paramJsonParser.nextToken();
          localComment.mUser = User.fromJsonParser(AppContext.getContext(), paramJsonParser);
          continue;
        }
        if ("text".equals(str))
        {
          paramJsonParser.nextToken();
          localComment.mText = StringUtil.stripEmoji(paramJsonParser.getText());
          continue;
        }
        if ("media_id".equals(str))
        {
          paramJsonParser.nextToken();
          localComment.mMediaId = paramJsonParser.getText();
          continue;
        }
        if ("pk".equals(str))
        {
          paramJsonParser.nextToken();
          localComment.mPk = paramJsonParser.getText();
          continue;
        }
        if ("pk".equals(str))
        {
          paramJsonParser.nextToken();
          localComment.mMediaId = paramJsonParser.getText();
          continue;
        }
        if ("type".equals(str))
        {
          paramJsonParser.nextToken();
          localComment.mType = CommentType.fromValue(paramJsonParser.getValueAsInt());
          continue;
        }
        if (!"created_at".equals(str))
          continue;
        paramJsonParser.nextToken();
        localComment.mCreatedAt = paramJsonParser.getValueAsLong();
      }
    return localComment;
  }

  private void setPostedState(CommentPostedState paramCommentPostedState)
  {
    this.mPostedState = paramCommentPostedState;
  }

  void commentDeleteFailed()
  {
    setPostedState(CommentPostedState.Success);
    if (this.mCommentThreadFragment != null)
      this.mCommentThreadFragment.onPostCommentFailed(this, false, null);
    getMedia().commentRemoveRequestFailed();
  }

  void commentDeleteFinished()
  {
    setPostedState(CommentPostedState.Deleted);
    getMedia().commentRemoveRequestFinished();
  }

  public void forceRemove(LoaderManager paramLoaderManager)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.mMedia.getId();
    arrayOfObject[1] = this.mPk;
    new DeleteCommentRequest(paramLoaderManager, String.format("media/%s/comment/%s/delete/", arrayOfObject)).perform();
  }

  public SpannableStringBuilder getAnnotatedText()
  {
    if ((this.mAnnotatedText == null) && (this.mText != null))
    {
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(this.mText);
      buildMentionText(localSpannableStringBuilder);
      buildHashText(localSpannableStringBuilder);
      this.mAnnotatedText = localSpannableStringBuilder;
    }
    return this.mAnnotatedText;
  }

  public String getContentType()
  {
    return this.contentType;
  }

  public long getCreatedAt()
  {
    return this.mCreatedAt;
  }

  public CharSequence getFormattedCommentText()
  {
    if (this.mFormattedCommentText == null)
    {
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
      getUser().createUserLink(localSpannableStringBuilder, 0);
      localSpannableStringBuilder.append(" ").append(getAnnotatedText());
      this.mFormattedCommentText = localSpannableStringBuilder;
    }
    return this.mFormattedCommentText;
  }

  public CharSequence getFormattedDate(Context paramContext)
  {
    return TimespanUtils.getFormattedDateRelativeToNow(paramContext, this.mCreatedAt);
  }

  public String getMarkedAsSpamErrorMessage()
  {
    return this.mWasMarkedAsSpamErrorMessage;
  }

  public Media getMedia()
  {
    return this.mMedia;
  }

  public String getMediaId()
  {
    return this.mMediaId;
  }

  public String getPk()
  {
    return this.mPk;
  }

  public CommentPostedState getPostedState()
  {
    return this.mPostedState;
  }

  public String getText()
  {
    return this.mText;
  }

  public CommentType getType()
  {
    return this.mType;
  }

  public User getUser()
  {
    return this.mUser;
  }

  public void remove(LoaderManager paramLoaderManager)
  {
    if (this.mPostedState == CommentPostedState.Success)
    {
      setPostedState(CommentPostedState.DeletePending);
      getMedia().commentRemoveRequestStarted();
      forceRemove(paramLoaderManager);
    }
    while (true)
    {
      return;
      if (this.mPostedState == CommentPostedState.Failure)
      {
        setPostedState(CommentPostedState.DeletePending);
        getMedia().commentRemoveRequestStarted();
        commentDeleteFinished();
        continue;
      }
      setPostedState(CommentPostedState.DeletePending);
    }
  }

  public void setMedia(Media paramMedia)
  {
    this.mMedia = paramMedia;
  }

  public void updateFromCommentPostFailed(Context paramContext, boolean paramBoolean, String paramString)
  {
    this.mPostedState = CommentPostedState.Failure;
    if ((paramBoolean) && (paramString != null))
      this.mWasMarkedAsSpamErrorMessage = paramString;
    if (this.mCommentThreadFragment != null)
      this.mCommentThreadFragment.onPostCommentFailed(this, paramBoolean, paramString);
    while (true)
    {
      getMedia().commentPostRequestFailed(paramContext);
      return;
      if (!paramBoolean)
        continue;
      remove(null);
    }
  }

  public void updateFromCommentPostSuccess(JsonNode paramJsonNode, Context paramContext)
  {
    this.mPk = JSONUtil.safeParsePK(paramJsonNode);
    if (getPostedState() == CommentPostedState.DeletePending)
    {
      this.mPostedState = CommentPostedState.Success;
      if (this.mCommentThreadFragment != null)
        remove(this.mCommentThreadFragment.getLoaderManager());
    }
    while (true)
    {
      return;
      this.mPostedState = CommentPostedState.Success;
      getMedia().commentPostRequestFinished(paramContext);
    }
  }

  public boolean wasMarkedAsSpam()
  {
    if (this.mWasMarkedAsSpamErrorMessage != null);
    for (int i = 1; ; i = 0)
      return i;
  }

  public static enum CommentPostedState
  {
    static
    {
      Failure = new CommentPostedState("Failure", 1);
      Posting = new CommentPostedState("Posting", 2);
      Deleted = new CommentPostedState("Deleted", 3);
      DeletePending = new CommentPostedState("DeletePending", 4);
      Success = new CommentPostedState("Success", 5);
      CommentPostedState[] arrayOfCommentPostedState = new CommentPostedState[6];
      arrayOfCommentPostedState[0] = Unposted;
      arrayOfCommentPostedState[1] = Failure;
      arrayOfCommentPostedState[2] = Posting;
      arrayOfCommentPostedState[3] = Deleted;
      arrayOfCommentPostedState[4] = DeletePending;
      arrayOfCommentPostedState[5] = Success;
      $VALUES = arrayOfCommentPostedState;
    }

    public boolean isActive()
    {
      if ((this != Deleted) && (this != DeletePending));
      for (int i = 1; ; i = 0)
        return i;
    }
  }

  static enum CommentType
  {
    static
    {
      Caption = new CommentType("Caption", 1);
      CommentType[] arrayOfCommentType = new CommentType[2];
      arrayOfCommentType[0] = Normal;
      arrayOfCommentType[1] = Caption;
      $VALUES = arrayOfCommentType;
    }

    public static CommentType fromValue(int paramInt)
    {
      switch (paramInt)
      {
      default:
      case 1:
      }
      for (CommentType localCommentType = Normal; ; localCommentType = Caption)
        return localCommentType;
    }
  }

  private class DeleteCommentRequest extends AbstractRequest<Object>
  {
    private final String mPath;

    public DeleteCommentRequest(LoaderManager paramString, String arg3)
    {
      super(paramString, LoaderUtil.getUniqueId(), null);
      Object localObject;
      this.mPath = localObject;
    }

    protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
    {
      return paramApiHttpClient.postRequest(paramString, paramRequestParams);
    }

    protected String getPath()
    {
      return this.mPath;
    }

    public void handleErrorInBackground(ApiResponse<Object> paramApiResponse)
    {
      Comment.this.commentDeleteFailed();
    }

    public Object processInBackground(ApiResponse<Object> paramApiResponse)
    {
      Comment.this.commentDeleteFinished();
      return null;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.model.Comment
 * JD-Core Version:    0.6.0
 */