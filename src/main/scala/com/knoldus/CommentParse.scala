package com.knoldus
import scala.concurrent.duration._

import net.liftweb.json.JsonAST.JNothing
import net.liftweb.json.{DefaultFormats, JValue, parse}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

case class Comments(postId:String,id:String,name:String,email:String,body:String)

class CommentParse extends GetUrl {

  implicit val formats: DefaultFormats.type = DefaultFormats
  implicit def extract(json: JValue): String = json match {
    case JNothing => ""
    case data => data.extract[String]
  }

  def initializeComment(jvalue: JValue): Future[List[Comments]] = Future{
    jvalue.children.map(comment=>comment.extract[Comments])
  }


  def parseJson(url: String): List[Comments] = {
    val json:Future[String] = Future{readData(url)}
    val jsonData: String = Await.result(json, 100.seconds)
    val jvalue = parse(jsonData)
    Await.result(this.initializeComment(jvalue),100.seconds)
  }

}
