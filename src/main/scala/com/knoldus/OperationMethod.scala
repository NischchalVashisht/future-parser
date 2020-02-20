package com.knoldus

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class UserAndPost(id: User, post: List[Post])

case class PostAndComment(id: Post, comment: List[Comments])

/**
 * Base Class where i get Max Post by User and Max Comment on Post
 */
class OperationMethod {

  /**
   *
   * @param listOfUser List Of User Case Class
   * @param listOfPost List Of Post Case Class
   * @return List Of UserAndPost Case Class
   */
  def getUsersPost(listOfUser: Future[List[User]], listOfPost: Future[List[Post]]): Future[List[UserAndPost]] = {

     listOfUser.map(listUser=>listOfPost.map(listPost=>listUser.map(user=>UserAndPost(user,listPost.filter(_.userId==user.id))))).flatten

  }

  /**
   *
   * @param listOfComment List Of Comment Class
   * @param listOfPost List Of post Class
   * @return List of PostAndComment case Class
   */
  def getPostWithComment(listOfComment: Future[List[Comments]], listOfPost: Future[List[Post]]): Future[List[PostAndComment]] = {
      listOfPost.map(listPost=>listOfComment.map(listComment=>listPost.map(post=>PostAndComment(post,listComment.filter(_.postId==post.id))))).flatten

  }

  /**
   *
   * @param list List of User and Post
   * @return Name of user with MaxPost
   */
  def userWithMaxPost(list: Future[List[UserAndPost]]): Future[String] = {

       val sortedUserAndPost= list.map(_.sortWith((x,y)=>x.post.length<=y.post.length))
        sortedUserAndPost.map(s=>s.last.id.name)
 }

  /**
   *
   * @param list List of PostAndComment case Class
   * @param user List of User case Class
   * @return name of user with max post Comment
   */
  def postWithMaxComment(list: Future[List[PostAndComment]], user: Future[List[User]]): Future[String] = {


    val sortedPostAndComment=list.map(_.sortWith((x,y)=>x.comment.length<=y.comment.length))
    val resultPost=sortedPostAndComment.map(s=>s.last.id.userId)
     user.map(s=>resultPost.map(j=>s.filter(_.id==j).map(_.name).head)).flatten

  }
}
