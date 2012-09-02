package com.instagram.android.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.instagram.android.Log;
import com.instagram.android.fragment.CommentThreadFragment;
import com.instagram.android.fragment.UserDetailFragment;
import com.instagram.android.fragment.UserListFragment;
import com.instagram.android.location.Venue;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.service.ClickManager;
import com.instagram.android.service.CustomObjectMapper;
import com.instagram.android.service.MediaStore;
import com.instagram.android.widget.ClickableNameSpan;
import com.instagram.util.FragmentUtil;
import com.instagram.util.JSONUtil;
import com.instagram.util.StringUtil;
import com.instagram.util.TimespanUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class Media
{
  public static final String BROADCAST_DELIMITER = "|";
  private static final String BROADCAST_EXTRA_ID = "id";
  private static final String BROADCAST_UPDATED_MEDIA = "com.instagram.broadcasts.updated_media";
  private static final int COMMENT_COLLAPSE_THRESHOLD = 6;
  private static final int COMMENT_MINIMUM_HIDDEN = 2;
  public static final int DELETED_STATUS_DELETED = 2;
  public static final int DELETED_STATUS_NONE = 0;
  public static final int DELETED_STATUS_PENDING = 1;
  private static final String EXTRA_RELOAD_TABLE = "com.instagram.android.model.Media.reload_table";
  private static final int HIGH_RES_PX = 612;
  public static final int INITIAL_CAPACITY_COMMENTS = 5;
  private static final String LOG_TAG = "Media";
  private static final int LOW_RES_PX = 306;
  private static final String TAG = "Media";
  private static final int THUMB_RES_PX = 150;
  private static MediaSize mediaSize;
  private Long deviceTimestamp;
  private String filter;
  private Integer likeCount;
  private ArrayList<Comment> mActiveComments;
  private List<Comment> mAllComments;
  private Comment mCaption;
  private Integer mCommentCount;
  private CharSequence mCommentText;
  private int mDeletedStatus;
  private CharSequence mFormattedDate;
  private boolean mHasLiked;
  private boolean mHasMoreComments;
  private String mId;
  private boolean mIsLoadingMoreComments = false;
  private Double mLatitude;
  String mLikeAttributedString;
  CharSequence mLikeText;
  private HashSet<User> mLikers;
  private Uri mLocalBitmapUri = null;
  private Double mLongitude;
  private String mLowResolutionUrl;
  private int mNumSkippedCommentsThatCouldNotBeParsed;
  private ArrayList<Comment> mPendingComments = new ArrayList(5);
  private ArrayList<Comment> mPostedComments;
  private String mStandardResolutionUrl;
  private double mTakenAt;
  private String mThumbnailUrl;
  private User mUser;
  private CharSequence mUsernameText;
  private Venue mVenue;
  private String type;

  private void clearCommentArrayCaches()
  {
    this.mActiveComments = null;
    this.mPostedComments = null;
    this.mCommentText = null;
  }

  private void clearLikeText()
  {
    this.mLikeText = null;
    this.mLikeAttributedString = null;
  }

  private MediaSize computeMediaSize(Context paramContext)
  {
    WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    if (Math.abs(306 - localDisplayMetrics.widthPixels) < Math.abs(612 - localDisplayMetrics.widthPixels));
    for (MediaSize localMediaSize = MediaSize.LOW_RES_PX; ; localMediaSize = MediaSize.HIGH_RES_PX)
      return localMediaSize;
  }

  private SpannableStringBuilder createLikersPageLink()
  {
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
    Context localContext = AppContext.getContext();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.likeCount.toString();
    localSpannableStringBuilder.append(localContext.getString(2131230910, arrayOfObject));
    localSpannableStringBuilder.setSpan(new ClickableNameSpan()
    {
      public void onClick(View paramView)
      {
        Bundle localBundle = new Bundle();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Media.this.getId();
        localBundle.putString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FETCH_URL", String.format("media/%s/likers/", arrayOfObject));
        localBundle.putString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_TITLE", AppContext.getContext().getString(2131230920));
        localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_BUTTONS", false);
        FragmentUtil.navigateTo(ClickManager.getInstance().getCurrentFragmentManager(), new UserListFragment(), localBundle);
      }
    }
    , 0, localSpannableStringBuilder.length(), 33);
    return localSpannableStringBuilder;
  }

  private int drawComment(SpannableStringBuilder paramSpannableStringBuilder, int paramInt, Comment paramComment)
  {
    paramSpannableStringBuilder.append(paramComment.getUser().getUsername());
    paramSpannableStringBuilder.setSpan(new ClickableNameSpan(paramComment)
    {
      public void onClick(View paramView)
      {
        Bundle localBundle = new Bundle();
        localBundle.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID", this.val$finalCommentToDraw.getUser().getId());
        FragmentUtil.navigateTo(ClickManager.getInstance().getCurrentFragmentManager(), new UserDetailFragment(), localBundle);
      }
    }
    , paramInt, paramInt + paramComment.getUser().getUsername().length(), 33);
    paramSpannableStringBuilder.append(" ").append(paramComment.getAnnotatedText()).append("\n");
    return paramSpannableStringBuilder.length();
  }

  private int drawMoreLink(SpannableStringBuilder paramSpannableStringBuilder, int paramInt)
  {
    3 local3 = new ClickableNameSpan()
    {
      public void onClick(View paramView)
      {
        CommentThreadFragment.show(ClickManager.getInstance().getCurrentFragmentManager(), Media.this, false);
      }

      public void updateDrawState(TextPaint paramTextPaint)
      {
        super.updateDrawState(paramTextPaint);
        paramTextPaint.setColor(2131165189);
      }
    };
    Context localContext = AppContext.getContext();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mCommentCount;
    paramSpannableStringBuilder.append(localContext.getString(2131230916, arrayOfObject));
    paramSpannableStringBuilder.setSpan(local3, paramInt, paramInt + (paramSpannableStringBuilder.length() - paramInt), 33);
    paramSpannableStringBuilder.append("\n");
    return paramSpannableStringBuilder.length();
  }

  public static Media fromJsonNode(CustomObjectMapper paramCustomObjectMapper, JsonNode paramJsonNode, Media paramMedia)
  {
    if (paramMedia == null)
      paramMedia = new Media();
    paramMedia.parseImageVersion(paramJsonNode.get("image_versions").getElements());
    paramMedia.mId = paramJsonNode.get("id").asText();
    paramMedia.mTakenAt = paramJsonNode.get("taken_at").asDouble();
    paramMedia.mUser = ((User)paramCustomObjectMapper.readValue(paramJsonNode.get("user"), User.class));
    paramMedia.setLikersFromEntry(paramCustomObjectMapper, paramJsonNode);
    paramMedia.setCommentsFromEntry(paramCustomObjectMapper, paramJsonNode);
    paramMedia.setLocationFromEntry(paramCustomObjectMapper, paramJsonNode);
    paramMedia.clearLikeText();
    paramMedia.clearCommentArrayCaches();
    paramMedia.mFormattedDate = null;
    paramMedia.getFormattedDate();
    return paramMedia;
  }

  public static Media fromJsonParser(JsonParser paramJsonParser)
    throws IOException
  {
    Media localMedia = null;
    while (true)
    {
      if (paramJsonParser.nextToken() == JsonToken.END_OBJECT)
        break label544;
      String str1 = paramJsonParser.getCurrentName();
      if ((str1 != null) && (localMedia == null))
      {
        localMedia = new Media();
        localMedia.mAllComments = null;
        localMedia.mCaption = null;
      }
      if ("id".equals(str1))
      {
        paramJsonParser.nextToken();
        localMedia.mId = paramJsonParser.getText();
        continue;
      }
      if ("taken_at".equals(str1))
      {
        paramJsonParser.nextToken();
        localMedia.mTakenAt = paramJsonParser.getDoubleValue();
        continue;
      }
      if ("user".equals(str1))
      {
        localMedia.mUser = User.fromJsonParser(AppContext.getContext(), paramJsonParser);
        continue;
      }
      if ("image_versions".equals(str1))
      {
        paramJsonParser.nextToken();
        String str2 = null;
        int i = 0;
        if (paramJsonParser.nextToken() == JsonToken.END_ARRAY)
          continue;
        String str3 = paramJsonParser.getCurrentName();
        if ("url".equals(str3))
        {
          paramJsonParser.nextToken();
          str2 = paramJsonParser.getText();
          label182: if ((str2 == null) || (i == 0))
            break;
          switch (i)
          {
          default:
          case 7:
          case 6:
          case 5:
          }
        }
        while (true)
        {
          str2 = null;
          i = 0;
          break;
          if (!"type".equals(str3))
            break label182;
          paramJsonParser.nextToken();
          i = paramJsonParser.getValueAsInt();
          break label182;
          localMedia.mStandardResolutionUrl = str2;
          continue;
          localMedia.mLowResolutionUrl = str2;
          continue;
          localMedia.mThumbnailUrl = str2;
        }
      }
      if ("has_liked".equals(str1))
      {
        paramJsonParser.nextToken();
        localMedia.mHasLiked = paramJsonParser.getValueAsBoolean();
        continue;
      }
      if ("like_count".equals(str1))
      {
        paramJsonParser.nextToken();
        localMedia.likeCount = Integer.valueOf(paramJsonParser.getValueAsInt());
        continue;
      }
      if ("likers".equals(str1))
      {
        if (paramJsonParser.nextToken() == JsonToken.VALUE_NULL)
        {
          localMedia.mLikers = null;
          continue;
        }
        ArrayList localArrayList = new ArrayList();
        while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
        {
          User localUser = User.fromJsonParser(AppContext.getContext(), paramJsonParser);
          if (localUser == null)
            break;
          localArrayList.add(localUser);
        }
        localMedia.mLikers = new HashSet(localArrayList);
        continue;
      }
      if (localMedia.canParseAsCommentField(str1))
      {
        localMedia.parseAsCommentField(paramJsonParser);
        continue;
      }
      if ("location".equals(str1))
      {
        paramJsonParser.nextToken();
        localMedia.mVenue = Venue.fromMediaLocationParser(paramJsonParser);
        continue;
      }
      if ("lat".equals(str1))
      {
        paramJsonParser.nextToken();
        localMedia.mLatitude = Double.valueOf(paramJsonParser.getValueAsDouble());
        continue;
      }
      if ("lng".equals(str1))
      {
        paramJsonParser.nextToken();
        localMedia.mLongitude = Double.valueOf(paramJsonParser.getValueAsDouble());
        continue;
      }
      if (str1 == null)
        continue;
      paramJsonParser.skipChildren();
    }
    label544: localMedia.clearLikeText();
    localMedia.clearCommentArrayCaches();
    if ((localMedia.mCommentCount.intValue() > 0) && (localMedia.mNumSkippedCommentsThatCouldNotBeParsed > 0))
      localMedia.mCommentCount = Integer.valueOf(localMedia.mCommentCount.intValue() - localMedia.mNumSkippedCommentsThatCouldNotBeParsed);
    MediaStore localMediaStore;
    if (localMedia != null)
    {
      localMediaStore = MediaStore.getInstance(AppContext.getContext());
      if (localMediaStore.get(localMedia.getId()) != null)
        break label622;
      localMediaStore.put(localMedia.getId(), localMedia);
    }
    while (true)
    {
      return localMedia;
      label622: localMedia = localMediaStore.update(localMedia.getId(), localMedia);
      localMedia.broadcastUpdatedMedia();
    }
  }

  private ArrayList<Comment> generateActiveComments()
  {
    ArrayList localArrayList = new ArrayList(5);
    if ((this.mCaption != null) && (this.mCaption.getPostedState() == Comment.CommentPostedState.Success))
      localArrayList.add(this.mCaption);
    Iterator localIterator = this.mAllComments.iterator();
    while (localIterator.hasNext())
    {
      Comment localComment = (Comment)localIterator.next();
      if (!localComment.getPostedState().isActive())
        continue;
      localArrayList.add(localComment);
    }
    return localArrayList;
  }

  private CharSequence generateCommentText()
  {
    ArrayList localArrayList = getPostedComments();
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
    int i = 0;
    int j;
    int k;
    int m;
    int n;
    int i1;
    label62: int i2;
    label70: int i4;
    label80: Object localObject;
    int i5;
    if (localArrayList != null)
    {
      j = localArrayList.size();
      k = 0;
      if (!this.mHasMoreComments)
        break label157;
      m = 0;
      n = Math.min(j, 6);
      if ((m != 0) && (!this.mHasMoreComments))
        break label199;
      i1 = 1;
      if (i1 == 0)
        break label205;
      i2 = 1;
      int i3 = n + i2;
      i4 = 0;
      if (i4 >= i3)
        break label295;
      localObject = null;
      i5 = 0;
      if (i4 != 0)
        break label217;
      Comment localComment = getProtectedCommentIndex(localArrayList, 0);
      if (localComment != null)
      {
        if ((m == 0) && (localComment.getType() != Comment.CommentType.Caption))
          break label211;
        localObject = localComment;
      }
      label131: if (localObject == null)
        break label277;
      i = drawComment(localSpannableStringBuilder, i, (Comment)localObject);
    }
    while (true)
    {
      i4++;
      break label80;
      j = 0;
      break;
      label157: if (j - 6 <= 2)
      {
        m = 1;
        label169: if (m == 0)
          break label187;
      }
      label187: for (n = j; ; n = Math.min(j, 6))
      {
        break;
        m = 0;
        break label169;
      }
      label199: i1 = 0;
      break label62;
      label205: i2 = 0;
      break label70;
      label211: i5 = 1;
      break label131;
      label217: if ((i4 == 1) && (i1 != 0) && (k == 0))
      {
        i5 = 1;
        break label131;
      }
      if (m != 0)
      {
        localObject = getProtectedCommentIndex(localArrayList, i4);
        break label131;
      }
      localObject = getProtectedCommentIndex(localArrayList, -1 + (i4 + (j - n)));
      break label131;
      label277: if (i5 == 0)
        continue;
      i = drawMoreLink(localSpannableStringBuilder, i);
      k = 1;
    }
    label295: if (localSpannableStringBuilder.length() > 0)
      localSpannableStringBuilder.delete(-1 + localSpannableStringBuilder.length(), localSpannableStringBuilder.length());
    return (CharSequence)localSpannableStringBuilder;
  }

  private CharSequence generateLikersText()
  {
    Object localObject;
    if (this.mLikers != null)
      if (this.mLikers.size() == 0)
        localObject = "";
    while (true)
    {
      return localObject;
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
      int i = 0;
      Iterator localIterator = this.mLikers.iterator();
      while (localIterator.hasNext())
      {
        ((User)localIterator.next()).createUserLink(localSpannableStringBuilder, i);
        localSpannableStringBuilder.append(", ");
        i = localSpannableStringBuilder.length();
      }
      localSpannableStringBuilder.delete(-2 + localSpannableStringBuilder.length(), localSpannableStringBuilder.length());
      localObject = localSpannableStringBuilder;
      continue;
      if (this.likeCount.intValue() > 0)
      {
        localObject = createLikersPageLink();
        continue;
      }
      localObject = "";
    }
  }

  private ArrayList<Comment> generatePostedComments()
  {
    ArrayList localArrayList = new ArrayList(5);
    if ((this.mCaption != null) && (this.mCaption.getPostedState() == Comment.CommentPostedState.Success))
      localArrayList.add(this.mCaption);
    Iterator localIterator = this.mAllComments.iterator();
    while (localIterator.hasNext())
    {
      Comment localComment = (Comment)localIterator.next();
      if (localComment.getPostedState() != Comment.CommentPostedState.Success)
        continue;
      localArrayList.add(localComment);
    }
    return localArrayList;
  }

  private CharSequence generateUsernameText()
  {
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
    getUser().createUserLink(localSpannableStringBuilder, 0);
    return localSpannableStringBuilder;
  }

  private String getLowResolutionUrl()
  {
    return this.mLowResolutionUrl;
  }

  public static String getMediaBroadcastString(String paramString)
  {
    return "com.instagram.broadcasts.updated_media|" + paramString;
  }

  private Comment getProtectedCommentIndex(ArrayList<Comment> paramArrayList, int paramInt)
  {
    try
    {
      localComment = (Comment)paramArrayList.get(paramInt);
      return localComment;
    }
    catch (Exception localException)
    {
      while (true)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(paramInt);
        Log.e("Media", String.format("Attempt to get a comment that does not exist. index = %s", arrayOfObject));
        Comment localComment = null;
      }
    }
  }

  private String getStandardResolutionUrl()
  {
    return this.mStandardResolutionUrl;
  }

  private void parseImageVersion(Iterator<JsonNode> paramIterator)
  {
    while (paramIterator.hasNext())
    {
      JsonNode localJsonNode = (JsonNode)paramIterator.next();
      String str = localJsonNode.get("url").asText();
      switch (localJsonNode.get("type").asInt())
      {
      default:
        break;
      case 5:
        this.mThumbnailUrl = str;
        break;
      case 7:
        this.mStandardResolutionUrl = str;
        break;
      case 6:
      }
      this.mLowResolutionUrl = str;
    }
  }

  public static String parseMediaIdFromBroadcast(String paramString)
  {
    return paramString.split("|")[1];
  }

  private void setCommentsFromEntry(CustomObjectMapper paramCustomObjectMapper, JsonNode paramJsonNode)
  {
    this.mAllComments = null;
    clearCommentArrayCaches();
    if (paramJsonNode.get("comments") != null)
    {
      this.mAllComments = paramCustomObjectMapper.readArrayList(paramJsonNode.get("comments"), Comment.class);
      Iterator localIterator = this.mAllComments.iterator();
      while (localIterator.hasNext())
        ((Comment)localIterator.next()).setMedia(this);
    }
    this.mAllComments = null;
    this.mCommentCount = Integer.valueOf(paramJsonNode.get("comment_count").asInt());
    this.mHasMoreComments = paramJsonNode.get("has_more_comments").asBoolean(false);
    if (JSONUtil.exists(paramJsonNode, "caption"))
    {
      this.mCaption = ((Comment)paramCustomObjectMapper.readValue(paramJsonNode.get("caption"), Comment.class));
      this.mCaption.setMedia(this);
    }
    while (true)
    {
      return;
      this.mCaption = null;
    }
  }

  private void setLikersFromEntry(CustomObjectMapper paramCustomObjectMapper, JsonNode paramJsonNode)
  {
    if (paramJsonNode.get("has_liked") != null)
      this.mHasLiked = paramJsonNode.get("has_liked").asBoolean();
    if (paramJsonNode.get("like_count") != null)
      this.likeCount = Integer.valueOf(paramJsonNode.get("like_count").asInt());
    this.mLikers = null;
    if (paramJsonNode.get("likers") != null)
      this.mLikers = new HashSet(paramCustomObjectMapper.readArrayList(paramJsonNode.get("likers"), User.class));
  }

  private void setLocationFromEntry(CustomObjectMapper paramCustomObjectMapper, JsonNode paramJsonNode)
  {
    JsonNode localJsonNode = paramJsonNode.get("location");
    this.mVenue = null;
    if (localJsonNode != null);
    try
    {
      this.mVenue = Venue.fromMediaLocationParser(localJsonNode.traverse());
      label28: if (paramJsonNode.get("lat") != null)
      {
        this.mLatitude = Double.valueOf(paramJsonNode.get("lat").asDouble());
        this.mLongitude = Double.valueOf(paramJsonNode.get("lng").asDouble());
      }
      return;
    }
    catch (IOException localIOException)
    {
      break label28;
    }
  }

  public void broadcastUpdatedMedia()
  {
    broadcastUpdatedMedia(AppContext.getContext(), false);
  }

  public void broadcastUpdatedMedia(Context paramContext, boolean paramBoolean)
  {
    Intent localIntent = new Intent();
    localIntent.setAction(getMediaBroadcastString(getId()));
    localIntent.putExtra("id", getId());
    localIntent.putExtra("com.instagram.android.model.Media.reload_table", paramBoolean);
    LocalBroadcastManager.getInstance(paramContext).sendBroadcast(localIntent);
  }

  public boolean canParseAsCommentField(String paramString)
  {
    int i = 1;
    if ("comments".equals(paramString));
    while (true)
    {
      return i;
      if (("comment_count".equals(paramString)) || ("has_more_comments".equals(paramString)) || ("caption".equals(paramString)))
        continue;
      i = 0;
    }
  }

  public void commentPostRequestFailed(Context paramContext)
  {
    clearCommentArrayCaches();
    broadcastUpdatedMedia(paramContext, true);
  }

  public void commentPostRequestFinished(Context paramContext)
  {
    this.mCommentCount = Integer.valueOf(1 + this.mCommentCount.intValue());
    clearCommentArrayCaches();
    broadcastUpdatedMedia(paramContext, true);
  }

  public void commentPostRequestStart(Comment paramComment, Context paramContext)
  {
    if (this.mAllComments.indexOf(paramComment) == -1)
    {
      this.mAllComments.add(paramComment);
      if (this.mIsLoadingMoreComments)
        this.mPendingComments.add(paramComment);
    }
    clearCommentArrayCaches();
    broadcastUpdatedMedia(paramContext, true);
  }

  public void commentRemoveRequestFailed()
  {
    clearCommentArrayCaches();
    broadcastUpdatedMedia(AppContext.getContext(), true);
  }

  public void commentRemoveRequestFinished()
  {
    clearCommentArrayCaches();
    this.mCommentCount = Integer.valueOf(-1 + this.mCommentCount.intValue());
    broadcastUpdatedMedia(AppContext.getContext(), true);
  }

  public void commentRemoveRequestStarted()
  {
    clearCommentArrayCaches();
    broadcastUpdatedMedia();
  }

  public void commentsRequestFailed()
  {
    this.mIsLoadingMoreComments = false;
    broadcastUpdatedMedia(AppContext.getContext(), true);
  }

  public void commentsRequestFinished()
  {
    if (this.mPendingComments.size() > 0)
      this.mAllComments.addAll(this.mPendingComments);
    this.mPendingComments.clear();
    this.mHasMoreComments = false;
    this.mIsLoadingMoreComments = false;
    broadcastUpdatedMedia(AppContext.getContext(), true);
  }

  public void commentsRequestStarted()
  {
    this.mIsLoadingMoreComments = true;
  }

  public boolean equals(Object paramObject)
  {
    int i = 1;
    if (this == paramObject);
    while (true)
    {
      return i;
      if ((paramObject == null) || (getClass() != paramObject.getClass()))
      {
        i = 0;
        continue;
      }
      Media localMedia = (Media)paramObject;
      if (this.mId != null)
        if (this.mId.equals(localMedia.mId))
          continue;
      do
      {
        i = 0;
        break;
      }
      while (localMedia.mId != null);
    }
  }

  public ArrayList<Comment> getActiveComments()
  {
    if ((this.mActiveComments == null) && (this.mAllComments != null))
      this.mActiveComments = generateActiveComments();
    return this.mActiveComments;
  }

  public Comment getCaption()
  {
    return this.mCaption;
  }

  public Integer getCommentCount()
  {
    return this.mCommentCount;
  }

  public CharSequence getCommentText()
  {
    if (this.mCommentText == null)
      this.mCommentText = generateCommentText();
    return this.mCommentText;
  }

  public List<Comment> getComments()
  {
    return this.mAllComments;
  }

  public int getDeletedStatus()
  {
    return this.mDeletedStatus;
  }

  public Long getDeviceTimestamp()
  {
    return this.deviceTimestamp;
  }

  public FeedItemLocationType getFeedItemLocationType()
  {
    if (this.mVenue.latitude != null);
    for (FeedItemLocationType localFeedItemLocationType = FeedItemLocationType.Foursquare; ; localFeedItemLocationType = FeedItemLocationType.User)
      return localFeedItemLocationType;
  }

  public String getFilter()
  {
    return this.filter;
  }

  public CharSequence getFormattedDate()
  {
    if (this.mFormattedDate == null)
      this.mFormattedDate = TimespanUtils.getShortenedFormattedDateRelativeToNow(AppContext.getContext(), this.mTakenAt);
    return this.mFormattedDate;
  }

  public String getId()
  {
    return this.mId;
  }

  public Double getLatitude()
  {
    Venue localVenue = getVenue();
    if ((localVenue != null) && (localVenue.latitude != null));
    for (Double localDouble = Double.valueOf(localVenue.latitude.doubleValue()); ; localDouble = this.mLatitude)
      return localDouble;
  }

  public Integer getLikeCount()
  {
    return this.likeCount;
  }

  public CharSequence getLikeText()
  {
    if (this.mLikeText == null)
      this.mLikeText = generateLikersText();
    return this.mLikeText;
  }

  public Uri getLocalBitmapUri()
  {
    return this.mLocalBitmapUri;
  }

  public Double getLongitude()
  {
    Venue localVenue = getVenue();
    if ((localVenue != null) && (localVenue.longitude != null));
    for (Double localDouble = Double.valueOf(localVenue.longitude.doubleValue()); ; localDouble = this.mLongitude)
      return localDouble;
  }

  public ArrayList<Comment> getPostedComments()
  {
    if ((this.mPostedComments == null) && (this.mAllComments != null))
      this.mPostedComments = generatePostedComments();
    return this.mPostedComments;
  }

  public String getSizedUrl()
  {
    if (mediaSize == null)
      mediaSize = computeMediaSize(AppContext.getContext());
    switch (4.$SwitchMap$com$instagram$android$model$Media$MediaSize[mediaSize.ordinal()])
    {
    default:
    case 1:
    }
    for (String str = this.mStandardResolutionUrl; ; str = this.mLowResolutionUrl)
      return str;
  }

  public double getTakenAt()
  {
    return this.mTakenAt;
  }

  public String getThumbnailUrl()
  {
    return this.mThumbnailUrl;
  }

  public String getType()
  {
    return this.type;
  }

  public User getUser()
  {
    return this.mUser;
  }

  public CharSequence getUserNameText()
  {
    if (this.mUsernameText == null)
      this.mUsernameText = generateUsernameText();
    return this.mUsernameText;
  }

  public Venue getVenue()
  {
    return this.mVenue;
  }

  public boolean hasLatLng()
  {
    if ((getLatitude() != null) && (getLongitude() != null));
    for (int i = 1; ; i = 0)
      return i;
  }

  // ERROR //
  public boolean hasLocalBitmap()
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_1
    //   2: aload_0
    //   3: getfield 115	com/instagram/android/model/Media:mLocalBitmapUri	Landroid/net/Uri;
    //   6: ifnull +52 -> 58
    //   9: iload_1
    //   10: istore_2
    //   11: iconst_0
    //   12: istore_3
    //   13: iload_2
    //   14: ifeq +34 -> 48
    //   17: invokestatic 178	com/instagram/android/service/AppContext:getContext	()Landroid/content/Context;
    //   20: invokevirtual 744	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   23: astore 4
    //   25: aload 4
    //   27: aload_0
    //   28: getfield 115	com/instagram/android/model/Media:mLocalBitmapUri	Landroid/net/Uri;
    //   31: invokevirtual 750	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   34: astore 10
    //   36: iconst_1
    //   37: istore_3
    //   38: aload 10
    //   40: ifnull +8 -> 48
    //   43: aload 10
    //   45: invokevirtual 755	java/io/InputStream:close	()V
    //   48: iload_2
    //   49: ifeq +58 -> 107
    //   52: iload_3
    //   53: ifeq +54 -> 107
    //   56: iload_1
    //   57: ireturn
    //   58: iconst_0
    //   59: istore_2
    //   60: goto -49 -> 11
    //   63: astore 7
    //   65: ldc 48
    //   67: ldc_w 757
    //   70: aload 7
    //   72: invokestatic 761	com/instagram/android/Log:d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   75: pop
    //   76: iconst_0
    //   77: istore_3
    //   78: iconst_0
    //   79: ifeq -31 -> 48
    //   82: aconst_null
    //   83: invokevirtual 755	java/io/InputStream:close	()V
    //   86: goto -38 -> 48
    //   89: astore 9
    //   91: goto -43 -> 48
    //   94: astore 5
    //   96: iconst_0
    //   97: ifeq +7 -> 104
    //   100: aconst_null
    //   101: invokevirtual 755	java/io/InputStream:close	()V
    //   104: aload 5
    //   106: athrow
    //   107: iconst_0
    //   108: istore_1
    //   109: goto -53 -> 56
    //   112: astore 11
    //   114: goto -66 -> 48
    //   117: astore 6
    //   119: goto -15 -> 104
    //
    // Exception table:
    //   from	to	target	type
    //   25	36	63	java/io/FileNotFoundException
    //   82	86	89	java/io/IOException
    //   25	36	94	finally
    //   65	76	94	finally
    //   43	48	112	java/io/IOException
    //   100	104	117	java/io/IOException
  }

  public boolean hasMoreComments()
  {
    return this.mHasMoreComments;
  }

  public int hashCode()
  {
    if (this.mId != null);
    for (int i = this.mId.hashCode(); ; i = 0)
      return i;
  }

  public boolean isHasLiked()
  {
    return this.mHasLiked;
  }

  public boolean isLoadingMoreComments()
  {
    return this.mIsLoadingMoreComments;
  }

  void onTimeChange()
  {
    this.mFormattedDate = null;
  }

  public void parseAsCommentField(JsonParser paramJsonParser)
    throws JsonParseException, IOException
  {
    String str = paramJsonParser.getCurrentName();
    if ("comments".equals(str))
    {
      paramJsonParser.nextToken();
      ArrayList localArrayList = new ArrayList();
      while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
      {
        Comment localComment = Comment.fromJsonParser(paramJsonParser);
        if (localComment == null)
          break;
        if (!StringUtil.isNullOrEmpty(localComment.getText()))
        {
          localComment.setMedia(this);
          localArrayList.add(localComment);
          continue;
        }
        this.mNumSkippedCommentsThatCouldNotBeParsed = (1 + this.mNumSkippedCommentsThatCouldNotBeParsed);
      }
      this.mAllComments = localArrayList;
    }
    while (true)
    {
      return;
      if ("comment_count".equals(str))
      {
        paramJsonParser.nextToken();
        this.mCommentCount = Integer.valueOf(paramJsonParser.getValueAsInt());
        continue;
      }
      if ("has_more_comments".equals(str))
      {
        paramJsonParser.nextToken();
        this.mHasMoreComments = paramJsonParser.getValueAsBoolean();
        continue;
      }
      if (!"caption".equals(str))
        continue;
      paramJsonParser.nextToken();
      this.mCaption = Comment.fromJsonParser(paramJsonParser);
      if (this.mCaption == null)
        continue;
      if (!StringUtil.isNullOrEmpty(this.mCaption.getText()))
      {
        this.mCaption.setMedia(this);
        continue;
      }
      this.mNumSkippedCommentsThatCouldNotBeParsed = (1 + this.mNumSkippedCommentsThatCouldNotBeParsed);
      this.mCaption = null;
    }
  }

  public void setDeletedStatus(int paramInt)
  {
    this.mDeletedStatus = paramInt;
  }

  public void setLocalBitmapUri(Uri paramUri)
  {
    this.mLocalBitmapUri = paramUri;
  }

  public void updateFields(Media paramMedia)
  {
    if (paramMedia.mId != null)
      this.mId = paramMedia.getId();
    if (paramMedia.mStandardResolutionUrl != null)
      this.mStandardResolutionUrl = paramMedia.getStandardResolutionUrl();
    if (paramMedia.mLowResolutionUrl != null)
      this.mLowResolutionUrl = paramMedia.mLowResolutionUrl;
    if (paramMedia.mThumbnailUrl != null)
      this.mThumbnailUrl = paramMedia.mThumbnailUrl;
    this.mTakenAt = paramMedia.mTakenAt;
    this.mFormattedDate = null;
    getFormattedDate();
    if (paramMedia.mUser != null)
      this.mUser = paramMedia.mUser;
    this.mLikers = paramMedia.mLikers;
    this.mHasLiked = paramMedia.mHasLiked;
    this.likeCount = paramMedia.likeCount;
    this.mAllComments = paramMedia.mAllComments;
    if (this.mAllComments != null)
    {
      Iterator localIterator = this.mAllComments.iterator();
      while (localIterator.hasNext())
        ((Comment)localIterator.next()).setMedia(this);
    }
    this.mVenue = paramMedia.mVenue;
    this.mLatitude = paramMedia.mLatitude;
    this.mLongitude = paramMedia.mLongitude;
    this.mCommentCount = paramMedia.mCommentCount;
    this.mCaption = paramMedia.mCaption;
    this.mHasMoreComments = paramMedia.mHasMoreComments;
    clearLikeText();
    clearCommentArrayCaches();
  }

  public void updatedHasLiked(boolean paramBoolean)
  {
    User localUser;
    if (this.mHasLiked != paramBoolean)
    {
      this.mHasLiked = paramBoolean;
      localUser = AuthHelper.getInstance().getCurrentUser();
      if (this.mLikers != null)
      {
        if (!paramBoolean)
          break label71;
        this.mLikers.add(localUser);
      }
      if (!paramBoolean)
        break label83;
    }
    label71: label83: for (int i = 1 + this.likeCount.intValue(); ; i = -1 + this.likeCount.intValue())
    {
      this.likeCount = Integer.valueOf(i);
      clearLikeText();
      broadcastUpdatedMedia();
      return;
      this.mLikers.remove(localUser);
      break;
    }
  }

  public static enum FeedItemLocationType
  {
    static
    {
      Foursquare = new FeedItemLocationType("Foursquare", 2);
      FeedItemLocationType[] arrayOfFeedItemLocationType = new FeedItemLocationType[3];
      arrayOfFeedItemLocationType[0] = Undefined;
      arrayOfFeedItemLocationType[1] = User;
      arrayOfFeedItemLocationType[2] = Foursquare;
      $VALUES = arrayOfFeedItemLocationType;
    }
  }

  private static enum MediaSize
  {
    static
    {
      HIGH_RES_PX = new MediaSize("HIGH_RES_PX", 1);
      MediaSize[] arrayOfMediaSize = new MediaSize[2];
      arrayOfMediaSize[0] = LOW_RES_PX;
      arrayOfMediaSize[1] = HIGH_RES_PX;
      $VALUES = arrayOfMediaSize;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.model.Media
 * JD-Core Version:    0.6.0
 */