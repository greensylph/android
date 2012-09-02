package com.instagram.android.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabWidget;
import android.widget.TextView;
import com.instagram.android.Preferences;
import com.instagram.android.activity.MainTabActivity;
import com.instagram.android.adapter.CommentThreadAdapter;
import com.instagram.android.adapter.FilterHashTagsAndNamesAdapter;
import com.instagram.android.model.Comment;
import com.instagram.android.model.Media;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.android.model.User;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.service.ClickManager;
import com.instagram.android.service.MediaStore;
import com.instagram.android.widget.CommentAutoCompleteTextView;
import com.instagram.android.widget.LoadMoreButton.LoadMoreInterface;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.request.FetchAllCommentsRequest;
import com.instagram.api.request.MediaFeedRequest;
import com.instagram.api.request.PostCommentRequest;
import com.instagram.util.FragmentUtil;
import java.util.ArrayList;
import java.util.Iterator;

public class CommentThreadFragment extends ListFragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory, LoadMoreButton.LoadMoreInterface
{
  private static final int COMMENT_POST_REQUEST_ID = 0;
  public static final String INTENT_EXTRA_MEDIA_ID = "com.instagram.android.fragment.CommentThreadFragment.INTENT_EXTRA_MEDIA_ID";
  public static final String INTENT_EXTRA_SHOW_KEYBOARD = "com.instagram.android.fragment.CommentThreadFragment.INTENT_EXTRA_SHOW_KEYBOARD";
  private final DataSetObserver editStatusUpdateObserver = new CommentThreadFragment.3(this);
  private CommentThreadAdapter mAdapter;
  private BroadcastReceiver mBroadcastReceiver = new CommentThreadFragment.2(this);
  private CommentAutoCompleteTextView mCommentEditText;
  private Handler mHandler = new Handler();
  private boolean mIsEditing = false;
  private boolean mIsLoading = false;
  private Media mMedia;
  private int mOldOrientation;
  private View mView;
  private BroadcastReceiver updatedMediaReceiver = new CommentThreadFragment.1(this);

  private boolean canEdit()
  {
    int i = 0;
    if (this.mMedia == null);
    while (true)
    {
      return i;
      if (this.mAdapter.getCount() == 0)
        continue;
      User localUser = AuthHelper.getInstance().getCurrentUser();
      if (localUser == null)
        continue;
      boolean bool1 = localUser.isIsStaff();
      boolean bool2 = this.mMedia.getUser().equals(localUser);
      int j = 0;
      Iterator localIterator = this.mAdapter.getComments().iterator();
      while (localIterator.hasNext())
      {
        if (!((Comment)localIterator.next()).getUser().equals(localUser))
          continue;
        j = 1;
      }
      if ((!bool2) && (j == 0) && (!bool1))
        continue;
      i = 1;
    }
  }

  private void configureAutoComplete()
  {
    if (this.mMedia != null)
    {
      FilterHashTagsAndNamesAdapter localFilterHashTagsAndNamesAdapter = new FilterHashTagsAndNamesAdapter(getActivity(), this.mMedia);
      this.mCommentEditText.setAdapter(localFilterHashTagsAndNamesAdapter);
    }
  }

  private View getButtonSend()
  {
    return this.mView.findViewById(2131492977);
  }

  private CommentAutoCompleteTextView getCommentEditText()
  {
    if (this.mCommentEditText == null)
    {
      this.mCommentEditText = ((CommentAutoCompleteTextView)this.mView.findViewById(2131492976));
      if (this.mMedia != null)
        configureAutoComplete();
    }
    return this.mCommentEditText;
  }

  private void hideKeyboard(EditText paramEditText)
  {
    ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
  }

  private void postCommentWithText(String paramString)
  {
    if (this.mMedia != null)
    {
      User localUser = AuthHelper.getInstance().getCurrentUser();
      postCommentWithText(paramString, this.mMedia, localUser);
    }
  }

  private void postCommentWithText(String paramString, Media paramMedia, User paramUser)
  {
    if (this.mMedia != null)
    {
      Comment localComment = new Comment(paramString, paramMedia, paramUser, this);
      this.mMedia.commentPostRequestStart(localComment, getActivity());
      new PostCommentRequest(getActivity(), getLoaderManager()).perform(localComment);
    }
  }

  private void scrollToBottom(int paramInt)
  {
    if ((getView() != null) && (getListView() != null) && (getListView().getHandler() != null))
      getListView().getHandler().postDelayed(new CommentThreadFragment.5(this), paramInt);
  }

  private void sendCommentFromTextView(TextView paramTextView)
  {
    if (paramTextView.getText().length() > 0)
    {
      postCommentWithText(paramTextView.getText().toString());
      paramTextView.setText("");
      scrollToBottom(0);
    }
  }

  private boolean shouldShowKeyboard()
  {
    if ((getArguments() != null) && (getArguments().getBoolean("com.instagram.android.fragment.CommentThreadFragment.INTENT_EXTRA_SHOW_KEYBOARD")));
    for (int i = 1; ; i = 0)
      return i;
  }

  public static void show(FragmentManager paramFragmentManager, Media paramMedia, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("com.instagram.android.fragment.CommentThreadFragment.INTENT_EXTRA_MEDIA_ID", paramMedia.getId());
    localBundle.putBoolean("com.instagram.android.fragment.CommentThreadFragment.INTENT_EXTRA_SHOW_KEYBOARD", paramBoolean);
    FragmentUtil.navigateTo(paramFragmentManager, new CommentThreadFragment(), localBundle);
  }

  private void toggleEditView()
  {
    if (!this.mIsEditing);
    for (boolean bool = true; ; bool = false)
    {
      this.mIsEditing = bool;
      EditText localEditText = (EditText)getView().findViewById(2131492976);
      if (this.mIsEditing)
      {
        getListView().requestFocus();
        hideKeyboard(localEditText);
      }
      this.mAdapter.notifyDataSetChanged();
      return;
    }
  }

  private void updateProgressBarState()
  {
    this.mHandler.post(new CommentThreadFragment.16(this));
  }

  private boolean validate()
  {
    boolean bool = true;
    if ((this.mMedia == null) || (TextUtils.isEmpty(getCommentEditText().getText())))
    {
      getButtonSend().setEnabled(false);
      bool = false;
    }
    while (true)
    {
      return bool;
      getButtonSend().setEnabled(bool);
    }
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new CommentThreadFragment.14(this);
  }

  public boolean hasMoreItems()
  {
    if ((this.mMedia != null) && (this.mMedia.hasMoreComments()));
    for (int i = 1; ; i = 0)
      return i;
  }

  public boolean isEditing()
  {
    return this.mIsEditing;
  }

  public boolean isLoadMoreVisible()
  {
    return true;
  }

  public boolean isLoading()
  {
    if ((this.mMedia != null) && (this.mMedia.isLoadingMoreComments()));
    for (int i = 1; ; i = 0)
      return i;
  }

  public void loadMore()
  {
    new FetchAllCommentsRequest(getActivity(), getLoaderManager(), 0, new CommentThreadFragment.15(this)).perform(this.mMedia);
  }

  protected MediaFeedRequest makeRequest(AbstractStreamingApiCallbacks<MediaFeedResponse> paramAbstractStreamingApiCallbacks)
  {
    return new CommentThreadFragment.17(this, this, 2131492873, paramAbstractStreamingApiCallbacks);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (((MainTabActivity)getActivity().getParent()).getTabHost() != null)
    {
      ((MainTabActivity)getActivity().getParent()).getTabWidget().setVisibility(8);
      super.onConfigurationChanged(paramConfiguration);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    this.mMedia = MediaStore.getInstance(getActivity()).get(getArguments().getString("com.instagram.android.fragment.CommentThreadFragment.INTENT_EXTRA_MEDIA_ID"));
    this.mAdapter = new CommentThreadAdapter(getActivity().getApplicationContext(), getActivity(), this);
    if (this.mMedia == null)
      makeRequest(new CommentThreadFragment.4(this)).perform();
    while (true)
    {
      setListAdapter(this.mAdapter);
      LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.updatedMediaReceiver, new IntentFilter(Media.getMediaBroadcastString(getArguments().getString("com.instagram.android.fragment.CommentThreadFragment.INTENT_EXTRA_MEDIA_ID"))));
      super.onCreate(paramBundle);
      return;
      this.mAdapter.setMedia(this.mMedia);
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mView = paramLayoutInflater.inflate(2130903083, paramViewGroup, false);
    CommentAutoCompleteTextView localCommentAutoCompleteTextView = getCommentEditText();
    localCommentAutoCompleteTextView.setOnEditorActionListener(new CommentThreadFragment.6(this));
    ((Button)getButtonSend()).setOnClickListener(new CommentThreadFragment.7(this, localCommentAutoCompleteTextView));
    localCommentAutoCompleteTextView.addTextChangedListener(new CommentThreadFragment.8(this));
    localCommentAutoCompleteTextView.setBackButtonListener(new CommentThreadFragment.9(this));
    localCommentAutoCompleteTextView.setSimpleChangedLayoutListener(new CommentThreadFragment.10(this));
    if (!Preferences.getInstance(getActivity()).isLoggedIn())
    {
      hideKeyboard(localCommentAutoCompleteTextView);
      localCommentAutoCompleteTextView.setVisibility(8);
    }
    return this.mView;
  }

  public void onDestroy()
  {
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.updatedMediaReceiver);
    super.onDestroy();
  }

  public void onDestroyView()
  {
    this.mCommentEditText = null;
    this.mView = null;
    super.onDestroyView();
  }

  public void onPause()
  {
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.mBroadcastReceiver);
    ClickManager.getInstance().setUserLinkClickListener(null);
    MainTabActivity.setShouldShowToast(true);
    getActivity().setRequestedOrientation(this.mOldOrientation);
    this.mAdapter.unregisterDataSetObserver(this.editStatusUpdateObserver);
    getActivity().getParent().getWindow().setSoftInputMode(48);
    ((MainTabActivity)getActivity().getParent()).getTabWidget().setVisibility(0);
    hideKeyboard(getCommentEditText());
    super.onPause();
  }

  public void onPostCommentFailed(Comment paramComment, boolean paramBoolean, String paramString)
  {
    if ((paramBoolean) && (paramString != null))
      getActivity().runOnUiThread(new CommentThreadFragment.12(this, paramString, paramComment));
    while (true)
    {
      return;
      getActivity().runOnUiThread(new CommentThreadFragment.13(this));
    }
  }

  public void onResume()
  {
    super.onResume();
    MainTabActivity.setShouldShowToast(false);
    this.mOldOrientation = getActivity().getRequestedOrientation();
    getActivity().setRequestedOrientation(-1);
    this.mAdapter.registerDataSetObserver(this.editStatusUpdateObserver);
    getActivity().getParent().getWindow().setSoftInputMode(16);
    ((MainTabActivity)getActivity().getParent()).getTabWidget().setVisibility(8);
    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.instagram.android.service.action_bar_clicked"));
    if (shouldShowKeyboard())
    {
      ((InputMethodManager)getActivity().getSystemService("input_method")).toggleSoftInput(2, 1);
      getCommentEditText().requestFocus();
    }
    while (true)
    {
      ClickManager.getInstance().setUserLinkClickListener(new CommentThreadFragment.11(this));
      validate();
      return;
      getActivity().getWindow().setSoftInputMode(2);
    }
  }

  public void onStart()
  {
    super.onStart();
  }

  public void onStop()
  {
    super.onStop();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    getListView().setTranscriptMode(1);
    getListView().setStackFromBottom(true);
    updateProgressBarState();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment
 * JD-Core Version:    0.6.0
 */