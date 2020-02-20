package com.knoldus

import net.liftweb.json.JsonAST.JNothing
import net.liftweb.json.{DefaultFormats, JValue, parse}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class Comments(postId:String,id:String,name:String,email:String,body:String)

class CommentParse extends GetUrl {

  implicit val formats: DefaultFormats.type = DefaultFormats

   def extract(json: JValue): String = json match {
    case JNothing => ""
    case data => data.extract[String]
  }

  def initializeComment(jvalue: JValue): Future[List[Comments]] = Future {
    jvalue.children.map(comment => comment.extract[Comments])
  }


  def parseJson(url: String): Future[List[Comments]] = Future {
    val json: String = readData(url)
    val jvalue = parse(json)
    initializeComment(jvalue)
 }.flatten

}