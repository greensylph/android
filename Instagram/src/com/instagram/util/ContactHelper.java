package com.instagram.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;

public class ContactHelper
{
  private static void addEmailAddresses(Context paramContext, HashMap<Integer, Contact> paramHashMap)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    Uri localUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
    Cursor localCursor = null;
    try
    {
      localCursor = localContentResolver.query(localUri, EmailQuery.EMAIL_PROJECTION, null, null, null);
      if (localCursor.moveToNext())
      {
        int i = localCursor.getInt(0);
        String str = localCursor.getString(1);
        Contact localContact = getOrCreateContact(paramHashMap, i);
        if (localContact.emailAddresses == null)
          Contact.access$002(localContact, new ArrayList());
        localContact.emailAddresses.add(str);
      }
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  private static void addNames(Context paramContext, HashMap<Integer, Contact> paramHashMap)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    Uri localUri = ContactsContract.Data.CONTENT_URI;
    Cursor localCursor = null;
    try
    {
      String[] arrayOfString1 = NameQuery.NAME_PROJECTION;
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = "vnd.android.cursor.item/name";
      localCursor = localContentResolver.query(localUri, arrayOfString1, "mimetype = ? AND data1 IS NOT NULL", arrayOfString2, null);
      if (localCursor.moveToNext())
      {
        Contact localContact = getOrCreateContact(paramHashMap, localCursor.getInt(0));
        Contact.access$202(localContact, localCursor.getString(1));
        Contact.access$302(localContact, localCursor.getString(2));
      }
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  private static void addPhoneNumbers(Context paramContext, HashMap<Integer, Contact> paramHashMap)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    Uri localUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    Cursor localCursor = null;
    try
    {
      localCursor = localContentResolver.query(localUri, PhoneQuery.PHONE_PROJECTION, null, null, null);
      if (localCursor.moveToNext())
      {
        int i = localCursor.getInt(0);
        String str = localCursor.getString(1);
        Contact localContact = getOrCreateContact(paramHashMap, i);
        if (localContact.phoneNumbers == null)
          Contact.access$102(localContact, new ArrayList());
        localContact.phoneNumbers.add(str);
      }
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  public static HashMap<Integer, Contact> getContacts(Context paramContext)
  {
    HashMap localHashMap = new HashMap();
    addEmailAddresses(paramContext, localHashMap);
    addPhoneNumbers(paramContext, localHashMap);
    addNames(paramContext, localHashMap);
    return localHashMap;
  }

  public static String getJsonString(HashMap<Integer, Contact> paramHashMap)
  {
    ObjectMapper localObjectMapper = new ObjectMapper();
    SimpleModule localSimpleModule = new SimpleModule("ContactModule", Version.unknownVersion());
    localSimpleModule.addSerializer(Contact.class, new ContactHelper.Contact.Serializer());
    localObjectMapper.registerModule(localSimpleModule);
    try
    {
      String str = localObjectMapper.writeValueAsString(paramHashMap.values());
      return str;
    }
    catch (IOException localIOException)
    {
    }
    throw new RuntimeException("Error creating json string");
  }

  private static Contact getOrCreateContact(HashMap<Integer, Contact> paramHashMap, int paramInt)
  {
    Contact localContact;
    if (paramHashMap.containsKey(Integer.valueOf(paramInt)))
      localContact = (Contact)paramHashMap.get(Integer.valueOf(paramInt));
    while (true)
    {
      return localContact;
      localContact = new Contact();
      paramHashMap.put(Integer.valueOf(paramInt), localContact);
    }
  }

  public static class Contact
  {
    private ArrayList<String> emailAddresses;
    private String firstName;
    private String lastName;
    private ArrayList<String> phoneNumbers;

    public static class Serializer extends JsonSerializer<ContactHelper.Contact>
    {
      public void serialize(ContactHelper.Contact paramContact, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
        throws IOException, JsonProcessingException
      {
        paramJsonGenerator.writeStartObject();
        if (paramContact.phoneNumbers != null)
          paramJsonGenerator.writeObjectField("phone_numbers", paramContact.phoneNumbers);
        if (paramContact.firstName != null)
          paramJsonGenerator.writeObjectField("first_name", paramContact.firstName);
        if (paramContact.lastName != null)
          paramJsonGenerator.writeObjectField("last_name", paramContact.lastName);
        if (paramContact.emailAddresses != null)
          paramJsonGenerator.writeObjectField("email_addresses", paramContact.emailAddresses);
        paramJsonGenerator.writeEndObject();
      }
    }
  }

  private static class EmailQuery
  {
    public static final int CONTACT_ID = 0;
    public static final int EMAIL = 1;
    public static final String[] EMAIL_PROJECTION;

    static
    {
      String[] arrayOfString = new String[2];
      arrayOfString[0] = "contact_id";
      arrayOfString[1] = "data1";
      EMAIL_PROJECTION = arrayOfString;
    }
  }

  private static class NameQuery
  {
    public static final int CONTACT_ID = 0;
    public static final int DISPLAY_NAME = 3;
    public static final int FAMILY_NAME = 2;
    public static final int GIVEN_NAME = 1;
    public static final String[] NAME_PROJECTION;

    static
    {
      String[] arrayOfString = new String[4];
      arrayOfString[0] = "contact_id";
      arrayOfString[1] = "data2";
      arrayOfString[2] = "data3";
      arrayOfString[3] = "data1";
      NAME_PROJECTION = arrayOfString;
    }
  }

  private static class PhoneQuery
  {
    public static final int CONTACT_ID = 0;
    public static final int DATA = 1;
    public static final String[] PHONE_PROJECTION;

    static
    {
      String[] arrayOfString = new String[2];
      arrayOfString[0] = "contact_id";
      arrayOfString[1] = "data1";
      PHONE_PROJECTION = arrayOfString;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.ContactHelper
 * JD-Core Version:    0.6.0
 */