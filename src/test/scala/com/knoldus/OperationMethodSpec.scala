package com.knoldus

import com.knoldus.controller.OperationMethod
import com.knoldus.model.{AddressDetail, Comments, CompanyDetail, GeoDetail, Post, PostAndComment, User, UserAndPost}
import net.liftweb.json.DefaultFormats
import org.scalatest._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec
import org.mockito.MockitoSugar
import org.mockito.Mockito.when
import net.liftweb.json.Serialization.write

import scala.concurrent.Future

class OperationMethodSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar{
  val stubUserList = List(User("1", "1", "3", "4", AddressDetail("5", "6", "7", "8", GeoDetail("9", "10")), "11", "12",
    CompanyDetail("13", "14", "15")),
    User("2", "2", "3", "4", AddressDetail("5", "6", "7", "8", GeoDetail("9", "10")), "11", "12",
      CompanyDetail("13", "14", "15")))
  val stubPostList = List(Post("1", "2", "3", "4"), Post("2", "2", "3", "4"))
  val stubCommentsList = List(Comments("1", "2", "3", "4", "5"), Comments("2", "2", "3", "4", "5"))
  val stubUserWithPostList = List(UserAndPost(stubUserList(0), List(stubPostList(0))),
    UserAndPost(stubUserList(1), List(stubPostList(1))))
  val stubPostWithCommentList = List(PostAndComment(stubPostList(0), List(stubCommentsList(1))),
    PostAndComment(stubPostList(1), List(stubCommentsList(1))))

  var operationMethod:OperationMethod=_

  override def beforeAll(): Unit = {
    operationMethod=new OperationMethod
  }

  override def afterAll():Unit={
    operationMethod=null
  }

  "getUserPost method " should " return List of UserAndPost " in {
    val expectedResult = stubUserWithPostList
    val actualResult: Future[List[UserAndPost]] = operationMethod.getUsersPost(Future(stubUserList),Future(stubPostList))
    actualResult map { value => assert(value == expectedResult) }
  }

  "getPostWithComment method " should " return List of PostAndComment " in {
    val expectedResult = stubPostWithCommentList
    val actualResult: Future[List[PostAndComment]] = operationMethod.getPostWithComment(Future(stubCommentsList),Future(stubPostList))
    actualResult map { value => assert(value == expectedResult) }
  }

  "userWithMaxPost method " should " find User with Maximum number of posts " in {
    val expectedResult = "1"
    val actualResult: Future[String] = operationMethod.userWithMaxPost(Future(stubUserWithPostList))
    actualResult map { value => assert(value == expectedResult) }
  }

  "postWithMaxComment" should " find User whose Post has maximum number of comments " in {
    val expectedResult = "1"
    val actualResult = operationMethod.postWithMaxComment(Future(stubPostWithCommentList),Future(stubUserList))
    actualResult map { value => assert(value == expectedResult) }
  }

}
