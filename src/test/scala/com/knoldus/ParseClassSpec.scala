package com.knoldus


import com.knoldus.controller.{JsonAssignment, ParseClass}
import com.knoldus.model.{AddressDetail, Comments, CompanyDetail, GeoDetail, GetUrl, Post, PostAndComment, User, UserAndPost}
import net.liftweb.json.DefaultFormats
import org.scalatest._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec
import org.mockito.MockitoSugar
import org.mockito.Mockito.when
import net.liftweb.json.Serialization.write

import scala.concurrent.Future

class ParseClassSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar{

   val getUrlData: GetUrl = mock[GetUrl]
   var parseClass:ParseClass=_

  val stubUserList = List(User("1", "1", "3", "4", AddressDetail("5", "6", "7", "8", GeoDetail("9", "10")), "11", "12",
    CompanyDetail("13", "14", "15")),
    User("2", "2", "3", "4", AddressDetail("5", "6", "7", "8", GeoDetail("9", "10")), "11", "12",
      CompanyDetail("13", "14", "15")))
  val stubPostList = List(Post("1", "2", "3", "4"), Post("2", "2", "3", "4"))
  val stubCommentsList = List(Comments("1", "2", "3", "4", "5"), Comments("2", "2", "3", "4", "5"))


  val userUrl = "https://jsonplaceholder.typicode.com/users"
  val postUrl = "https://jsonplaceholder.typicode.com/posts"
  val commentUrl = "https://jsonplaceholder.typicode.com/comments"

  override def beforeAll(): Unit = {
    parseClass=new ParseClass(getUrlData)
    implicit val formats: DefaultFormats.type = DefaultFormats
    val userJsonData: String = write(stubUserList)
    val postJsonData: String = write(stubPostList)
    val commentJsonData: String = write(stubCommentsList)


    when(getUrlData.readData(userUrl)).thenReturn(userJsonData)
    when(getUrlData.readData(postUrl)).thenReturn(postJsonData)
    when(getUrlData.readData(commentUrl)).thenReturn(commentJsonData)
  }

  override def afterAll():Unit={
    parseClass=null
  }


  "Parsing function - parseData " should "Parse user json string data into a case class of User" in {
    val futureListOfUsers: Future[List[User]] = parseClass.parseData[User](userUrl)
    futureListOfUsers map { eachUser => assert(eachUser == stubUserList)
    }
  }

  "Parsing function - parseData " should "Parse posts json string data into a case class of Post" in {
    val futureListOfPosts: Future[List[Post]] = parseClass.parseData[Post](postUrl)
    futureListOfPosts map { eachPost => assert(eachPost == stubPostList)
    }
  }

  "Parsing function - parseData " should "Parse comments json string data into a case class of Comment" in {
    val futureListOfComments: Future[List[Comments]] = parseClass.parseData[Comments](commentUrl)
    futureListOfComments map { eachComment => assert(eachComment == stubCommentsList)
    }
  }
}
