package com.knoldus

case class UserAndPost(id: User, post: List[Post])

case class PostAndComment(id: Post, comment: List[Comments])

class OperationMethod {

  def getUsersPost(listOfUser: List[User], listOfPost: List[Post]): List[UserAndPost] = {

    @scala.annotation.tailrec
    def innerTest(userList: List[User], postOfUser: List[UserAndPost]): List[UserAndPost] = {
      userList match {
        case Nil => postOfUser
        case post :: Nil => postOfUser :+ UserAndPost(post, listOfPost.filter(_.userId == post.id))
        case post :: rest => innerTest(rest, postOfUser :+ UserAndPost(post, listOfPost.filter(_.userId == post.id)))
      }
    }

    innerTest(listOfUser, List())

  }

  def getPostWithComment(listOfComment: List[Comments], listOfPost: List[Post]): List[PostAndComment] = {

    @scala.annotation.tailrec
    def innerTest(postList: List[Post], commentsPerPost: List[PostAndComment]): List[PostAndComment] = {
      postList match {
        case Nil => commentsPerPost
        case post :: Nil => commentsPerPost :+ PostAndComment(post, listOfComment.filter(_.postId == post.id))
        case post :: rest => innerTest(rest, commentsPerPost :+ PostAndComment(post, listOfComment.filter(_.postId == post.id)))
      }
    }

    innerTest(listOfPost, List())

  }

  def userWithMaxPost(list: List[UserAndPost]): (String, Int) = {


    @scala.annotation.tailrec
    def innerTest(innerList: List[UserAndPost], result: (String, Int)): (String, Int) = {
      innerList match {
        case Nil => result
        case head :: Nil => if (head.post.length >= result._2) (head.id.name, head.post.length) else result
        case head :: tail => if (head.post.length >= result._2) innerTest(tail, (head.id.name, head.post.length))  else innerTest(tail, result)
      }

    }

    innerTest(list, ("", 0))
  }

  def postWithMaxComment(list: List[PostAndComment], user: List[User]): (String, String, Int) = {


    @scala.annotation.tailrec
    def innerTest(innerList: List[PostAndComment], result: (String, Int)): (String, Int) = {

      innerList match {
        case Nil => result
        case head :: Nil => if (head.comment.length >= result._2) (head.id.userId, head.comment.length)  else result
        case head :: tail => if (head.comment.length >= result._2) innerTest(tail, (head.id.userId, head.comment.length)) else innerTest(tail, result)
          }
     }

    val result = innerTest(list, ("", 0))
    val tupleList = user.filter(_.id == result._1)

    (tupleList.head.name, result._1, result._2)
  }
}
