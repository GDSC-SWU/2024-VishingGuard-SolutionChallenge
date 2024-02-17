package com.example.vishingguard.community

object PostData {
    private var postId: String? = null
    private var title: String? = null
    private var content: String? = null
    private var comment: String? = null
    private var commentId: String? = null

    fun setPostId(postId: String) {
        this.postId = postId
    }
    fun setPostContent(title: String, content: String) {
        this.title = title
        this.content = content
    }
    fun setCommentId(commentId: String) {
        this.commentId = commentId
    }

    fun setComment(comment: String) {
        this.comment = comment
    }

    fun getPostId(): String? {
        return postId
    }
    fun getTitle(): String? {
        return title
    }
    fun getContent(): String? {
        return content
    }
    fun getCommentId(): String? {
        return commentId
    }

    fun getComment(): String? {
        return comment
    }
}