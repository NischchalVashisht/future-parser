package com.knoldus.controller

import com.knoldus.model.{Comments, GetUrl, Post, User}
import net.liftweb.json.JsonAST.JNothing
import net.liftweb.json.{DefaultFormats, JValue, parse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class ParseClass extends GetUrl {

  implicit val formats: DefaultFormats.type = DefaultFormats

   def extract(json: JValue): String = json match {
    case JNothing => ""
    case data => data.extract[String]
  }


  def parseData[T](jvalue: Future[JValue] )(implicit m : Manifest[T]): Future[List[T]] = Future{
    jvalue.map(x=>x.children.map(user=>user.extract[T]))
  }.flatten



  def parseJson(url: String):Future[JValue]= Future {
     val json: String = readData(url)
    parse(json)

 }

}