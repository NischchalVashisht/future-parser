package com.knoldus

import scala.concurrent.Future

/**
 *  This is Object place where we do all our Processing
 */
object JsonAssignment {
  val cp = new CommentParse
  val up = new UserParse
  val pp = new PostParse
  val op = new OperationMethod
  val cmntList: Future[List[Comments]] = cp.parseJson("https://jsonplaceholder.typicode.com/comments")
  val usrList: Future[List[User]] = up.parseJson("https://jsonplaceholder.typicode.com/users")
  val pstList: Future[List[Post]] = pp.parseJson("https://jsonplaceholder.typicode.com/posts")

  val returnOfUserPost: Future[List[UserAndPost]] = op.getUsersPost(usrList, pstList)
  val returnOfCommentPost: Future[List[PostAndComment]] = op.getPostWithComment(cmntList, pstList)

  /**
   *
   * @return maxPost in  Future[String]
   */
  def maxPost:Future[String] = {
    val maxPost = op.userWithMaxPost(returnOfUserPost)
     maxPost
  }

  /**
   *
   * @return maxPostComment in Future[String]
   */
  def maxPostComment:Future[String] = {
    op.postWithMaxComment(returnOfCommentPost, usrList)
    val maxPostComment = op.postWithMaxComment(returnOfCommentPost, usrList)
    maxPostComment
  }


}
