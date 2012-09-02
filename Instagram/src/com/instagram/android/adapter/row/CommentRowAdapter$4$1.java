package com.instagram.android.adapter.row;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.instagram.android.fragment.CommentThreadFragment;
import com.instagram.android.model.Comment;
import com.instagram.android.model.Media;
import com.instagram.api.request.PostCommentRequest;

class CommentRowAdapter$4$1
  implements DialogInterface.OnClickListener
{
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case -1:
    case -2:
    }
    while (true)
    {
      return;
      this.this$0.val$comment.getMedia().commentPostRequestStart(this.this$0.val$comment, this.this$0.val$context);
      new PostCommentRequest(this.this$0.val$context, this.this$0.val$fragment.getLoaderManager()).perform(this.this$0.val$comment);
      paramDialogInterface.dismiss();
      continue;
      this.this$0.val$comment.remove(this.this$0.val$fragment.getLoaderManager());
      paramDialogInterface.dismiss();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.CommentRowAdapter.4.1
 * JD-Core Version:    0.6.0
 */