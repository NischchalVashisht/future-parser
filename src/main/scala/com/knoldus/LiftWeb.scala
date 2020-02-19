package com.knoldus
import net.liftweb.json._
import net.liftweb.json.JsonDSL._


case class Person(name: String, address: Address) {
  var friends = List[Person]()
}
case class Address(city: String, state: String)
object LiftJsonListsVersion1 extends App {
  //import net.liftweb.json.JsonParser._
  implicit val formats = DefaultFormats
  val merc = Person("Mercedes", Address("Somewhere", "KY"))
  val mel = Person("Mel", Address("Lake Zurich", "IL"))
  val friends = List(merc, mel)
  val p = Person("Alvin Alexander", Address("Talkeetna", "AK"))
  p.friends = friends
  val json =
    ("person" ->
      ("name" -> p.name) ~
        ("address" ->
          ("city" -> p.address.city) ~
            ("state" -> p.address.state)) ~
        ("friends" ->
          friends.map { f =>
            ("name" -> f.name) ~
              ("address" ->
                ("city" -> f.address.city) ~
                  ("state" -> f.address.state))
          }))

  println(prettyRender(json))

}
