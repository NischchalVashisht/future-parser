package com.knoldus

import net.liftweb.json.JsonAST.JNothing
import net.liftweb.json._
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

case class AddressDetail(street:String,suite:String,city:String,zipcode:String,geo:GeoDetail)
case class GeoDetail(lat:String,lng:String)
case class CompanyDetail(name:String,catchPhrase:String,bs:String)

case class User(id:String,name:String,username:String,email:String,address:AddressDetail,phone:String,website:String,company:CompanyDetail)


class UserParse extends GetUrl {
  implicit val formats: DefaultFormats.type = DefaultFormats

  implicit def extract(json: JValue): String = json match {
    case JNothing => ""
    case data => data.extract[String]
  }

  def initializeUser(jvalue: JValue): Future[List[User]] =Future {
      jvalue.children.map(user=>user.extract[User])
     }


  def parseJson(url: String): List[User] = {
    val json:Future[String] = Future{readData(url)}
    val jsonData: String = Await.result(json, 100.seconds)


    val jvalue = parse(jsonData)
   val resultList= this.initializeUser(jvalue)
    Await.result(resultList,100.seconds)
  }
}