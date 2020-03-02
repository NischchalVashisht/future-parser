package com.knoldus.controller

import com.knoldus.model._
import net.liftweb.json.JValue
import net.liftweb.json.JsonAST.JValue

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * This is Object place where we do all our Processing
 */
object JsonAssignment {
  val cp = new ParseClass
  val commentJvalue: Future[JValue] = cp.parseJson("https://jsonplaceholder.typicode.com/comment")
  val userJvalue: Future[JValue] = cp.parseJson("https://jsonplaceholder.typicode.com/users")
  val postJvalue: Future[JValue] = cp.parseJson("https://jsonplaceholder.typicode.com/posts")
  val op = new OperationMethod
  val cmntList: Future[List[Comments]] = cp.parseData[Comments](commentJvalue)
  val usrList: Future[List[User]] = cp.parseData[User](userJvalue)
  val pstList: Future[List[Post]] = cp.parseData[Post](postJvalue)

  val returnOfUserPost: Future[List[UserAndPost]] = op.getUsersPost(usrList, pstList)
  val returnOfCommentPost: Future[List[PostAndComment]] = op.getPostWithComment(cmntList, pstList)

  /**
   *
   * @return maxPost in  Future[String]
   */
  def maxPost: Future[String] = {
    val maxPost = op.userWithMaxPost(returnOfUserPost)
    //maxPost.map(println)
    maxPost
  }

  /**
   *
   * @return maxPostComment in Future[String]
   */
  def maxPostComment: Future[String] = {
    op.postWithMaxComment(returnOfCommentPost, usrList)
    val maxPostComment = op.postWithMaxComment(returnOfCommentPost, usrList)
    // maxPostComment.map(println)
    maxPostComment
  }


}
