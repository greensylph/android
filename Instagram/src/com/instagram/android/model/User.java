package com.instagram.android.model;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Toast;
import com.instagram.android.Preferences;
import com.instagram.android.listener.UserLinkClickListener;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.service.ClickManager;
import com.instagram.android.service.UserStore;
import com.instagram.android.widget.ClickableNameSpan;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiResponse;
import com.instagram.api.request.BlockStatusUpdateRequest;
import com.instagram.api.request.FollowStatusUpdateRequest;
import com.instagram.util.JSONUtil;
import com.instagram.util.StringUtil;
import java.io.IOException;
import java.util.ArrayList;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.TypeFactory;

public class User
{
  public static final String BROADCAST_EXTRA_ID = "id";
  private static final String COM_INSTAGRAM_BROADCASTS_UPDATED_FRIEND_STATUS = "com.instagram.broadcasts.updated_friend_status";
  private static final String COM_INSTAGRAM_BROADCASTS_UPDATED_USER = "com.instagram.broadcasts.updated_user";
  private static Handler broadcastUpdateHandler;
  private static ObjectMapper mUserObjectMapper;
  private String mBiography;
  private boolean mBlocking;
  private String mExternalUrl;
  private FollowStatus mFollowStatus = FollowStatus.FollowStatusUnknown;
  private Integer mFollowerCount;
  private Integer mFollowingCount;
  private String mFullName;
  private String mId;
  private Boolean mIncomingRequestPending;
  private boolean mIsStaff;
  private FollowStatus mLastFollowStatus = FollowStatus.FollowStatusUnknown;
  private Integer mMediaCount;
  private PrivacyStatus mPrivacyStatus;
  private String mProfilePicUrl;
  private String mUsername;

  private static void broadcastUpdate(String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.setAction(getUserBroadcastId(paramString));
    localIntent.putExtra("id", paramString);
    LocalBroadcastManager.getInstance(AppContext.getContext()).sendBroadcast(localIntent);
  }

  public static User fromJsonNode(JsonNode paramJsonNode, User paramUser)
  {
    if (paramUser == null)
      paramUser = new User();
    paramUser.mUsername = paramJsonNode.get("username").asText();
    paramUser.mFullName = StringUtil.stripEmoji(paramJsonNode.get("full_name").asText());
    paramUser.mProfilePicUrl = paramJsonNode.get("profile_pic_url").asText();
    paramUser.mId = JSONUtil.safeParsePK(paramJsonNode);
    if (paramJsonNode.get("is_staff") != null)
      paramUser.mIsStaff = paramJsonNode.get("is_staff").asBoolean();
    if ((paramJsonNode.get("biography") != null) && (!paramJsonNode.get("biography").isNull()))
    {
      String str = StringUtil.stripEmoji(paramJsonNode.get("biography").asText());
      if (!StringUtil.isNullOrEmpty(str))
        paramUser.mBiography = str;
    }
    if ((paramJsonNode.get("external_url") != null) && (!paramJsonNode.get("external_url").isNull()))
      paramUser.mExternalUrl = paramJsonNode.get("external_url").asText();
    if (paramJsonNode.get("follower_count") != null)
      paramUser.mFollowerCount = Integer.valueOf(paramJsonNode.get("follower_count").asInt());
    if (paramJsonNode.get("following_count") != null)
      paramUser.mFollowingCount = Integer.valueOf(paramJsonNode.get("following_count").asInt());
    if (paramJsonNode.get("media_count") != null)
      paramUser.mMediaCount = Integer.valueOf(paramJsonNode.get("media_count").asInt());
    if (paramJsonNode.get("is_private") != null)
      if (!paramJsonNode.get("is_private").asBoolean())
        break label271;
    label271: for (PrivacyStatus localPrivacyStatus = PrivacyStatus.PrivacyStatusPrivate; ; localPrivacyStatus = PrivacyStatus.PrivacyStatusPublic)
    {
      paramUser.mPrivacyStatus = localPrivacyStatus;
      return paramUser;
    }
  }

  public static User fromJsonParser(Context paramContext, JsonParser paramJsonParser)
    throws IOException
  {
    User localUser = null;
    while (paramJsonParser.nextToken() != JsonToken.END_OBJECT)
    {
      String str1 = paramJsonParser.getCurrentName();
      if ((str1 != null) && (localUser == null))
        localUser = new User();
      if ("username".equals(str1))
      {
        paramJsonParser.nextToken();
        localUser.mUsername = paramJsonParser.getText();
        continue;
      }
      if ("full_name".equals(str1))
      {
        paramJsonParser.nextToken();
        localUser.mFullName = StringUtil.stripEmoji(paramJsonParser.getText());
        continue;
      }
      if ("profile_pic_url".equals(str1))
      {
        paramJsonParser.nextToken();
        localUser.mProfilePicUrl = paramJsonParser.getText();
        continue;
      }
      if ("is_staff".equals(str1))
      {
        paramJsonParser.nextToken();
        localUser.mIsStaff = paramJsonParser.getBooleanValue();
        continue;
      }
      if ("biography".equals(str1))
      {
        paramJsonParser.nextToken();
        if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_NULL)
          continue;
        String str2 = StringUtil.stripEmoji(paramJsonParser.getText());
        if (StringUtil.isNullOrEmpty(str2))
          continue;
        localUser.mBiography = str2;
        continue;
      }
      if ("external_url".equals(str1))
      {
        paramJsonParser.nextToken();
        if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_NULL)
          continue;
        localUser.mExternalUrl = paramJsonParser.getText();
        continue;
      }
      if ("follower_count".equals(str1))
      {
        paramJsonParser.nextToken();
        localUser.mFollowerCount = Integer.valueOf(paramJsonParser.getIntValue());
        continue;
      }
      if ("following_count".equals(str1))
      {
        paramJsonParser.nextToken();
        localUser.mFollowingCount = Integer.valueOf(paramJsonParser.getIntValue());
        continue;
      }
      if ("media_count".equals(str1))
      {
        paramJsonParser.nextToken();
        localUser.mMediaCount = Integer.valueOf(paramJsonParser.getIntValue());
        continue;
      }
      if ("is_private".equals(str1))
      {
        paramJsonParser.nextToken();
        if (paramJsonParser.getBooleanValue());
        for (PrivacyStatus localPrivacyStatus = PrivacyStatus.PrivacyStatusPrivate; ; localPrivacyStatus = PrivacyStatus.PrivacyStatusPublic)
        {
          localUser.mPrivacyStatus = localPrivacyStatus;
          break;
        }
      }
      if ("id".equals(str1))
      {
        paramJsonParser.nextToken();
        localUser.mId = paramJsonParser.getText();
        continue;
      }
      if (!"pk".equals(str1))
        continue;
      paramJsonParser.nextToken();
      localUser.mId = paramJsonParser.getText();
    }
    UserStore localUserStore;
    if (localUser != null)
    {
      localUserStore = UserStore.getInstance(paramContext);
      if ((User)localUserStore.get(localUser.getId()) != null)
        break label448;
      localUserStore.put(localUser.getId(), localUser);
    }
    while (true)
    {
      return localUser;
      label448: localUser = localUserStore.update(localUser.getId(), localUser);
      localUser.broadcastCoalescedUpdate(localUser.getId());
    }
  }

  public static String getUserBroadcastId(String paramString)
  {
    return "com.instagram.broadcasts.updated_user|" + paramString;
  }

  public static String getUserFollowUpdateBroadcastId(String paramString)
  {
    return "com.instagram.broadcasts.updated_friend_status|" + paramString;
  }

  public static User getUserFromSerializedData(String paramString)
    throws IOException
  {
    return (User)getUserObjectMapper().readValue(paramString, User.class);
  }

  public static ArrayList<User> getUserListFromSerializedData(String paramString)
    throws IOException
  {
    CollectionType localCollectionType = getUserObjectMapper().getTypeFactory().constructCollectionType(ArrayList.class, User.class);
    return (ArrayList)getUserObjectMapper().readValue(paramString, localCollectionType);
  }

  public static String getUserListSerialized(ArrayList<User> paramArrayList)
    throws IOException
  {
    return getUserObjectMapper().writeValueAsString(paramArrayList);
  }

  private static ObjectMapper getUserObjectMapper()
  {
    if (mUserObjectMapper == null)
    {
      SimpleModule localSimpleModule = new SimpleModule("UserModule", Version.unknownVersion());
      localSimpleModule.addSerializer(User.class, new User.LocalJson.Serializer());
      localSimpleModule.addDeserializer(User.class, new User.LocalJson.Deserializer());
      mUserObjectMapper = new ObjectMapper().withModule(localSimpleModule);
    }
    return mUserObjectMapper;
  }

  public static void initialize()
  {
    broadcastUpdateHandler = new Handler()
    {
      public void handleMessage(Message paramMessage)
      {
        User.access$000(paramMessage.getData().getString("id"));
      }
    };
  }

  public void broadcastCoalescedUpdate(String paramString)
  {
    Message localMessage = broadcastUpdateHandler.obtainMessage(paramString.hashCode());
    Bundle localBundle = new Bundle();
    localBundle.putString("id", paramString);
    localMessage.setData(localBundle);
    broadcastUpdateHandler.removeMessages(paramString.hashCode());
    broadcastUpdateHandler.sendMessageDelayed(localMessage, 1000L);
  }

  public void broadcastUpdate()
  {
    broadcastUpdate(getId());
  }

  void createUserLink(SpannableStringBuilder paramSpannableStringBuilder, int paramInt)
  {
    paramSpannableStringBuilder.append(getUsername());
    paramSpannableStringBuilder.setSpan(new ClickableNameSpan()
    {
      public void onClick(View paramView)
      {
        ClickManager.getInstance().getUserLinkClickListener().onClick(User.this);
      }
    }
    , paramInt, paramInt + getUsername().length(), 33);
  }

  public boolean equals(Object paramObject)
  {
    int i = 1;
    if (this == paramObject);
    User localUser;
    do
    {
      while (true)
      {
        return i;
        if ((paramObject != null) && (getClass() == paramObject.getClass()))
          break;
        i = 0;
      }
      localUser = (User)paramObject;
      if (this.mId == null)
        break;
    }
    while (this.mId.equals(localUser.mId));
    while (true)
    {
      i = 0;
      break;
      if (localUser.mId == null)
        break;
    }
  }

  public String getBiography()
  {
    return this.mBiography;
  }

  public String getExternalUrl()
  {
    return this.mExternalUrl;
  }

  public FollowStatus getFollowStatus()
  {
    return this.mFollowStatus;
  }

  public Integer getFollowerCount()
  {
    return this.mFollowerCount;
  }

  public Integer getFollowingCount()
  {
    return this.mFollowingCount;
  }

  public String getFullName()
  {
    return this.mFullName;
  }

  public String getFullNameOrUsername()
  {
    if ((this.mFullName != null) && (!this.mFullName.equals("")));
    for (String str = this.mFullName; ; str = getUsername())
      return str;
  }

  public String getId()
  {
    return this.mId;
  }

  public FollowStatus getLastFollowStatus()
  {
    return this.mLastFollowStatus;
  }

  public Integer getMediaCount()
  {
    return this.mMediaCount;
  }

  public PrivacyStatus getPrivacyStatus()
  {
    return this.mPrivacyStatus;
  }

  public String getProfilePicUrl()
  {
    return this.mProfilePicUrl;
  }

  public String getUserSerialized()
    throws IOException
  {
    return getUserObjectMapper().writeValueAsString(this);
  }

  public String getUsername()
  {
    return this.mUsername;
  }

  public int hashCode()
  {
    if (this.mId != null);
    for (int i = this.mId.hashCode(); ; i = 0)
      return i;
  }

  public boolean isBlocking()
  {
    return this.mBlocking;
  }

  public boolean isIncomingRequestPending()
  {
    if ((this.mIncomingRequestPending != null) && (this.mIncomingRequestPending.booleanValue()));
    for (int i = 1; ; i = 0)
      return i;
  }

  public boolean isIsStaff()
  {
    return this.mIsStaff;
  }

  public boolean isSelf(Context paramContext)
  {
    String str = Preferences.getInstance(paramContext).getUserId();
    if ((getId() != null) && (str != null) && (str.equals(getId())));
    for (int i = 1; ; i = 0)
      return i;
  }

  public void revertBlockStatus(Context paramContext)
  {
    if (!this.mBlocking);
    for (boolean bool = true; ; bool = false)
    {
      this.mBlocking = bool;
      broadcastUpdate();
      return;
    }
  }

  public void revertFollowStatus(Context paramContext)
  {
    if (this.mLastFollowStatus != null)
      updateFollowStatus(this.mLastFollowStatus, paramContext);
    this.mLastFollowStatus = null;
  }

  public void setPrivacyStatus(PrivacyStatus paramPrivacyStatus)
  {
    this.mPrivacyStatus = paramPrivacyStatus;
  }

  public void toggleBlockStatus(Context paramContext, LoaderManager paramLoaderManager)
  {
    String str;
    if (isBlocking())
    {
      str = "unblock";
      if (this.mBlocking)
        break label62;
    }
    label62: for (boolean bool = true; ; bool = false)
    {
      this.mBlocking = bool;
      broadcastUpdate();
      new BlockStatusUpdateRequest(paramContext, paramLoaderManager, new AbstractApiCallbacks(paramContext)
      {
        protected void onRequestFail(ApiResponse<Void> paramApiResponse)
        {
          Context localContext = this.val$context;
          Resources localResources = this.val$context.getResources();
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = this.val$context.getResources().getString(2131230975);
          Toast.makeText(localContext, localResources.getString(2131230974, arrayOfObject), 0).show();
        }

        protected void onSuccess(Void paramVoid)
        {
          Context localContext = this.val$context;
          if (User.this.mBlocking);
          for (int i = 2131230976; ; i = 2131230977)
          {
            Toast.makeText(localContext, i, 0).show();
            return;
          }
        }
      }).perform(this, str);
      return;
      str = "block";
      break;
    }
  }

  public void toggleFollowStatus(Context paramContext, LoaderManager paramLoaderManager)
  {
    FollowStatus localFollowStatus1 = this.mFollowStatus;
    UserAction localUserAction;
    FollowStatus localFollowStatus2;
    switch (4.$SwitchMap$com$instagram$android$model$User$FollowStatus[localFollowStatus1.ordinal()])
    {
    default:
      return;
    case 1:
      localUserAction = UserAction.UserActionUnfollow;
      localFollowStatus2 = FollowStatus.FollowStatusNotFollowing;
    case 2:
    case 3:
    }
    while (true)
    {
      updateFollowStatus(localFollowStatus2, paramContext);
      new FollowStatusUpdateRequest(paramContext, paramLoaderManager, localUserAction, this).perform();
      break;
      localUserAction = UserAction.UserActionFollow;
      if ((this.mPrivacyStatus == PrivacyStatus.PrivacyStatusPrivate) || (this.mPrivacyStatus == PrivacyStatus.PrivacyStatusUnknown))
      {
        localFollowStatus2 = FollowStatus.FollowStatusRequested;
        continue;
      }
      localFollowStatus2 = FollowStatus.FollowStatusFollowing;
      continue;
      localUserAction = UserAction.UserActionCancelRequest;
      localFollowStatus2 = FollowStatus.FollowStatusNotFollowing;
    }
  }

  public void updateFields(User paramUser)
  {
    if (paramUser.getId() != null)
      this.mId = paramUser.getId();
    if (paramUser.getUsername() != null)
      this.mUsername = paramUser.getUsername();
    if (paramUser.getFullName() != null)
      this.mFullName = paramUser.getFullName();
    if (paramUser.getProfilePicUrl() != null)
      this.mProfilePicUrl = paramUser.getProfilePicUrl();
    if (paramUser.isIsStaff() != this.mIsStaff)
      this.mIsStaff = paramUser.isIsStaff();
    if (paramUser.getBiography() != null)
      this.mBiography = paramUser.getBiography();
    if (paramUser.getExternalUrl() != null)
      this.mExternalUrl = paramUser.getExternalUrl();
    if (paramUser.getFollowerCount() != null)
      this.mFollowerCount = paramUser.getFollowerCount();
    if (paramUser.getFollowingCount() != null)
      this.mFollowingCount = paramUser.getFollowingCount();
    if (paramUser.getMediaCount() != null)
      this.mMediaCount = paramUser.getMediaCount();
    if (paramUser.getPrivacyStatus() != this.mPrivacyStatus)
      this.mPrivacyStatus = paramUser.getPrivacyStatus();
    if (paramUser.mFollowStatus != FollowStatus.FollowStatusUnknown)
    {
      this.mFollowStatus = paramUser.mFollowStatus;
      this.mLastFollowStatus = paramUser.mLastFollowStatus;
    }
    if (paramUser.mIncomingRequestPending != null)
      this.mIncomingRequestPending = paramUser.mIncomingRequestPending;
  }

  public void updateFollowStatus(FollowStatus paramFollowStatus, Context paramContext)
  {
    this.mLastFollowStatus = this.mFollowStatus;
    this.mFollowStatus = paramFollowStatus;
    if ((this.mFollowerCount != null) && (this.mLastFollowStatus != null) && (this.mLastFollowStatus != FollowStatus.FollowStatusFetching) && (paramFollowStatus != this.mLastFollowStatus))
    {
      if (!paramFollowStatus.equals(FollowStatus.FollowStatusFollowing))
        break label177;
      this.mFollowerCount = Integer.valueOf(1 + this.mFollowerCount.intValue());
      broadcastUpdate();
    }
    while (true)
    {
      if (paramFollowStatus != FollowStatus.FollowStatusUnknown)
      {
        Intent localIntent1 = new Intent();
        localIntent1.setAction(getUserFollowUpdateBroadcastId(getId()));
        localIntent1.putExtra("id", getId());
        LocalBroadcastManager.getInstance(paramContext).sendBroadcast(localIntent1);
        Intent localIntent2 = new Intent();
        localIntent2.setAction(getUserFollowUpdateBroadcastId(AuthHelper.getInstance().getCurrentUser().getId()));
        localIntent2.putExtra("id", getId());
        LocalBroadcastManager.getInstance(paramContext).sendBroadcast(localIntent2);
      }
      return;
      label177: if (!paramFollowStatus.equals(FollowStatus.FollowStatusNotFollowing))
        continue;
      this.mFollowerCount = Integer.valueOf(-1 + this.mFollowerCount.intValue());
      broadcastUpdate();
    }
  }

  public void updateFollowStatus(JsonNode paramJsonNode, Context paramContext)
  {
    boolean bool1 = true;
    boolean bool2 = paramJsonNode.get("following").asBoolean();
    boolean bool3;
    if ((paramJsonNode.get("outgoing_request") != null) && (paramJsonNode.get("outgoing_request").asBoolean()))
    {
      bool3 = bool1;
      this.mLastFollowStatus = this.mFollowStatus;
      if (!bool3)
        break label234;
      this.mFollowStatus = FollowStatus.FollowStatusRequested;
      label60: if ((paramJsonNode.get("incoming_request") == null) || (!paramJsonNode.get("incoming_request").asBoolean()))
        break label259;
      label83: this.mIncomingRequestPending = Boolean.valueOf(bool1);
      if (!paramJsonNode.get("is_private").asBoolean())
        break label264;
    }
    label259: label264: for (this.mPrivacyStatus = PrivacyStatus.PrivacyStatusPrivate; ; this.mPrivacyStatus = PrivacyStatus.PrivacyStatusPublic)
    {
      if (paramJsonNode.get("blocking") != null)
        this.mBlocking = paramJsonNode.get("blocking").asBoolean();
      Intent localIntent1 = new Intent();
      localIntent1.setAction(getUserFollowUpdateBroadcastId(getId()));
      localIntent1.putExtra("id", getId());
      LocalBroadcastManager.getInstance(paramContext).sendBroadcast(localIntent1);
      Intent localIntent2 = new Intent();
      localIntent2.setAction(getUserFollowUpdateBroadcastId(AuthHelper.getInstance().getCurrentUser().getId()));
      localIntent2.putExtra("id", getId());
      LocalBroadcastManager.getInstance(paramContext).sendBroadcast(localIntent2);
      return;
      bool3 = false;
      break;
      label234: if (bool2)
      {
        this.mFollowStatus = FollowStatus.FollowStatusFollowing;
        break label60;
      }
      this.mFollowStatus = FollowStatus.FollowStatusNotFollowing;
      break label60;
      bool1 = false;
      break label83;
    }
  }

  public void updateFollowStatus(JsonParser paramJsonParser, Context paramContext)
    throws JsonParseException, IOException
  {
    boolean bool1 = false;
    boolean bool2 = false;
    this.mPrivacyStatus = PrivacyStatus.PrivacyStatusPublic;
    while (paramJsonParser.nextToken() != JsonToken.END_OBJECT)
    {
      String str = paramJsonParser.getCurrentName();
      if ("following".equals(str))
      {
        paramJsonParser.nextToken();
        bool1 = paramJsonParser.getValueAsBoolean();
        continue;
      }
      if ("outgoing_request".equals(str))
      {
        paramJsonParser.nextToken();
        bool2 = paramJsonParser.getValueAsBoolean();
        continue;
      }
      if ("incoming_request".equals(str))
      {
        paramJsonParser.nextToken();
        this.mIncomingRequestPending = Boolean.valueOf(paramJsonParser.getValueAsBoolean());
        continue;
      }
      if ("is_private".equals(str))
      {
        paramJsonParser.nextToken();
        if (!paramJsonParser.getValueAsBoolean())
          continue;
        this.mPrivacyStatus = PrivacyStatus.PrivacyStatusPrivate;
        continue;
      }
      if (!"is_private".equals(str))
        continue;
      paramJsonParser.nextToken();
      this.mBlocking = paramJsonParser.getValueAsBoolean();
    }
    this.mLastFollowStatus = this.mFollowStatus;
    if (bool2)
      this.mFollowStatus = FollowStatus.FollowStatusRequested;
    while (true)
    {
      Intent localIntent1 = new Intent();
      localIntent1.setAction(getUserFollowUpdateBroadcastId(getId()));
      localIntent1.putExtra("id", getId());
      LocalBroadcastManager.getInstance(paramContext).sendBroadcast(localIntent1);
      Intent localIntent2 = new Intent();
      localIntent2.setAction(getUserFollowUpdateBroadcastId(AuthHelper.getInstance().getCurrentUser().getId()));
      localIntent2.putExtra("id", getId());
      LocalBroadcastManager.getInstance(paramContext).sendBroadcast(localIntent2);
      return;
      if (bool1)
      {
        this.mFollowStatus = FollowStatus.FollowStatusFollowing;
        continue;
      }
      this.mFollowStatus = FollowStatus.FollowStatusNotFollowing;
    }
  }

  public static enum FollowStatus
  {
    static
    {
      FollowStatusFetching = new FollowStatus("FollowStatusFetching", 1);
      FollowStatusNotFollowing = new FollowStatus("FollowStatusNotFollowing", 2);
      FollowStatusFollowing = new FollowStatus("FollowStatusFollowing", 3);
      FollowStatusRequested = new FollowStatus("FollowStatusRequested", 4);
      FollowStatusInProgress = new FollowStatus("FollowStatusInProgress", 5);
      FollowStatusDoesNotExist = new FollowStatus("FollowStatusDoesNotExist", 6);
      FollowStatus[] arrayOfFollowStatus = new FollowStatus[7];
      arrayOfFollowStatus[0] = FollowStatusUnknown;
      arrayOfFollowStatus[1] = FollowStatusFetching;
      arrayOfFollowStatus[2] = FollowStatusNotFollowing;
      arrayOfFollowStatus[3] = FollowStatusFollowing;
      arrayOfFollowStatus[4] = FollowStatusRequested;
      arrayOfFollowStatus[5] = FollowStatusInProgress;
      arrayOfFollowStatus[6] = FollowStatusDoesNotExist;
      $VALUES = arrayOfFollowStatus;
    }
  }

  public static class LocalJson
  {
    private static final String JSON_KEY_BIOGRAPHY = "biography";
    private static final String JSON_KEY_BLOCKING = "blocking";
    private static final String JSON_KEY_EXTERNAL_URL = "external_url";
    private static final String JSON_KEY_FOLLOWER_COUNT = "follower_count";
    private static final String JSON_KEY_FOLLOWING_COUNT = "following_count";
    private static final String JSON_KEY_FOLLOW_STATUS = "follow_status";
    private static final String JSON_KEY_FULL_NAME = "full_name";
    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_IS_STAFF = "is_staff";
    private static final String JSON_KEY_LAST_FOLLOW_STATUS = "last_follow_status";
    private static final String JSON_KEY_MEDIA_COUNT = "media_count";
    private static final String JSON_KEY_PRIVACY_STATUS = "privacy_status";
    private static final String JSON_KEY_PROFILE_PIC_URL = "profile_pic_url";
    private static final String JSON_KEY_USERNAME = "username";

    public static class Deserializer extends JsonDeserializer<User>
    {
      public User deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
        throws IOException, JsonProcessingException
      {
        ObjectMapper localObjectMapper = (ObjectMapper)paramJsonParser.getCodec();
        JsonNode localJsonNode = (JsonNode)paramJsonParser.readValueAs(JsonNode.class);
        User localUser = new User();
        User.access$202(localUser, localJsonNode.get("id").asText());
        if (localJsonNode.has("biography"))
          if (!localJsonNode.get("biography").isNull())
            break label319;
        label319: for (String str = null; ; str = localJsonNode.get("biography").asText())
        {
          User.access$302(localUser, str);
          User.access$102(localUser, localJsonNode.get("blocking").asBoolean());
          if (localJsonNode.has("external_url"))
            User.access$402(localUser, localJsonNode.get("external_url").asText());
          User.access$502(localUser, (Integer)localObjectMapper.readValue(localJsonNode.get("follower_count"), Integer.class));
          User.access$602(localUser, (Integer)localObjectMapper.readValue(localJsonNode.get("following_count"), Integer.class));
          User.access$702(localUser, (User.FollowStatus)localObjectMapper.readValue(localJsonNode.get("follow_status"), User.FollowStatus.class));
          User.access$802(localUser, localJsonNode.get("full_name").asText());
          User.access$902(localUser, localJsonNode.get("is_staff").asBoolean());
          User.access$1002(localUser, (User.FollowStatus)localObjectMapper.readValue(localJsonNode.get("last_follow_status"), User.FollowStatus.class));
          User.access$1102(localUser, (Integer)localObjectMapper.readValue(localJsonNode.get("media_count"), Integer.class));
          User.access$1202(localUser, (User.PrivacyStatus)localObjectMapper.readValue(localJsonNode.get("privacy_status"), User.PrivacyStatus.class));
          User.access$1302(localUser, localJsonNode.get("profile_pic_url").asText());
          User.access$1402(localUser, localJsonNode.get("username").asText());
          return localUser;
        }
      }
    }

    public static class Serializer extends JsonSerializer<User>
    {
      public void serialize(User paramUser, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
        throws IOException, JsonProcessingException
      {
        paramJsonGenerator.writeStartObject();
        paramJsonGenerator.writeStringField("id", paramUser.getId());
        if (paramUser.getBiography() != null)
          paramJsonGenerator.writeStringField("biography", paramUser.getBiography());
        paramJsonGenerator.writeBooleanField("blocking", paramUser.isBlocking());
        if (paramUser.getExternalUrl() != null)
          paramJsonGenerator.writeStringField("external_url", paramUser.getExternalUrl());
        paramJsonGenerator.writeObjectField("follower_count", paramUser.getFollowerCount());
        paramJsonGenerator.writeObjectField("following_count", paramUser.getFollowingCount());
        paramJsonGenerator.writeObjectField("follow_status", paramUser.getFollowStatus());
        paramJsonGenerator.writeStringField("full_name", paramUser.getFullName());
        paramJsonGenerator.writeBooleanField("is_staff", paramUser.isIsStaff());
        paramJsonGenerator.writeObjectField("last_follow_status", paramUser.getLastFollowStatus());
        paramJsonGenerator.writeObjectField("media_count", paramUser.getMediaCount());
        paramJsonGenerator.writeObjectField("privacy_status", paramUser.getPrivacyStatus());
        paramJsonGenerator.writeStringField("profile_pic_url", paramUser.getProfilePicUrl());
        paramJsonGenerator.writeStringField("username", paramUser.getUsername());
        paramJsonGenerator.writeEndObject();
      }
    }
  }

  public static enum PrivacyStatus
  {
    static
    {
      PrivacyStatusPublic = new PrivacyStatus("PrivacyStatusPublic", 1);
      PrivacyStatusPrivate = new PrivacyStatus("PrivacyStatusPrivate", 2);
      PrivacyStatus[] arrayOfPrivacyStatus = new PrivacyStatus[3];
      arrayOfPrivacyStatus[0] = PrivacyStatusUnknown;
      arrayOfPrivacyStatus[1] = PrivacyStatusPublic;
      arrayOfPrivacyStatus[2] = PrivacyStatusPrivate;
      $VALUES = arrayOfPrivacyStatus;
    }
  }

  public static enum UserAction
  {
    static
    {
      UserActionCancelRequest = new UserAction("UserActionCancelRequest", 2);
      UserActionIgnore = new UserAction("UserActionIgnore", 3);
      UserActionApprove = new UserAction("UserActionApprove", 4);
      UserAction[] arrayOfUserAction = new UserAction[5];
      arrayOfUserAction[0] = UserActionFollow;
      arrayOfUserAction[1] = UserActionUnfollow;
      arrayOfUserAction[2] = UserActionCancelRequest;
      arrayOfUserAction[3] = UserActionIgnore;
      arrayOfUserAction[4] = UserActionApprove;
      $VALUES = arrayOfUserAction;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.model.User
 * JD-Core Version:    0.6.0
 */