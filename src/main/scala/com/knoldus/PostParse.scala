package com.knoldus

import net.liftweb.json.JsonAST.JNothing
import net.liftweb.json.{DefaultFormats, JValue, parse}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


case class Post(userId:String,id:String,title:String,body:String)

/**
 * This Class passes Post Json
 */
class PostParse extends GetUrl {
  implicit val formats: DefaultFormats.type = DefaultFormats
  /**
   *
   * @param json Actual json Object
   * @return String of data
   */
  def extract(json: JValue): String = json match {
    case JNothing => ""
    case data => data.extract[String]
  }

  /**
   *
   * @param jvalue Got Jvalue object in modified in case class
   * @return  Future of List of Post
   */
  def initializeComment(jvalue: JValue): Future[List[Post]] = Future{
    jvalue.children.map(user=>user.extract[Post])
  }

  /**
   *
   * @param url Here we get url in String format to fetch Json data
   * @return Returns of Future of List of Post
   */
  def parseJson(url: String): Future[List[Post]] = Future{
    val json:String = readData(url)
    val jvalue = parse(json)

    initializeComment(jvalue)
    }.flatten


}
