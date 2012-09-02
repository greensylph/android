package com.instagram.android.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.StatusLine;
import ch.boye.httpclientandroidlib.util.EntityUtils;
import com.facebook.android.Facebook;
import com.instagram.android.Log;
import com.instagram.android.Preferences;
import com.instagram.android.location.Venue;
import com.instagram.android.media.PendingMedia;
import com.instagram.android.model.Media;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.RequestParams;
import com.instagram.api.request.ApiUrlHelper;
import com.instagram.facebook.FacebookAccount;
import com.instagram.foursquare.FoursquareAccount;
import com.instagram.tumblr.TumblrAccount;
import com.instagram.twitter.TwitterAccount;
import com.instagram.util.JsonBuilder;
import com.instagram.util.RequestUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.codehaus.jackson.type.TypeReference;

public class PendingMediaService extends IntentService
{
  public static final String FILENAME = "pending_media.json";
  public static final String INTENT_ACTION_NEW_USER_MEDIA = "com.instagram.android.PendingMediaService.INTENT_ACTION_NEW_USER_MEDIA";
  public static final String INTENT_ACTION_PENDING_MEDIA_CHANGED = "com.instagram.android.PendingMediaService.INTENT_ACTION_PENDING_MEDIA_CHANGED";
  private static final String INTENT_EXTRA_PENDING_MEDIA_KEY = "com.instagram.android.PendingMediaService.INTENT_EXTRA_PENDING_MEDIA_ID";
  private static final String INTENT_EXTRA_PERFORM_ACTION = "com.instagram.android.PendingMediaService.INTENT_EXTRA_PERFORM_ACTION";
  public static final String JSON_KEY_ADDRESS = "address";
  public static final String JSON_KEY_CAPTION = "caption";
  public static final String JSON_KEY_EXTERNAL_ID = "externalId";
  public static final String JSON_KEY_EXTERNAL_SOURCE = "externalSource";
  public static final String JSON_KEY_FACEBOOK_ENABLED = "facebookEnabled";
  public static final String JSON_KEY_FILTER_TYPE_ID = "filterTypeOrdinal";
  public static final String JSON_KEY_FOURSQUARE_ENABLED = "foursquareEnabled";
  public static final String JSON_KEY_ID = "id";
  public static final String JSON_KEY_IMAGE_FILE_PATH = "imageFilePath";
  public static final String JSON_KEY_KEY = "key";
  public static final String JSON_KEY_LATITUDE = "latitude";
  public static final String JSON_KEY_LOCATION = "location";
  public static final String JSON_KEY_LONGITUDE = "longitude";
  public static final String JSON_KEY_NAME = "name";
  public static final String JSON_KEY_NEEDS_CONFIGURE = "needsConfigure";
  public static final String JSON_KEY_NEEDS_UPLOAD = "needsUpload";
  public static final String JSON_KEY_SOURCE_TYPE = "sourceType";
  public static final String JSON_KEY_THUMBNAIL_SIZE = "thumbnailSize";
  public static final String JSON_KEY_TUMBLR_ENABLED = "tumblrEnabled";
  public static final String JSON_KEY_TWITTER_ENABLED = "twitterEnabled";
  private static final int PERFORM_ACTION_CANCEL = 3;
  private static final int PERFORM_ACTION_CONFIGURE = 1;
  private static final int PERFORM_ACTION_RETRY = 2;
  private static final int PERFORM_ACTION_UPLOAD = 0;
  public static final String TAG = "PendingMediaService";
  private static Map<String, PendingMedia> sPendingMediaMap;
  private final IBinder mBinder = new ServiceBinder();

  public PendingMediaService()
  {
    super("PendingMediaService");
  }

  public static void cancel(Context paramContext, PendingMedia paramPendingMedia)
  {
    startService(paramContext, paramPendingMedia, 3);
  }

  private void cancelInternal(PendingMedia paramPendingMedia)
  {
    removeMedia(paramPendingMedia.getDeviceTimestamp());
  }

  public static void configure(Context paramContext, PendingMedia paramPendingMedia)
  {
    paramPendingMedia.setStatus(1);
    startService(paramContext, paramPendingMedia, 1);
  }

  private boolean configureInternal(PendingMedia paramPendingMedia)
    throws IOException
  {
    JsonBuilder localJsonBuilder = new JsonBuilder();
    if (paramPendingMedia.getMediaId() != null)
      localJsonBuilder.put("media_id", paramPendingMedia.getMediaId());
    localJsonBuilder.put("caption", paramPendingMedia.getCaption());
    AutoCompleteHashtagService.addTagsFromText(paramPendingMedia.getCaption());
    localJsonBuilder.put("device_timestamp", paramPendingMedia.getDeviceTimestamp());
    String str1;
    switch (paramPendingMedia.getSourceType().intValue())
    {
    default:
      str1 = "4";
    case 1:
    }
    while (true)
    {
      localJsonBuilder.put("source_type", str1);
      localJsonBuilder.put("filter_type", paramPendingMedia.getFilterType().toString());
      if (paramPendingMedia.isGeotagEnabled())
      {
        localJsonBuilder.put("geotag_enabled", "1");
        localJsonBuilder.put("media_latitude", paramPendingMedia.getLatitude().toString());
        localJsonBuilder.put("media_longitude", paramPendingMedia.getLongitude().toString());
      }
      if (!paramPendingMedia.isTwitterEnabled())
        break;
      Iterator localIterator = TwitterAccount.get(this).getSharingInfo().entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localJsonBuilder.put((String)localEntry.getKey(), (String)localEntry.getValue());
      }
      if (Preferences.getInstance(this).getHasAdvancedCameraEnabled())
      {
        str1 = "3";
        continue;
      }
      str1 = "5";
    }
    if (paramPendingMedia.isFacebookEnabled())
    {
      Facebook localFacebook = FacebookAccount.getFacebook();
      localJsonBuilder.put("share_to_facebook", "1");
      localJsonBuilder.put("fb_access_token", localFacebook.getAccessToken());
    }
    if (paramPendingMedia.isFoursquareEnabled())
    {
      FoursquareAccount localFoursquareAccount = FoursquareAccount.get(this);
      localJsonBuilder.put("share_to_foursquare", "1");
      localJsonBuilder.put("foursquare_access_token", localFoursquareAccount.getAccessToken());
    }
    if (paramPendingMedia.isTumblrEnabled())
    {
      TumblrAccount localTumblrAccount = TumblrAccount.get(this);
      localJsonBuilder.put("share_to_tumblr", "1");
      localJsonBuilder.put("tumblr_access_token_key", localTumblrAccount.getToken());
      localJsonBuilder.put("tumblr_access_token_secret", localTumblrAccount.getSecret());
    }
    Venue localVenue = paramPendingMedia.getVenue();
    ObjectMapper localObjectMapper;
    Object localObject;
    if (localVenue != null)
    {
      SimpleModule localSimpleModule = new SimpleModule("LocationModule", Version.unknownVersion());
      localSimpleModule.addSerializer(Venue.class, new UploadLocationSerializer());
      localObjectMapper = new ObjectMapper().withModule(localSimpleModule);
      localObject = null;
    }
    int i;
    HttpEntity localHttpEntity;
    CustomObjectMapper localCustomObjectMapper;
    JsonNode localJsonNode1;
    JsonNode localJsonNode2;
    try
    {
      String str3 = localObjectMapper.writeValueAsString(localVenue);
      localObject = str3;
      if (localObject != null)
        localJsonBuilder.put("location", localObject);
      RequestParams localRequestParams = new RequestParams();
      RequestUtil.setSignedBody(localRequestParams, localJsonBuilder.toString());
      localHttpResponse = ApiHttpClient.getInstance(this).post(ApiUrlHelper.expandPath("media/configure/", true), localRequestParams);
      if ((localHttpResponse.getStatusLine() == null) || (localHttpResponse.getStatusLine().getStatusCode() != 200))
      {
        i = 0;
        Log.e("PendingMediaService", "Bad response from server");
        return i;
      }
    }
    catch (IOException localIOException)
    {
      HttpResponse localHttpResponse;
      while (true)
        Log.e("PendingMediaService", "Could not serialize location", localIOException);
      localHttpEntity = localHttpResponse.getEntity();
      String str2 = EntityUtils.toString(localHttpEntity);
      localCustomObjectMapper = CustomObjectMapper.getInstance(this);
      localJsonNode1 = (JsonNode)localCustomObjectMapper.readValue(str2, JsonNode.class);
      localJsonNode2 = localJsonNode1.get("message");
      if (localJsonNode2 == null)
        break label634;
    }
    if ("media_needs_reupload".equalsIgnoreCase(localJsonNode2.asText()))
    {
      i = 0;
      reupload(this, paramPendingMedia);
    }
    while (true)
    {
      EntityUtils.consume(localHttpEntity);
      break;
      label634: i = 1;
      paramPendingMedia.setNeedsConfigure(false);
      paramPendingMedia.setNeedsUpload(false);
      Media localMedia = (Media)localCustomObjectMapper.readValue(localJsonNode1.get("media"), Media.class);
      localMedia.setLocalBitmapUri(Uri.fromFile(new File(paramPendingMedia.getImageFilePath())));
      sendNewMediaBroadcast(localMedia, paramPendingMedia);
    }
  }

  private void doConfigure(PendingMedia paramPendingMedia)
  {
    if (paramPendingMedia.getNeedsUpload());
    while (true)
    {
      return;
      try
      {
        boolean bool3 = configureInternal(paramPendingMedia);
        bool1 = bool3;
        if (!bool1)
        {
          bool2 = true;
          paramPendingMedia.setNeedsConfigure(bool2);
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          boolean bool1 = false;
          Log.e("PendingMediaService", "Media failed configure", localException);
          continue;
          boolean bool2 = false;
        }
      }
    }
  }

  private void doRetry(PendingMedia paramPendingMedia)
  {
    try
    {
      retryInternal(paramPendingMedia);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Log.e("PendingMediaService", "Media failed retry", localException);
    }
  }

  private static void doSerializePendingMedia()
  {
    Context localContext = AppContext.getContext();
    localContext.deleteFile("pending_media.json");
    Object localObject1 = null;
    try
    {
      FileOutputStream localFileOutputStream = localContext.openFileOutput("pending_media.json", 0);
      localObject1 = localFileOutputStream;
      if (localObject1 == null)
      {
        Log.e("PendingMediaService", "Failed to acquire output stream for pending_media.json");
        return;
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      while (true)
      {
        Log.e("PendingMediaService", "File not found wile getting output stream for pending_media.json", localFileNotFoundException);
        continue;
        if (sPendingMediaMap == null)
        {
          Log.e("PendingMediaService", "Pending media map is null during serialization");
          continue;
        }
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = sPendingMediaMap.values().iterator();
        while (localIterator.hasNext())
        {
          PendingMedia localPendingMedia = (PendingMedia)localIterator.next();
          if ((localPendingMedia.getStatus() != 1) || (!localPendingMedia.getNeedsConfigure()))
            continue;
          localArrayList.add(localPendingMedia);
        }
        if (localArrayList.size() == 0)
          continue;
        SimpleModule localSimpleModule = new SimpleModule("PendingMediaModule", Version.unknownVersion());
        localSimpleModule.addSerializer(PendingMedia.class, new PendingMediaSerializer());
        localSimpleModule.addSerializer(Venue.class, new VenueSerializer());
        ObjectMapper localObjectMapper = new ObjectMapper().withModule(localSimpleModule);
        try
        {
          localObjectMapper.writeValue(localObject1, localArrayList);
          try
          {
            localObject1.close();
          }
          catch (IOException localIOException4)
          {
          }
          continue;
        }
        catch (IOException localIOException2)
        {
          Log.e("PendingMediaService", "Exception while writing out pending_media.json", localIOException2);
          try
          {
            localObject1.close();
          }
          catch (IOException localIOException3)
          {
          }
          continue;
        }
        finally
        {
        }
      }
    }
    try
    {
      localObject1.close();
      label264: throw localObject2;
    }
    catch (IOException localIOException1)
    {
      break label264;
    }
  }

  private void doUpload(PendingMedia paramPendingMedia)
  {
    try
    {
      boolean bool3 = uploadInternal(paramPendingMedia);
      bool1 = bool3;
      if (!bool1)
      {
        bool2 = true;
        paramPendingMedia.setNeedsUpload(bool2);
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        boolean bool1 = false;
        Log.e("PendingMediaService", "Media failed upload", localException);
        continue;
        boolean bool2 = false;
      }
    }
  }

  public static void filterPartialPendingMedia()
  {
    Map localMap = getPendingMediaMap();
    Iterator localIterator = getPendingMedia().iterator();
    while (localIterator.hasNext())
    {
      PendingMedia localPendingMedia = (PendingMedia)localIterator.next();
      if (localPendingMedia.getStatus() == 1)
        continue;
      localMap.remove(localPendingMedia.getKey());
    }
  }

  private int getAction(Intent paramIntent)
  {
    return paramIntent.getIntExtra("com.instagram.android.PendingMediaService.INTENT_EXTRA_PERFORM_ACTION", 0);
  }

  public static PendingMedia getMedia(String paramString)
  {
    return (PendingMedia)getPendingMediaMap().get(paramString);
  }

  private String getMediaKey(Intent paramIntent)
  {
    return paramIntent.getStringExtra("com.instagram.android.PendingMediaService.INTENT_EXTRA_PENDING_MEDIA_ID");
  }

  public static ArrayList<PendingMedia> getPendingMedia()
  {
    return new ArrayList(getPendingMediaMap().values());
  }

  private static Map<String, PendingMedia> getPendingMediaMap()
  {
    if (sPendingMediaMap == null)
    {
      sPendingMediaMap = new ConcurrentHashMap();
      List localList = loadSerializedPendingMediaList();
      if (localList != null)
      {
        Log.d("PendingMediaService", "Loading serialized pendign media list, size: " + localList.size());
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
        {
          PendingMedia localPendingMedia = (PendingMedia)localIterator.next();
          if (localPendingMedia == null)
            continue;
          putMedia(localPendingMedia);
        }
      }
    }
    return sPendingMediaMap;
  }

  public static boolean hasPendingMedia()
  {
    if (getPendingMediaMap().size() > 0);
    for (int i = 1; ; i = 0)
      return i;
  }

  private void internalHandleIntent(Intent paramIntent)
  {
    int i = getAction(paramIntent);
    PendingMedia localPendingMedia = getMedia(getMediaKey(paramIntent));
    if (localPendingMedia == null)
      Log.e("PendingMediaService", "Pending media did not exist on handle intent");
    while (true)
    {
      return;
      if (i != 3)
        break;
      cancelInternal(localPendingMedia);
    }
    localPendingMedia.setInProgress(true);
    sendPendingMediaChangeBroadcast();
    switch (i)
    {
    default:
      throw new UnsupportedOperationException("Unhandled action type");
    case 1:
      doConfigure(localPendingMedia);
    case 0:
    case 2:
    }
    while (true)
    {
      localPendingMedia.setInProgress(false);
      sendPendingMediaChangeBroadcast();
      break;
      doUpload(localPendingMedia);
      continue;
      doRetry(localPendingMedia);
    }
  }

  private static List<PendingMedia> loadSerializedPendingMediaList()
  {
    Context localContext = AppContext.getContext();
    Object localObject1 = null;
    try
    {
      FileInputStream localFileInputStream = localContext.openFileInput("pending_media.json");
      localObject1 = localFileInputStream;
      if (localObject1 == null)
      {
        localList = null;
        return localList;
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      while (true)
      {
        Log.d("PendingMediaService", "No serialized pending media file found, pending_media.json");
        continue;
        List localList = null;
        SimpleModule localSimpleModule = new SimpleModule("PendingMediaModule", Version.unknownVersion());
        localSimpleModule.addDeserializer(PendingMedia.class, new PendingMediaDeserializer());
        localSimpleModule.addDeserializer(Venue.class, new LocationDeserializer());
        2 local2 = new TypeReference()
        {
        };
        ObjectMapper localObjectMapper = new ObjectMapper().withModule(localSimpleModule);
        try
        {
          localList = (List)localObjectMapper.readValue(localObject1, local2);
          try
          {
            localObject1.close();
          }
          catch (IOException localIOException3)
          {
          }
          continue;
        }
        catch (Exception localException)
        {
          Log.d("PendingMediaService", "Failed to decode pending_media.json, deleting file.", localException);
          localContext.deleteFile("pending_media.json");
          try
          {
            localObject1.close();
          }
          catch (IOException localIOException2)
          {
          }
          continue;
        }
        finally
        {
        }
      }
    }
    try
    {
      localObject1.close();
      label175: throw localObject2;
    }
    catch (IOException localIOException1)
    {
      break label175;
    }
  }

  public static void putMedia(PendingMedia paramPendingMedia)
  {
    String str = paramPendingMedia.getDeviceTimestamp();
    getPendingMediaMap().put(str, paramPendingMedia);
  }

  public static void removeMedia(String paramString)
  {
    new File(((PendingMedia)getPendingMediaMap().get(paramString)).getImageFilePath()).deleteOnExit();
    getPendingMediaMap().remove(paramString);
    sendPendingMediaChangeBroadcast();
  }

  public static void retry(Context paramContext, PendingMedia paramPendingMedia)
  {
    startService(paramContext, paramPendingMedia, 2);
  }

  private void retryInternal(PendingMedia paramPendingMedia)
    throws Exception
  {
    if (paramPendingMedia.getNeedsUpload())
    {
      doUpload(paramPendingMedia);
      doConfigure(paramPendingMedia);
    }
    while (true)
    {
      return;
      if (!paramPendingMedia.getNeedsConfigure())
        break;
      doConfigure(paramPendingMedia);
    }
    throw new UnsupportedOperationException("Media is uploaded and configued but retry was tapped?!");
  }

  private static void reupload(Context paramContext, PendingMedia paramPendingMedia)
  {
    paramPendingMedia.setMediaId(null);
    paramPendingMedia.setNeedsUpload(true);
    startService(paramContext, paramPendingMedia, 2);
  }

  private void sendNewMediaBroadcast(Media paramMedia, PendingMedia paramPendingMedia)
  {
    Intent localIntent = new Intent("com.instagram.android.PendingMediaService.INTENT_ACTION_NEW_USER_MEDIA");
    localIntent.putExtra("mediaId", paramMedia.getId());
    localIntent.putExtra("pendingMediaKey", paramPendingMedia.getKey());
    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
  }

  private static void sendPendingMediaChangeBroadcast()
  {
    Intent localIntent = new Intent("com.instagram.android.PendingMediaService.INTENT_ACTION_PENDING_MEDIA_CHANGED");
    LocalBroadcastManager.getInstance(AppContext.getContext()).sendBroadcast(localIntent);
  }

  private static void serializePendingMedia()
  {
    new AsyncTask()
    {
      protected Void doInBackground(Void[] paramArrayOfVoid)
      {
        PendingMediaService.access$000();
        return null;
      }
    }
    .execute((Void[])null);
  }

  public static void startService(Context paramContext, PendingMedia paramPendingMedia, int paramInt)
  {
    Context localContext = paramContext.getApplicationContext();
    Intent localIntent = new Intent(localContext, PendingMediaService.class);
    localIntent.putExtra("com.instagram.android.PendingMediaService.INTENT_EXTRA_PERFORM_ACTION", paramInt);
    localIntent.putExtra("com.instagram.android.PendingMediaService.INTENT_EXTRA_PENDING_MEDIA_ID", paramPendingMedia.getDeviceTimestamp());
    localContext.startService(localIntent);
  }

  public static void upload(Context paramContext, PendingMedia paramPendingMedia)
  {
    putMedia(paramPendingMedia);
    startService(paramContext, paramPendingMedia, 0);
  }

  private boolean uploadInternal(PendingMedia paramPendingMedia)
    throws IOException
  {
    RequestParams localRequestParams = new RequestParams();
    FileInputStream localFileInputStream = new FileInputStream(paramPendingMedia.getImageFilePath());
    localRequestParams.put("photo", localFileInputStream, "file");
    localRequestParams.put("device_timestamp", paramPendingMedia.getDeviceTimestamp());
    HttpResponse localHttpResponse = ApiHttpClient.getInstance(this).post(ApiUrlHelper.expandPath("media/upload/"), localRequestParams);
    localFileInputStream.close();
    if ((localHttpResponse.getStatusLine() == null) || (localHttpResponse.getStatusLine().getStatusCode() != 200))
      Log.e("PendingMediaService", "Bad response from server");
    for (int i = 0; ; i = 1)
    {
      EntityUtils.consume(localHttpResponse.getEntity());
      return i;
      String str = EntityUtils.toString(localHttpResponse.getEntity());
      JsonNode localJsonNode = (JsonNode)CustomObjectMapper.getInstance(this).readValue(str, JsonNode.class);
      if (localJsonNode.get("media_id") == null)
        continue;
      paramPendingMedia.setMediaId(localJsonNode.get("media_id").asText());
    }
  }

  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }

  public void onDestroy()
  {
    super.onDestroy();
    serializePendingMedia();
  }

  protected void onHandleIntent(Intent paramIntent)
  {
    try
    {
      internalHandleIntent(paramIntent);
      return;
    }
    finally
    {
      doSerializePendingMedia();
    }
    throw localObject;
  }

  static class LocationDeserializer extends StdDeserializer<Venue>
  {
    public LocationDeserializer()
    {
      super();
    }

    public Venue deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException
    {
      JsonNode localJsonNode = (JsonNode)paramJsonParser.readValueAs(JsonNode.class);
      Venue localVenue = new Venue();
      localVenue.latitude = Double.valueOf(localJsonNode.get("latitude").asDouble());
      localVenue.longitude = Double.valueOf(localJsonNode.get("longitude").asDouble());
      localVenue.address = localJsonNode.get("address").asText();
      localVenue.externalId = localJsonNode.get("externalId").asText();
      localVenue.externalSource = localJsonNode.get("externalSource").asText();
      localVenue.id = localJsonNode.get("id").asText();
      localVenue.name = localJsonNode.get("name").asText();
      return localVenue;
    }
  }

  static class PendingMediaDeserializer extends StdDeserializer<PendingMedia>
  {
    public PendingMediaDeserializer()
    {
      super();
    }

    public PendingMedia deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException
    {
      ObjectMapper localObjectMapper = (ObjectMapper)paramJsonParser.getCodec();
      JsonNode localJsonNode = (JsonNode)paramJsonParser.readValueAs(JsonNode.class);
      PendingMedia localPendingMedia = PendingMedia.create(localJsonNode.get("imageFilePath").asText(), localJsonNode.get("thumbnailSize").asInt());
      if (localPendingMedia == null)
        localPendingMedia = null;
      while (true)
      {
        return localPendingMedia;
        localPendingMedia.setStatus(1);
        localPendingMedia.setNeedsUpload(localJsonNode.get("needsUpload").asBoolean());
        localPendingMedia.setNeedsUpload(localJsonNode.get("needsConfigure").asBoolean());
        localPendingMedia.setKey(localJsonNode.get("key").asText());
        localPendingMedia.setSourceType(localJsonNode.get("sourceType").asInt());
        localPendingMedia.setFilterType(localJsonNode.get("filterTypeOrdinal").asInt());
        localPendingMedia.setIsTwitterEnabled(localJsonNode.get("twitterEnabled").asBoolean());
        localPendingMedia.setIsFacebookEnabled(localJsonNode.get("facebookEnabled").asBoolean());
        localPendingMedia.setIsFoursquareEnabled(localJsonNode.get("foursquareEnabled").asBoolean());
        localPendingMedia.setIsTumblrEnabled(localJsonNode.get("tumblrEnabled").asBoolean());
        localPendingMedia.setLatitude(localJsonNode.get("latitude").asDouble());
        localPendingMedia.setLongitude(localJsonNode.get("longitude").asDouble());
        localPendingMedia.setVenue((Venue)localObjectMapper.readValue(localJsonNode.get("location"), Venue.class));
        localPendingMedia.setCaption(localJsonNode.get("caption").asText());
      }
    }
  }

  static class PendingMediaSerializer extends SerializerBase<PendingMedia>
  {
    public PendingMediaSerializer()
    {
      super();
    }

    public void serialize(PendingMedia paramPendingMedia, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException
    {
      paramJsonGenerator.writeStartObject();
      paramJsonGenerator.writeBooleanField("needsUpload", paramPendingMedia.getNeedsUpload());
      paramJsonGenerator.writeBooleanField("needsConfigure", paramPendingMedia.getNeedsConfigure());
      paramJsonGenerator.writeNumberField("thumbnailSize", paramPendingMedia.getThumbnailSize());
      paramJsonGenerator.writeStringField("imageFilePath", paramPendingMedia.getImageFilePath());
      paramJsonGenerator.writeStringField("key", paramPendingMedia.getKey());
      paramJsonGenerator.writeNumberField("sourceType", paramPendingMedia.getSourceType().intValue());
      paramJsonGenerator.writeNumberField("filterTypeOrdinal", paramPendingMedia.getFilterType().intValue());
      paramJsonGenerator.writeBooleanField("twitterEnabled", paramPendingMedia.isTwitterEnabled());
      paramJsonGenerator.writeBooleanField("facebookEnabled", paramPendingMedia.isFacebookEnabled());
      paramJsonGenerator.writeBooleanField("foursquareEnabled", paramPendingMedia.isFoursquareEnabled());
      paramJsonGenerator.writeBooleanField("tumblrEnabled", paramPendingMedia.isTumblrEnabled());
      paramJsonGenerator.writeNumberField("latitude", paramPendingMedia.getLatitude().doubleValue());
      paramJsonGenerator.writeNumberField("longitude", paramPendingMedia.getLongitude().doubleValue());
      paramJsonGenerator.writeObjectField("location", paramPendingMedia.getVenue());
      paramJsonGenerator.writeStringField("caption", paramPendingMedia.getCaption());
      paramJsonGenerator.writeEndObject();
    }
  }

  public class ServiceBinder extends Binder
  {
    public ServiceBinder()
    {
    }

    public PendingMediaService getService()
    {
      return PendingMediaService.this;
    }
  }

  private static class UploadLocationSerializer extends SerializerBase<Venue>
  {
    public UploadLocationSerializer()
    {
      super();
    }

    public void serialize(Venue paramVenue, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException
    {
      paramJsonGenerator.writeStartObject();
      paramJsonGenerator.writeStringField("name", paramVenue.name);
      paramJsonGenerator.writeStringField("address", paramVenue.address);
      paramJsonGenerator.writeNumberField("lat", paramVenue.latitude.doubleValue());
      paramJsonGenerator.writeNumberField("lng", paramVenue.longitude.doubleValue());
      paramJsonGenerator.writeStringField("external_source", paramVenue.externalSource);
      if (paramVenue.externalSource.equals("foursquare"))
        paramJsonGenerator.writeStringField("foursquare_v2_id", paramVenue.externalId);
      paramJsonGenerator.writeEndObject();
    }
  }

  static class VenueSerializer extends SerializerBase<Venue>
  {
    public VenueSerializer()
    {
      super();
    }

    public void serialize(Venue paramVenue, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException
    {
      paramJsonGenerator.writeStartObject();
      paramJsonGenerator.writeNumberField("latitude", paramVenue.latitude.doubleValue());
      paramJsonGenerator.writeNumberField("longitude", paramVenue.longitude.doubleValue());
      paramJsonGenerator.writeStringField("address", paramVenue.address);
      paramJsonGenerator.writeStringField("externalId", paramVenue.externalId);
      paramJsonGenerator.writeStringField("externalSource", paramVenue.externalSource);
      paramJsonGenerator.writeStringField("id", paramVenue.id);
      paramJsonGenerator.writeStringField("name", paramVenue.name);
      paramJsonGenerator.writeEndObject();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.PendingMediaService
 * JD-Core Version:    0.6.0
 */