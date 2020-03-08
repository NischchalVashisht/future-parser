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
  val classParseInstance = new ParseClass(new GetUrl)
  val operation = new OperationMethod
  val commentList: Future[List[Comments]] = classParseInstance.parseData[Comments]("https://jsonplaceholder.typicode.com/comments").fallbackTo(Future{

      List()
  })
  val usrList: Future[List[User]] = classParseInstance.parseData[User]("https://jsonplaceholder.typicode.com/users").fallbackTo(Future{
    List()
  })
  val pstList: Future[List[Post]] = classParseInstance.parseData[Post]("https://jsonplaceholder.typicode.com/posts").fallbackTo(Future{
    List()
  })

  val returnOfUserPost: Future[List[UserAndPost]] = operation.getUsersPost(usrList, pstList)
  val returnOfCommentPost: Future[List[PostAndComment]] = operation.getPostWithComment(commentList, pstList)

  /**
   *
   * @return maxPost in  Future[String]
   */
  def maxPost: Future[String] = {
    val maxPost = operation.userWithMaxPost(returnOfUserPost)
    maxPost
  }

  /**
   *
   * @return maxPostComment in Future[String]
   */
  def maxPostComment: Future[String] = {
    operation.postWithMaxComment(returnOfCommentPost, usrList)
    val maxPostComment = operation.postWithMaxComment(returnOfCommentPost, usrList)
    maxPostComment
  }


}
