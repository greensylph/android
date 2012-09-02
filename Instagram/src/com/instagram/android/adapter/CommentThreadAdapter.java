package com.instagram.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.instagram.android.adapter.row.CommentRowAdapter;
import com.instagram.android.adapter.row.CommentRowAdapter.Holder;
import com.instagram.android.adapter.row.LoadMoreRowAdapter;
import com.instagram.android.adapter.row.LoadMoreRowAdapter.Holder;
import com.instagram.android.fragment.CommentThreadFragment;
import com.instagram.android.model.Comment;
import com.instagram.android.model.Media;
import java.util.ArrayList;
import java.util.List;

public class CommentThreadAdapter extends EnhancedArrayAdapter
{
  private static final int ITEM_COMMENT = 0;
  private static final int ITEM_LOAD_ALL = 1;
  private static final int ITEM_VIEW_TYPE_COUNT = 2;
  private static final int REASONABLE_COMMENT_LOADING_SIZE = 500;
  private Activity mActivity;
  private CommentThreadFragment mFragment;
  private Media mMedia;

  public CommentThreadAdapter(Context paramContext, Activity paramActivity, CommentThreadFragment paramCommentThreadFragment)
  {
    super(paramContext);
    this.mActivity = paramActivity;
    this.mFragment = paramCommentThreadFragment;
  }

  private boolean showLoadAllCommentsButton()
  {
    if ((this.mMedia != null) && (this.mMedia.hasMoreComments()) && (this.mMedia.getCommentCount().intValue() < 500));
    for (int i = 1; ; i = 0)
      return i;
  }

  protected void bindView(View paramView, Context paramContext, int paramInt)
  {
    switch (getItemViewType(paramInt))
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return;
      CommentRowAdapter.bindView((CommentRowAdapter.Holder)paramView.getTag(), (Comment)getItem(paramInt), this.mActivity, this.mFragment);
      continue;
      LoadMoreRowAdapter.bindView((LoadMoreRowAdapter.Holder)paramView.getTag(), this.mFragment);
    }
  }

  public ArrayList<Comment> getComments()
  {
    return this.mMedia.getActiveComments();
  }

  public int getCount()
  {
    int i;
    if (this.mMedia != null)
      if (showLoadAllCommentsButton())
        i = 1 + this.mMedia.getActiveComments().size();
    while (true)
    {
      return i;
      i = this.mMedia.getActiveComments().size();
      continue;
      i = 0;
    }
  }

  public Object getItem(int paramInt)
  {
    if (showLoadAllCommentsButton());
    for (Object localObject = super.getItem(paramInt - 1); ; localObject = super.getItem(paramInt))
      return localObject;
  }

  public int getItemViewType(int paramInt)
  {
    if ((paramInt == 0) && (showLoadAllCommentsButton()));
    for (int i = 1; ; i = 0)
      return i;
  }

  public int getViewTypeCount()
  {
    return 2;
  }

  protected View newView(Context paramContext, int paramInt, ViewGroup paramViewGroup)
  {
    switch (getItemViewType(paramInt))
    {
    default:
      throw new RuntimeException("No item view type found");
    case 0:
    case 1:
    }
    for (View localView = CommentRowAdapter.newView(paramContext, paramViewGroup); ; localView = LoadMoreRowAdapter.newView(paramContext, paramViewGroup))
      return localView;
  }

  public void setMedia(Media paramMedia)
  {
    this.mMedia = paramMedia;
    this.mObjects.clear();
    ArrayList localArrayList = this.mMedia.getActiveComments();
    if (localArrayList != null)
      this.mObjects.addAll(localArrayList);
    notifyDataSetChanged();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.CommentThreadAdapter
 * JD-Core Version:    0.6.0
 */