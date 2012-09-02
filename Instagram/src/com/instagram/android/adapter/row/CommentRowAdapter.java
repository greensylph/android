package com.instagram.android.adapter.row;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.instagram.android.fragment.CommentThreadFragment;
import com.instagram.android.imagecache.IgImageView;
import com.instagram.android.listener.UserLinkClickListener;
import com.instagram.android.model.Comment;
import com.instagram.android.model.Comment.CommentPostedState;
import com.instagram.android.model.Media;
import com.instagram.android.model.User;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.service.ClickManager;
import com.instagram.android.widget.IgDialogBuilder;

public class CommentRowAdapter
{
  public static void bindView(Holder paramHolder, Comment paramComment, Activity paramActivity, CommentThreadFragment paramCommentThreadFragment)
  {
    paramHolder.userImageview.setUrl(paramComment.getUser().getProfilePicUrl());
    paramHolder.userImageview.setOnClickListener(new View.OnClickListener(paramComment)
    {
      public void onClick(View paramView)
      {
        ClickManager.getInstance().getUserLinkClickListener().onClick(this.val$comment.getUser());
      }
    });
    Button localButton = paramHolder.deleteButton;
    int i;
    if ((paramCommentThreadFragment.isEditing()) && (canEdit(paramComment)))
    {
      i = 0;
      localButton.setVisibility(i);
      paramHolder.deleteButton.setOnClickListener(new View.OnClickListener(paramHolder)
      {
        public void onClick(View paramView)
        {
          if (this.val$holder.deleteConfirmedButton.getVisibility() == 8)
          {
            this.val$holder.deleteConfirmedButton.setVisibility(0);
            this.val$holder.deleteButton.setSelected(true);
          }
          while (true)
          {
            return;
            this.val$holder.deleteConfirmedButton.setVisibility(8);
            this.val$holder.deleteButton.setSelected(false);
          }
        }
      });
      paramHolder.deleteButton.setSelected(false);
      paramHolder.deleteConfirmedButton.setVisibility(8);
      paramHolder.deleteConfirmedButton.setOnClickListener(new View.OnClickListener(paramComment, paramCommentThreadFragment)
      {
        public void onClick(View paramView)
        {
          this.val$comment.remove(this.val$fragment.getLoaderManager());
        }
      });
      paramHolder.commentText.setText(paramComment.getFormattedCommentText());
      paramHolder.commentText.setMovementMethod(new LinkMovementMethod());
      paramHolder.timeAgo.setText(paramComment.getFormattedDate(paramActivity));
      paramHolder.failedButton.setVisibility(8);
      if (paramComment.getPostedState() != Comment.CommentPostedState.Posting)
        break label214;
      paramHolder.mProgressBar.setVisibility(0);
      label171: if (paramComment.getPostedState() != Comment.CommentPostedState.Failure)
        break label226;
      paramHolder.failedButton.setVisibility(0);
    }
    while (true)
    {
      paramHolder.failedButton.setOnClickListener(new View.OnClickListener(paramComment, paramCommentThreadFragment, paramActivity)
      {
        public void onClick(View paramView)
        {
          if (this.val$comment.wasMarkedAsSpam())
            this.val$fragment.onPostCommentFailed(this.val$comment, true, this.val$comment.getMarkedAsSpamErrorMessage());
          while (true)
          {
            return;
            IgDialogBuilder localIgDialogBuilder = new IgDialogBuilder(this.val$context);
            CommentRowAdapter.4.1 local1 = new CommentRowAdapter.4.1(this);
            localIgDialogBuilder.setMessage(2131230984).setPositiveButton(2131230985, local1).setNegativeButton(2131230986, local1).setCancelable(true).create().show();
          }
        }
      });
      return;
      i = 8;
      break;
      label214: paramHolder.mProgressBar.setVisibility(8);
      break label171;
      label226: paramHolder.failedButton.setVisibility(8);
    }
  }

  public static boolean canEdit(Comment paramComment)
  {
    int i = 0;
    User localUser = AuthHelper.getInstance().getCurrentUser();
    if (localUser != null)
    {
      boolean bool1 = localUser.isIsStaff();
      boolean bool2 = paramComment.getMedia().getUser().equals(localUser);
      boolean bool3 = paramComment.getUser().equals(localUser);
      if ((bool2) || (bool3) || (bool1))
        i = 1;
    }
    return i;
  }

  public static View newView(Context paramContext, ViewGroup paramViewGroup)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903099, paramViewGroup, false);
    Holder localHolder = new Holder();
    localHolder.userImageview = ((IgImageView)localView.findViewById(2131493010));
    localHolder.commentText = ((TextView)localView.findViewById(2131493011));
    localHolder.timeAgo = ((TextView)localView.findViewById(2131493012));
    localHolder.failedButton = ((Button)localView.findViewById(2131493015));
    localHolder.deleteButton = ((Button)localView.findViewById(2131493009));
    localHolder.deleteConfirmedButton = ((Button)localView.findViewById(2131493013));
    localHolder.mProgressBar = ((ProgressBar)localView.findViewById(2131493014));
    localView.setTag(localHolder);
    return localView;
  }

  public static class Holder
  {
    TextView commentText;
    Button deleteButton;
    Button deleteConfirmedButton;
    Button failedButton;
    ProgressBar mProgressBar;
    TextView timeAgo;
    IgImageView userImageview;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.CommentRowAdapter
 * JD-Core Version:    0.6.0
 */