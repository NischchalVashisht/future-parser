package com.knoldus

import net.liftweb.json.JsonAST.JNothing
import net.liftweb.json._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class AddressDetail(street:String,suite:String,city:String,zipcode:String,geo:GeoDetail)
case class GeoDetail(lat:String,lng:String)
case class CompanyDetail(name:String,catchPhrase:String,bs:String)

case class User(id:String,name:String,username:String,email:String,address:AddressDetail,phone:String,website:String,company:CompanyDetail)

/**
 * This Class passes User Json
 */
class UserParse extends GetUrl {
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
   * @return Future of List of User
   */
  def initializeUser(jvalue: JValue): Future[List[User]] =Future {
      jvalue.children.map(user=>user.extract[User])
     }

  /**
   *
   * @param url Here we get url in String format to fetch Json data
   * @return Returns of Future of List of User
   */
  def parseJson(url: String): Future[List[User]] = Future{
    val json:String = readData(url)
    val jvalue = parse(json)
   initializeUser(jvalue)

  }.flatten
}