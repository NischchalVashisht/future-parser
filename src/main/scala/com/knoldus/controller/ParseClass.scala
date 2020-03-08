package com.knoldus.controller

import com.knoldus.model.{Comments, GetUrl, Post, User}
import net.liftweb.json.JsonAST.JNothing
import net.liftweb.json.{DefaultFormats, JValue, parse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class ParseClass(getUrl: GetUrl) {


  implicit val formats: DefaultFormats.type = DefaultFormats

   def extract(json: JValue): String = json match {
    case JNothing => ""
    case data => data.extract[String]
  }


  def parseData[T](url: String )(implicit m : Manifest[T]): Future[List[T]] = Future{
    val json = getUrl.readData(url)
    val parseData=parse(json)
    parseData.children.map(user=>user.extract[T])
  }

}