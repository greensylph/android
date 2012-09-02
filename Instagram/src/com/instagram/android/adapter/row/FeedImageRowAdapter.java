package com.instagram.android.adapter.row;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.instagram.android.Preferences;
import com.instagram.android.fragment.CommentThreadFragment;
import com.instagram.android.fragment.LocationFeedFragment;
import com.instagram.android.fragment.UserDetailFragment;
import com.instagram.android.imagecache.IgImageView;
import com.instagram.android.imagecache.IgProgressImageView;
import com.instagram.android.location.Venue;
import com.instagram.android.model.Media;
import com.instagram.android.model.Media.FeedItemLocationType;
import com.instagram.android.model.User;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.android.widget.MapImageViewHelper;
import com.instagram.android.widget.MapImageViewHelper.MapClickListener;
import com.instagram.android.widget.MediaOptionsButton;
import com.instagram.api.request.LikeRequest;
import com.instagram.util.FragmentUtil;

public class FeedImageRowAdapter
{
  public static final String FAR_ZOOM = "10";
  private Activity mActivity;
  private final Context mContext;
  private FragmentManager mFragmentManager;
  private View.OnTouchListener mImageViewOnTouchListener;
  private LoaderManager mLoaderManager;

  public FeedImageRowAdapter(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
  }

  private void bindFoursquareLocation(HeaderHolder paramHeaderHolder, Media paramMedia)
  {
    paramHeaderHolder.locationGlyph.setImageDrawable(this.mActivity.getResources().getDrawable(2130837763));
    paramHeaderHolder.location.setTextColor(this.mActivity.getResources().getColor(2131165194));
    paramHeaderHolder.location.setOnClickListener(new View.OnClickListener(paramMedia)
    {
      public void onClick(View paramView)
      {
        Bundle localBundle = new Bundle();
        localBundle.putString("com.instagram.android.fragment.LocationFeedFragment.ARGUMENT_LOCATION_MEDIA_ID", this.val$media.getId());
        FragmentUtil.navigateTo(FeedImageRowAdapter.this.mFragmentManager, new LocationFeedFragment(), localBundle);
      }
    });
  }

  private void bindUserLocation(HeaderHolder paramHeaderHolder, Media paramMedia)
  {
    if (paramMedia.hasLatLng())
    {
      paramHeaderHolder.location.setTextColor(this.mActivity.getResources().getColor(2131165194));
      paramHeaderHolder.locationGlyph.setImageDrawable(this.mActivity.getResources().getDrawable(2130837763));
      paramHeaderHolder.location.setOnClickListener(new View.OnClickListener(paramMedia)
      {
        public void onClick(View paramView)
        {
          View localView = LayoutInflater.from(FeedImageRowAdapter.this.mActivity).inflate(2130903064, null);
          IgImageView localIgImageView = (IgImageView)localView.findViewById(2131492930);
          Venue localVenue = this.val$media.getVenue();
          localIgImageView.setOnClickListener(new MapImageViewHelper.MapClickListener(FeedImageRowAdapter.this.mActivity, this.val$media.getLatitude(), this.val$media.getLongitude(), localVenue.name, "10"));
          localIgImageView.setUrl(MapImageViewHelper.constructStaticMapUrl(localVenue, "10", FeedImageRowAdapter.this.mActivity.getResources().getDimensionPixelSize(2131427339), FeedImageRowAdapter.this.mActivity.getResources().getDimensionPixelSize(2131427340), this.val$media.getLatitude(), this.val$media.getLongitude()));
          new IgDialogBuilder(FeedImageRowAdapter.this.mActivity).setView(localView).create().show();
        }
      });
    }
    while (true)
    {
      return;
      paramHeaderHolder.location.setTextColor(this.mActivity.getResources().getColor(2131165193));
      paramHeaderHolder.locationGlyph.setImageDrawable(this.mActivity.getResources().getDrawable(2130837764));
      paramHeaderHolder.location.setOnClickListener(null);
    }
  }

  public static HeaderHolder getHeaderHolder(View paramView)
  {
    HeaderHolder localHeaderHolder = new HeaderHolder();
    localHeaderHolder.profileImageView = ((IgImageView)paramView.findViewById(2131493029));
    localHeaderHolder.profileName = ((TextView)paramView.findViewById(2131493030));
    localHeaderHolder.timeAgo = ((TextView)paramView.findViewById(2131493033));
    localHeaderHolder.location = ((TextView)paramView.findViewById(2131493032));
    localHeaderHolder.locationGlyph = ((ImageView)paramView.findViewById(2131493031));
    return localHeaderHolder;
  }

  public static Holder getHolder(View paramView)
  {
    Holder localHolder = new Holder();
    localHolder.imageView = ((IgProgressImageView)paramView.findViewById(2131493016));
    localHolder.textViewComments = ((TextView)paramView.findViewById(2131493023));
    localHolder.commentsGroup = ((ViewGroup)paramView.findViewById(2131493021));
    localHolder.likesGroup = ((ViewGroup)paramView.findViewById(2131493018));
    localHolder.likeText = ((TextView)paramView.findViewById(2131493020));
    localHolder.toggleButtonLike = ((CompoundButton)paramView.findViewById(2131493025));
    localHolder.buttonComment = ((ImageView)paramView.findViewById(2131493026));
    localHolder.buttonOptions = ((MediaOptionsButton)paramView.findViewById(2131493027));
    localHolder.buttonViewGroup = ((ViewGroup)paramView.findViewById(2131493024));
    localHolder.doubleTapHeart = ((ImageView)paramView.findViewById(2131493017));
    localHolder.header = getHeaderHolder(paramView);
    return localHolder;
  }

  private void showDoubleTapHintIfNecessary()
  {
    Preferences localPreferences = Preferences.getInstance(this.mContext);
    int i = localPreferences.getDoubleTappedToLikeHintImpressions();
    if ((i < 3) && (!localPreferences.getHasUsedDoubleTapToLike()))
    {
      Toast localToast = Toast.makeText(this.mContext, 2131231045, 1);
      localToast.setGravity(17, 0, 0);
      localToast.show();
      localPreferences.setDoubleTappedToLikeHintImpressions(i + 1);
    }
  }

  public void bindProfileHeader(HeaderHolder paramHeaderHolder, Media paramMedia)
  {
    paramHeaderHolder.profileImageView.setUrl(paramMedia.getUser().getProfilePicUrl());
    paramHeaderHolder.profileImageView.setOnClickListener(new View.OnClickListener(paramMedia)
    {
      public void onClick(View paramView)
      {
        Bundle localBundle = new Bundle();
        localBundle.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID", this.val$media.getUser().getId());
        FragmentUtil.navigateTo(FeedImageRowAdapter.this.mFragmentManager, new UserDetailFragment(), localBundle);
      }
    });
    paramHeaderHolder.profileName.setText(paramMedia.getUserNameText());
    paramHeaderHolder.profileName.setMovementMethod(new LinkMovementMethod());
    Venue localVenue = paramMedia.getVenue();
    if ((localVenue != null) && (localVenue.name != null))
    {
      paramHeaderHolder.location.setVisibility(0);
      paramHeaderHolder.locationGlyph.setVisibility(0);
      paramHeaderHolder.location.setText(localVenue.name);
      if (paramMedia.getFeedItemLocationType() == Media.FeedItemLocationType.Foursquare)
        bindFoursquareLocation(paramHeaderHolder, paramMedia);
    }
    while (true)
    {
      paramHeaderHolder.timeAgo.setText(paramMedia.getFormattedDate());
      return;
      if (paramMedia.getFeedItemLocationType() != Media.FeedItemLocationType.User)
        continue;
      bindUserLocation(paramHeaderHolder, paramMedia);
      continue;
      paramHeaderHolder.location.setVisibility(8);
      paramHeaderHolder.locationGlyph.setVisibility(8);
    }
  }

  // ERROR //
  public void bindView(Holder paramHolder, Media paramMedia, int paramInt, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: getfield 223	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:header	Lcom/instagram/android/adapter/row/FeedImageRowAdapter$HeaderHolder;
    //   5: aload_2
    //   6: invokevirtual 326	com/instagram/android/adapter/row/FeedImageRowAdapter:bindProfileHeader	(Lcom/instagram/android/adapter/row/FeedImageRowAdapter$HeaderHolder;Lcom/instagram/android/model/Media;)V
    //   9: aload_2
    //   10: invokevirtual 329	com/instagram/android/model/Media:hasLocalBitmap	()Z
    //   13: ifeq +316 -> 329
    //   16: aload_2
    //   17: invokevirtual 333	com/instagram/android/model/Media:getLocalBitmapUri	()Landroid/net/Uri;
    //   20: astore 5
    //   22: invokestatic 338	com/instagram/android/service/AppContext:getContext	()Landroid/content/Context;
    //   25: invokevirtual 342	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   28: astore 6
    //   30: aconst_null
    //   31: astore 7
    //   33: aload 6
    //   35: aload 5
    //   37: invokevirtual 348	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   40: astore 7
    //   42: aload_1
    //   43: getfield 172	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:imageView	Lcom/instagram/android/imagecache/IgProgressImageView;
    //   46: aload 7
    //   48: invokevirtual 352	com/instagram/android/imagecache/IgProgressImageView:loadBitmap	(Ljava/io/InputStream;)V
    //   51: aload 7
    //   53: ifnull +8 -> 61
    //   56: aload 7
    //   58: invokevirtual 357	java/io/InputStream:close	()V
    //   61: aload_1
    //   62: getfield 172	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:imageView	Lcom/instagram/android/imagecache/IgProgressImageView;
    //   65: ldc_w 358
    //   68: iload_3
    //   69: invokestatic 364	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   72: invokevirtual 368	com/instagram/android/imagecache/IgProgressImageView:setTag	(ILjava/lang/Object;)V
    //   75: aload_1
    //   76: getfield 172	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:imageView	Lcom/instagram/android/imagecache/IgProgressImageView;
    //   79: aload_0
    //   80: getfield 72	com/instagram/android/adapter/row/FeedImageRowAdapter:mImageViewOnTouchListener	Landroid/view/View$OnTouchListener;
    //   83: invokevirtual 372	com/instagram/android/imagecache/IgProgressImageView:setOnTouchListener	(Landroid/view/View$OnTouchListener;)V
    //   86: aload_2
    //   87: invokevirtual 376	com/instagram/android/model/Media:getLikeCount	()Ljava/lang/Integer;
    //   90: invokevirtual 379	java/lang/Integer:intValue	()I
    //   93: ifle +250 -> 343
    //   96: aload_1
    //   97: getfield 191	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:likeText	Landroid/widget/TextView;
    //   100: aload_2
    //   101: invokevirtual 382	com/instagram/android/model/Media:getLikeText	()Ljava/lang/CharSequence;
    //   104: invokevirtual 278	android/widget/TextView:setText	(Ljava/lang/CharSequence;)V
    //   107: aload_1
    //   108: getfield 191	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:likeText	Landroid/widget/TextView;
    //   111: new 280	android/text/method/LinkMovementMethod
    //   114: dup
    //   115: invokespecial 281	android/text/method/LinkMovementMethod:<init>	()V
    //   118: invokevirtual 285	android/widget/TextView:setMovementMethod	(Landroid/text/method/MovementMethod;)V
    //   121: aload_1
    //   122: getfield 187	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:likesGroup	Landroid/view/ViewGroup;
    //   125: iconst_0
    //   126: invokevirtual 383	android/view/ViewGroup:setVisibility	(I)V
    //   129: aload_2
    //   130: invokevirtual 386	com/instagram/android/model/Media:getCommentCount	()Ljava/lang/Integer;
    //   133: invokevirtual 379	java/lang/Integer:intValue	()I
    //   136: ifle +219 -> 355
    //   139: aload_1
    //   140: getfield 176	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:textViewComments	Landroid/widget/TextView;
    //   143: aload_2
    //   144: invokevirtual 389	com/instagram/android/model/Media:getCommentText	()Ljava/lang/CharSequence;
    //   147: invokevirtual 278	android/widget/TextView:setText	(Ljava/lang/CharSequence;)V
    //   150: aload_1
    //   151: getfield 176	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:textViewComments	Landroid/widget/TextView;
    //   154: new 280	android/text/method/LinkMovementMethod
    //   157: dup
    //   158: invokespecial 281	android/text/method/LinkMovementMethod:<init>	()V
    //   161: invokevirtual 285	android/widget/TextView:setMovementMethod	(Landroid/text/method/MovementMethod;)V
    //   164: aload_1
    //   165: getfield 183	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:commentsGroup	Landroid/view/ViewGroup;
    //   168: iconst_0
    //   169: invokevirtual 383	android/view/ViewGroup:setVisibility	(I)V
    //   172: iload 4
    //   174: ifeq +193 -> 367
    //   177: aload_1
    //   178: getfield 213	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:buttonViewGroup	Landroid/view/ViewGroup;
    //   181: bipush 8
    //   183: invokevirtual 383	android/view/ViewGroup:setVisibility	(I)V
    //   186: aload_1
    //   187: getfield 198	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:toggleButtonLike	Landroid/widget/CompoundButton;
    //   190: aconst_null
    //   191: invokevirtual 393	android/widget/CompoundButton:setOnCheckedChangeListener	(Landroid/widget/CompoundButton$OnCheckedChangeListener;)V
    //   194: aload_1
    //   195: getfield 198	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:toggleButtonLike	Landroid/widget/CompoundButton;
    //   198: aload_2
    //   199: invokevirtual 396	com/instagram/android/model/Media:isHasLiked	()Z
    //   202: invokevirtual 400	android/widget/CompoundButton:setChecked	(Z)V
    //   205: aload_1
    //   206: getfield 198	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:toggleButtonLike	Landroid/widget/CompoundButton;
    //   209: new 6	com/instagram/android/adapter/row/FeedImageRowAdapter$1
    //   212: dup
    //   213: aload_0
    //   214: aload_2
    //   215: invokespecial 401	com/instagram/android/adapter/row/FeedImageRowAdapter$1:<init>	(Lcom/instagram/android/adapter/row/FeedImageRowAdapter;Lcom/instagram/android/model/Media;)V
    //   218: invokevirtual 393	android/widget/CompoundButton:setOnCheckedChangeListener	(Landroid/widget/CompoundButton$OnCheckedChangeListener;)V
    //   221: aload_1
    //   222: getfield 202	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:buttonComment	Landroid/widget/ImageView;
    //   225: new 8	com/instagram/android/adapter/row/FeedImageRowAdapter$2
    //   228: dup
    //   229: aload_0
    //   230: aload_2
    //   231: invokespecial 402	com/instagram/android/adapter/row/FeedImageRowAdapter$2:<init>	(Lcom/instagram/android/adapter/row/FeedImageRowAdapter;Lcom/instagram/android/model/Media;)V
    //   234: invokevirtual 403	android/widget/ImageView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   237: aload_1
    //   238: getfield 217	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:doubleTapHeart	Landroid/widget/ImageView;
    //   241: aconst_null
    //   242: invokevirtual 104	android/widget/ImageView:setImageDrawable	(Landroid/graphics/drawable/Drawable;)V
    //   245: aload_1
    //   246: getfield 217	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:doubleTapHeart	Landroid/widget/ImageView;
    //   249: invokevirtual 406	android/widget/ImageView:clearAnimation	()V
    //   252: aload_1
    //   253: getfield 209	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:buttonOptions	Lcom/instagram/android/widget/MediaOptionsButton;
    //   256: aload_2
    //   257: invokevirtual 410	com/instagram/android/widget/MediaOptionsButton:setMedia	(Lcom/instagram/android/model/Media;)V
    //   260: aload_1
    //   261: getfield 209	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:buttonOptions	Lcom/instagram/android/widget/MediaOptionsButton;
    //   264: aload_0
    //   265: getfield 54	com/instagram/android/adapter/row/FeedImageRowAdapter:mActivity	Landroid/app/Activity;
    //   268: invokevirtual 413	com/instagram/android/widget/MediaOptionsButton:setActivityContext	(Landroid/content/Context;)V
    //   271: aload_1
    //   272: getfield 209	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:buttonOptions	Lcom/instagram/android/widget/MediaOptionsButton;
    //   275: aload_0
    //   276: getfield 60	com/instagram/android/adapter/row/FeedImageRowAdapter:mFragmentManager	Landroid/support/v4/app/FragmentManager;
    //   279: invokevirtual 417	com/instagram/android/widget/MediaOptionsButton:setFragmentManager	(Landroid/support/v4/app/FragmentManager;)V
    //   282: aload_1
    //   283: getfield 209	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:buttonOptions	Lcom/instagram/android/widget/MediaOptionsButton;
    //   286: aload_0
    //   287: getfield 66	com/instagram/android/adapter/row/FeedImageRowAdapter:mLoaderManager	Landroid/support/v4/app/LoaderManager;
    //   290: invokevirtual 421	com/instagram/android/widget/MediaOptionsButton:setLoaderManager	(Landroid/support/v4/app/LoaderManager;)V
    //   293: return
    //   294: astore 10
    //   296: aload 7
    //   298: ifnull -237 -> 61
    //   301: aload 7
    //   303: invokevirtual 357	java/io/InputStream:close	()V
    //   306: goto -245 -> 61
    //   309: astore 11
    //   311: goto -250 -> 61
    //   314: astore 8
    //   316: aload 7
    //   318: ifnull +8 -> 326
    //   321: aload 7
    //   323: invokevirtual 357	java/io/InputStream:close	()V
    //   326: aload 8
    //   328: athrow
    //   329: aload_1
    //   330: getfield 172	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:imageView	Lcom/instagram/android/imagecache/IgProgressImageView;
    //   333: aload_2
    //   334: invokevirtual 424	com/instagram/android/model/Media:getSizedUrl	()Ljava/lang/String;
    //   337: invokevirtual 425	com/instagram/android/imagecache/IgProgressImageView:setUrl	(Ljava/lang/String;)V
    //   340: goto -279 -> 61
    //   343: aload_1
    //   344: getfield 187	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:likesGroup	Landroid/view/ViewGroup;
    //   347: bipush 8
    //   349: invokevirtual 383	android/view/ViewGroup:setVisibility	(I)V
    //   352: goto -223 -> 129
    //   355: aload_1
    //   356: getfield 183	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:commentsGroup	Landroid/view/ViewGroup;
    //   359: bipush 8
    //   361: invokevirtual 383	android/view/ViewGroup:setVisibility	(I)V
    //   364: goto -192 -> 172
    //   367: aload_1
    //   368: getfield 213	com/instagram/android/adapter/row/FeedImageRowAdapter$Holder:buttonViewGroup	Landroid/view/ViewGroup;
    //   371: iconst_0
    //   372: invokevirtual 383	android/view/ViewGroup:setVisibility	(I)V
    //   375: goto -189 -> 186
    //   378: astore 12
    //   380: goto -319 -> 61
    //   383: astore 9
    //   385: goto -59 -> 326
    //
    // Exception table:
    //   from	to	target	type
    //   33	51	294	java/io/FileNotFoundException
    //   301	306	309	java/io/IOException
    //   33	51	314	finally
    //   56	61	378	java/io/IOException
    //   321	326	383	java/io/IOException
  }

  public View newView()
  {
    View localView = LayoutInflater.from(this.mContext).inflate(2130903100, null);
    localView.setTag(getHolder(localView));
    return localView;
  }

  public static class Builder
  {
    private final FeedImageRowAdapter A;

    public Builder(Context paramContext)
    {
      this.A = new FeedImageRowAdapter(paramContext);
    }

    public FeedImageRowAdapter create()
    {
      return this.A;
    }

    public Builder setActivity(Activity paramActivity)
    {
      FeedImageRowAdapter.access$002(this.A, paramActivity);
      return this;
    }

    public Builder setFragmentManager(FragmentManager paramFragmentManager)
    {
      FeedImageRowAdapter.access$102(this.A, paramFragmentManager);
      return this;
    }

    public Builder setImageViewOnClickListener(View.OnTouchListener paramOnTouchListener)
    {
      FeedImageRowAdapter.access$302(this.A, paramOnTouchListener);
      return this;
    }

    public Builder setLoaderManager(LoaderManager paramLoaderManager)
    {
      FeedImageRowAdapter.access$202(this.A, paramLoaderManager);
      return this;
    }
  }

  public static class HeaderHolder
  {
    TextView location;
    ImageView locationGlyph;
    IgImageView profileImageView;
    TextView profileName;
    TextView timeAgo;
  }

  public static class Holder
  {
    ImageView buttonComment;
    MediaOptionsButton buttonOptions;
    ViewGroup buttonViewGroup;
    ViewGroup commentsGroup;
    ImageView doubleTapHeart;
    FeedImageRowAdapter.HeaderHolder header;
    IgProgressImageView imageView;
    TextView likeText;
    ViewGroup likesGroup;
    TextView textViewComments;
    CompoundButton toggleButtonLike;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.FeedImageRowAdapter
 * JD-Core Version:    0.6.0
 */