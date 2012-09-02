package com.instagram.android.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.instagram.android.Log;
import com.instagram.android.location.Venue;
import com.instagram.android.model.User;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.widget.SearchEditText;
import com.instagram.android.widget.SearchEditText.SearchEditTextListener;
import com.instagram.util.StringUtil;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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

public class NearbyVenuesAdapter extends BaseAdapter
{
  public static final String FILENAME = "custom_venues.json";
  public static final int FOOTER_ROW_ADD = 0;
  public static final int FOOTER_ROW_SEARCH = 1;
  private static final int HEADER_ROW_SEARCH_EDIT_TEXT = 0;
  public static final int MODE_CUSTOM_VENUE = 1;
  public static final int MODE_NEARBY_VENUES = 0;
  public static final int NUM_FOOTER_ROWS = 2;
  private static final int NUM_HEADER_ROWS = 1;
  private static final int NUM_VIEW_TYPES = 4;
  private static final String TAG = "CustomVenuesAdapter";
  private static final int VIEW_TYPE_ADD = 1;
  private static final int VIEW_TYPE_SEARCH = 2;
  private static final int VIEW_TYPE_SEARCH_EDIT_TEXT = 0;
  private static final int VIEW_TYPE_VENUE = 3;
  private Set<String> mCustomVenueNames = new HashSet();
  private List<Venue> mCustomVenues = new ArrayList();
  private List<Venue> mCustomVenuesFiltered = new ArrayList();
  private LayoutInflater mInflater;
  private Location mLocation;
  private List<Venue> mNearbyVenues = new ArrayList();
  private View.OnFocusChangeListener mSearchEditTextFocusListener = new View.OnFocusChangeListener()
  {
    public void onFocusChange(View paramView, boolean paramBoolean)
    {
      if (paramBoolean)
        NearbyVenuesAdapter.access$002(NearbyVenuesAdapter.this, paramBoolean);
    }
  };
  private boolean mSearchEditTextHasFocus;
  private SearchEditText.SearchEditTextListener mSearchEditTextListener = new SearchEditText.SearchEditTextListener()
  {
    public void onSearchSubmitted(SearchEditText paramSearchEditText, String paramString)
    {
    }

    public void onSearchTextChanged(SearchEditText paramSearchEditText, CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
      NearbyVenuesAdapter.this.updateSearchString(paramSearchEditText.getSearchString());
    }
  };
  private boolean mSearchFieldDisabled;
  private String mSearchString = null;

  public NearbyVenuesAdapter(Context paramContext)
  {
    loadSavedCustomVenues();
    this.mInflater = LayoutInflater.from(paramContext);
  }

  private void addCustomVenue(Venue paramVenue)
  {
    if (this.mCustomVenueNames.contains(paramVenue.name));
    while (true)
    {
      return;
      this.mCustomVenues.add(paramVenue);
      this.mCustomVenueNames.add(paramVenue.name.toLowerCase());
    }
  }

  private void bindView(int paramInt1, int paramInt2, View paramView)
  {
    switch (paramInt2)
    {
    default:
      throw new UnsupportedOperationException();
    case 0:
      NearbyVenuesAdapter.SearchEditTextRowViewAdapter.Holder localHolder = (NearbyVenuesAdapter.SearchEditTextRowViewAdapter.Holder)paramView.getTag();
      SearchEditTextRowViewAdapter.bindView(localHolder, this.mSearchEditTextListener);
      if (this.mSearchEditTextHasFocus)
        localHolder.editText.post(new Runnable(localHolder)
        {
          public void run()
          {
            this.val$holder.editText.requestFocus();
          }
        });
      localHolder.editText.setOnFocusChangeListener(this.mSearchEditTextFocusListener);
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return;
      AddRowViewAdapter.bindView((NearbyVenuesAdapter.AddRowViewAdapter.Holder)paramView.getTag(), this.mSearchString);
      continue;
      SearchRowViewAdapter.bindView((NearbyVenuesAdapter.SearchRowViewAdapter.Holder)paramView.getTag(), this.mSearchString);
      continue;
      Venue localVenue = getItem(paramInt1);
      VenueRowAdapter.bindView((NearbyVenuesAdapter.VenueRowAdapter.Holder)paramView.getTag(), localVenue);
    }
  }

  private View createView(int paramInt, ViewGroup paramViewGroup)
  {
    View localView;
    switch (paramInt)
    {
    default:
      throw new UnsupportedOperationException();
    case 0:
      localView = SearchEditTextRowViewAdapter.createView(this.mInflater, paramViewGroup);
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return localView;
      localView = AddRowViewAdapter.createView(this.mInflater, paramViewGroup);
      continue;
      localView = SearchRowViewAdapter.createView(this.mInflater, paramViewGroup);
      continue;
      localView = VenueRowAdapter.createView(this.mInflater, paramViewGroup);
    }
  }

  private void doSerializeCustomVenues(List<Venue> paramList)
  {
    Context localContext = AppContext.getContext();
    localContext.deleteFile("custom_venues.json");
    Object localObject1 = null;
    try
    {
      FileOutputStream localFileOutputStream = localContext.openFileOutput("custom_venues.json", 0);
      localObject1 = localFileOutputStream;
      if (localObject1 == null)
      {
        Log.e("CustomVenuesAdapter", "Failed to acquire output stream for custom_venues.json");
        return;
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      while (true)
      {
        Log.e("CustomVenuesAdapter", "File not found wile getting output stream for custom_venues.json", localFileNotFoundException);
        continue;
        if ((paramList == null) || (paramList.size() == 0))
        {
          Log.e("CustomVenuesAdapter", "Custom venues is null during serialization");
          continue;
        }
        if (paramList.size() == 0)
          continue;
        SimpleModule localSimpleModule = new SimpleModule("CustomVenues", Version.unknownVersion());
        localSimpleModule.addSerializer(Venue.class, new CustomVenueSerializer());
        ObjectMapper localObjectMapper = new ObjectMapper().withModule(localSimpleModule);
        try
        {
          localObjectMapper.writeValue(localObject1, paramList);
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
          Log.e("CustomVenuesAdapter", "Exception while writing out custom_venues.json", localIOException2);
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
      label186: throw localObject2;
    }
    catch (IOException localIOException1)
    {
      break label186;
    }
  }

  private void filterCustomVenues()
  {
    this.mCustomVenuesFiltered.clear();
    Iterator localIterator = this.mCustomVenues.iterator();
    while (localIterator.hasNext())
    {
      Venue localVenue = (Venue)localIterator.next();
      if (!localVenue.name.toLowerCase().startsWith(this.mSearchString.toLowerCase()))
        continue;
      this.mCustomVenuesFiltered.add(localVenue);
    }
  }

  private int getCurrentMode()
  {
    if ((this.mSearchString != null) && (this.mSearchString.length() > 0));
    for (int i = 1; ; i = 0)
      return i;
  }

  private Venue getCustomVenueByName(String paramString)
  {
    Venue localVenue;
    if ((paramString != null) && (this.mCustomVenueNames != null) && (paramString != null) && (this.mCustomVenueNames.contains(paramString.toLowerCase())))
    {
      Iterator localIterator = this.mCustomVenues.iterator();
      do
      {
        if (!localIterator.hasNext())
          break;
        localVenue = (Venue)localIterator.next();
      }
      while (!localVenue.name.toLowerCase().equals(paramString));
    }
    while (true)
    {
      return localVenue;
      localVenue = null;
    }
  }

  private int getNumActiveHeaderRows()
  {
    int i = 1;
    if (this.mSearchFieldDisabled)
      i--;
    return i;
  }

  private Venue getVenue(int paramInt)
  {
    switch (getCurrentMode())
    {
    default:
    case 1:
    }
    for (Venue localVenue = (Venue)this.mNearbyVenues.get(paramInt); ; localVenue = (Venue)this.mCustomVenuesFiltered.get(paramInt))
      return localVenue;
  }

  // ERROR //
  private void loadSavedCustomVenues()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 96	com/instagram/android/adapter/NearbyVenuesAdapter:mCustomVenues	Ljava/util/List;
    //   4: invokeinterface 291 1 0
    //   9: aload_0
    //   10: getfield 98	com/instagram/android/adapter/NearbyVenuesAdapter:mCustomVenuesFiltered	Ljava/util/List;
    //   13: invokeinterface 291 1 0
    //   18: invokestatic 222	com/instagram/android/service/AppContext:getContext	()Landroid/content/Context;
    //   21: astore_1
    //   22: aconst_null
    //   23: astore_2
    //   24: aload_1
    //   25: ldc 36
    //   27: invokevirtual 330	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   30: astore 21
    //   32: aload 21
    //   34: astore_2
    //   35: aload_2
    //   36: ifnull +86 -> 122
    //   39: new 253	org/codehaus/jackson/map/module/SimpleModule
    //   42: dup
    //   43: ldc_w 332
    //   46: invokestatic 261	org/codehaus/jackson/Version:unknownVersion	()Lorg/codehaus/jackson/Version;
    //   49: invokespecial 264	org/codehaus/jackson/map/module/SimpleModule:<init>	(Ljava/lang/String;Lorg/codehaus/jackson/Version;)V
    //   52: astore 5
    //   54: aload 5
    //   56: ldc 131
    //   58: new 19	com/instagram/android/adapter/NearbyVenuesAdapter$CustomVenueDeserializer
    //   61: dup
    //   62: invokespecial 333	com/instagram/android/adapter/NearbyVenuesAdapter$CustomVenueDeserializer:<init>	()V
    //   65: invokevirtual 337	org/codehaus/jackson/map/module/SimpleModule:addDeserializer	(Ljava/lang/Class;Lorg/codehaus/jackson/map/JsonDeserializer;)Lorg/codehaus/jackson/map/module/SimpleModule;
    //   68: pop
    //   69: new 271	org/codehaus/jackson/map/ObjectMapper
    //   72: dup
    //   73: invokespecial 272	org/codehaus/jackson/map/ObjectMapper:<init>	()V
    //   76: aload 5
    //   78: invokevirtual 276	org/codehaus/jackson/map/ObjectMapper:withModule	(Lorg/codehaus/jackson/map/Module;)Lorg/codehaus/jackson/map/ObjectMapper;
    //   81: astore 7
    //   83: new 12	com/instagram/android/adapter/NearbyVenuesAdapter$4
    //   86: dup
    //   87: aload_0
    //   88: invokespecial 338	com/instagram/android/adapter/NearbyVenuesAdapter$4:<init>	(Lcom/instagram/android/adapter/NearbyVenuesAdapter;)V
    //   91: astore 8
    //   93: aload 7
    //   95: aload_2
    //   96: aload 8
    //   98: invokevirtual 342	org/codehaus/jackson/map/ObjectMapper:readValue	(Ljava/io/InputStream;Lorg/codehaus/jackson/type/TypeReference;)Ljava/lang/Object;
    //   101: checkcast 142	java/util/List
    //   104: astore 18
    //   106: aload_0
    //   107: getfield 96	com/instagram/android/adapter/NearbyVenuesAdapter:mCustomVenues	Ljava/util/List;
    //   110: aload 18
    //   112: invokeinterface 346 2 0
    //   117: pop
    //   118: aload_2
    //   119: invokevirtual 349	java/io/FileInputStream:close	()V
    //   122: aload_0
    //   123: getfield 94	com/instagram/android/adapter/NearbyVenuesAdapter:mCustomVenueNames	Ljava/util/Set;
    //   126: invokeinterface 350 1 0
    //   131: aload_0
    //   132: getfield 96	com/instagram/android/adapter/NearbyVenuesAdapter:mCustomVenues	Ljava/util/List;
    //   135: invokeinterface 295 1 0
    //   140: astore 15
    //   142: aload 15
    //   144: invokeinterface 301 1 0
    //   149: ifeq +97 -> 246
    //   152: aload 15
    //   154: invokeinterface 304 1 0
    //   159: checkcast 131	com/instagram/android/location/Venue
    //   162: astore 16
    //   164: aload_0
    //   165: getfield 94	com/instagram/android/adapter/NearbyVenuesAdapter:mCustomVenueNames	Ljava/util/Set;
    //   168: aload 16
    //   170: getfield 134	com/instagram/android/location/Venue:name	Ljava/lang/String;
    //   173: invokeinterface 152 2 0
    //   178: pop
    //   179: goto -37 -> 142
    //   182: astore_3
    //   183: ldc 52
    //   185: ldc_w 352
    //   188: invokestatic 355	com/instagram/android/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   191: pop
    //   192: goto -157 -> 35
    //   195: astore 11
    //   197: ldc 52
    //   199: ldc_w 357
    //   202: aload 11
    //   204: invokestatic 245	com/instagram/android/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   207: pop
    //   208: aload_1
    //   209: ldc 36
    //   211: invokevirtual 228	android/content/Context:deleteFile	(Ljava/lang/String;)Z
    //   214: pop
    //   215: aload_2
    //   216: invokevirtual 349	java/io/FileInputStream:close	()V
    //   219: goto -97 -> 122
    //   222: astore 14
    //   224: goto -102 -> 122
    //   227: astore 9
    //   229: aload_2
    //   230: invokevirtual 349	java/io/FileInputStream:close	()V
    //   233: aload 9
    //   235: athrow
    //   236: astore 20
    //   238: goto -116 -> 122
    //   241: astore 10
    //   243: goto -10 -> 233
    //   246: return
    //
    // Exception table:
    //   from	to	target	type
    //   24	32	182	java/io/FileNotFoundException
    //   93	118	195	java/io/IOException
    //   215	219	222	java/io/IOException
    //   93	118	227	finally
    //   197	215	227	finally
    //   118	122	236	java/io/IOException
    //   229	233	241	java/io/IOException
  }

  private void serializeCustomVenues()
  {
    new AsyncTask(new ArrayList(this.mCustomVenues))
    {
      protected Void doInBackground(Void[] paramArrayOfVoid)
      {
        NearbyVenuesAdapter.this.doSerializeCustomVenues(this.val$venues);
        return null;
      }
    }
    .execute((Void[])null);
  }

  public void disableSearch()
  {
    this.mSearchFieldDisabled = true;
    notifyDataSetChanged();
  }

  public int getCount()
  {
    int i = getVenuesCount() + getNumActiveHeaderRows();
    if (getCurrentMode() == 1)
      i += 2;
    return i;
  }

  public Venue getItem(int paramInt)
  {
    if (paramInt < getNumActiveHeaderRows())
      throw new UnsupportedOperationException("Get item called for header row");
    int i = getVenuesCount();
    int j = paramInt - getNumActiveHeaderRows();
    Venue localVenue;
    if (j < i)
      localVenue = getVenue(j);
    while (true)
    {
      return localVenue;
      switch (j - i)
      {
      default:
        throw new UnsupportedOperationException("Get item called for unsupported footer row");
      case 0:
      }
      localVenue = getCustomVenueByName(this.mSearchString);
      if (localVenue != null)
        continue;
      if (this.mSearchString != null)
      {
        localVenue = Venue.fromCustomName(this.mSearchString, this.mLocation);
        localVenue.setIsCustomVenue(true);
        addCustomVenue(localVenue);
        serializeCustomVenues();
        continue;
      }
      localVenue = null;
    }
  }

  public long getItemId(int paramInt)
  {
    return 0L;
  }

  public int getItemViewType(int paramInt)
  {
    int i = getNumActiveHeaderRows();
    int m;
    if (paramInt < i)
    {
      switch (paramInt)
      {
      default:
        throw new UnsupportedOperationException("Unhandled header view type");
      case 0:
      }
      m = 0;
    }
    while (true)
    {
      return m;
      int j = getVenuesCount();
      int k = paramInt - i;
      if (k < j)
      {
        m = 3;
        continue;
      }
      switch (k - j)
      {
      default:
        throw new UnsupportedOperationException("View type not handled");
      case 0:
        m = 1;
        break;
      case 1:
        m = 2;
      }
    }
  }

  public String getSearchString()
  {
    return this.mSearchString;
  }

  public int getVenuesCount()
  {
    if (getCurrentMode() == 0);
    for (int i = this.mNearbyVenues.size(); ; i = this.mCustomVenuesFiltered.size())
      return i;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    int i = getItemViewType(paramInt);
    View localView = paramView;
    if (localView == null)
      localView = createView(i, paramViewGroup);
    bindView(paramInt, i, localView);
    return localView;
  }

  public int getViewTypeCount()
  {
    return 4;
  }

  public boolean hasStableIds()
  {
    return true;
  }

  public boolean isCustomRow(int paramInt1, int paramInt2)
  {
    int i = 0;
    if (getCurrentMode() == 0);
    while (true)
    {
      return i;
      int j = getVenuesCount();
      int k = paramInt2 - getNumActiveHeaderRows();
      if ((k < j) || (k - j != paramInt1))
        continue;
      i = 1;
    }
  }

  public boolean isEmpty()
  {
    return false;
  }

  public void setLocation(Location paramLocation)
  {
    this.mLocation = paramLocation;
  }

  public void setNearbyVenues(List<Venue> paramList)
  {
    this.mNearbyVenues.clear();
    this.mNearbyVenues.addAll(paramList);
  }

  public void updateSearchString(String paramString)
  {
    this.mSearchString = paramString;
    filterCustomVenues();
    notifyDataSetChanged();
  }

  public static class AddRowViewAdapter
  {
    public static void bindView(Holder paramHolder, String paramString)
    {
      TextView localTextView = paramHolder.title;
      Resources localResources = AppContext.getContext().getResources();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramString;
      localTextView.setText(localResources.getString(2131231023, arrayOfObject));
    }

    public static View createView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      View localView = paramLayoutInflater.inflate(2130903098, paramViewGroup, false);
      Holder localHolder = new Holder();
      localView.setTag(localHolder);
      localHolder.title = ((TextView)localView.findViewById(2131493007));
      return localView;
    }

    public static class Holder
    {
      TextView title;
    }
  }

  private static class CustomVenueDeserializer extends StdDeserializer<Venue>
  {
    protected CustomVenueDeserializer()
    {
      super();
    }

    public Venue deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException
    {
      JsonNode localJsonNode = (JsonNode)paramJsonParser.readValueAs(JsonNode.class);
      Venue localVenue = new Venue();
      localVenue.name = localJsonNode.get("name").asText();
      localVenue.latitude = Double.valueOf(localJsonNode.get("lat").asDouble());
      localVenue.longitude = Double.valueOf(localJsonNode.get("lng").asDouble());
      User localUser = AuthHelper.getInstance().getCurrentUser();
      localVenue.externalSource = ("user-" + localUser.getId());
      localVenue.setIsCustomVenue(true);
      return localVenue;
    }
  }

  private static class CustomVenueSerializer extends SerializerBase<Venue>
  {
    public CustomVenueSerializer()
    {
      super();
    }

    public void serialize(Venue paramVenue, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException
    {
      paramJsonGenerator.writeStartObject();
      paramJsonGenerator.writeStringField("name", paramVenue.name);
      paramJsonGenerator.writeNumberField("lat", paramVenue.latitude.doubleValue());
      paramJsonGenerator.writeNumberField("lng", paramVenue.longitude.doubleValue());
      paramJsonGenerator.writeEndObject();
    }
  }

  public static class SearchEditTextRowViewAdapter
  {
    public static void bindView(Holder paramHolder, SearchEditText.SearchEditTextListener paramSearchEditTextListener)
    {
      paramHolder.editText.setOnFilterTextListener(paramSearchEditTextListener);
      paramHolder.editText.setHint(2131231048);
      paramHolder.editText.setFocusable(true);
    }

    public static View createView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      View localView = paramLayoutInflater.inflate(2130903116, paramViewGroup, false);
      localView.setBackgroundColor(localView.getContext().getResources().getColor(2131165210));
      Holder localHolder = new Holder();
      localView.setTag(localHolder);
      localHolder.editText = ((SearchEditText)localView.findViewById(2131493076));
      return localView;
    }

    public static class Holder
    {
      SearchEditText editText;
    }
  }

  public static class SearchRowViewAdapter
  {
    public static void bindView(Holder paramHolder, String paramString)
    {
      TextView localTextView = paramHolder.title;
      Resources localResources = AppContext.getContext().getResources();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramString;
      localTextView.setText(localResources.getString(2131231024, arrayOfObject));
    }

    public static View createView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      View localView = paramLayoutInflater.inflate(2130903118, paramViewGroup, false);
      Holder localHolder = new Holder();
      localView.setTag(localHolder);
      localHolder.title = ((TextView)localView.findViewById(2131493077));
      return localView;
    }

    public static class Holder
    {
      TextView title;
    }
  }

  private static class VenueRowAdapter
  {
    public static void bindView(Holder paramHolder, Venue paramVenue)
    {
      paramHolder.title.setText(paramVenue.name);
      if ((paramVenue.address == null) && (paramVenue.getIsCustomVenue()))
        paramHolder.subtitle.setText(2131231025);
      while (true)
      {
        return;
        if (!StringUtil.isNullOrEmpty(paramVenue.address))
        {
          paramHolder.subtitle.setText(paramVenue.address);
          paramHolder.subtitle.setVisibility(0);
          continue;
        }
        paramHolder.subtitle.setVisibility(8);
      }
    }

    public static View createView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      View localView = paramLayoutInflater.inflate(2130903122, paramViewGroup, false);
      Holder localHolder = new Holder();
      localView.setTag(localHolder);
      localHolder.title = ((TextView)localView.findViewById(2131493096));
      localHolder.subtitle = ((TextView)localView.findViewById(2131493097));
      return localView;
    }

    public static class Holder
    {
      TextView subtitle;
      TextView title;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.NearbyVenuesAdapter
 * JD-Core Version:    0.6.0
 */