package com.knoldus

import scala.concurrent.duration._
import net.liftweb.json.JsonAST.JNothing
import net.liftweb.json.{DefaultFormats, JValue, parse}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global


case class Post(userId:String,id:String,title:String,body:String)

class PostParse extends GetUrl {
  implicit val formats: DefaultFormats.type = DefaultFormats

  implicit def extract(json: JValue): String = json match {
    case JNothing => ""
    case data => data.extract[String]
  }

  def initializeComment(jvalue: JValue): Future[List[Post]] = Future{
    jvalue.children.map(user=>user.extract[Post])
  }


  def parseJson(url: String): List[Post] = {
    val json:Future[String] = Future{readData(url)}
    val jsonData: String = Await.result(json, 100.seconds)
    val jvalue = parse(jsonData)

    val resultlist=this.initializeComment(jvalue)
    Await.result(resultlist,100.seconds)
  }


}
