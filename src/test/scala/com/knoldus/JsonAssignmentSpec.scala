package com.knoldus

import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec

class JsonAssignmentSpec extends AsyncFlatSpec with BeforeAndAfterAll
{


  override def beforeAll(): Unit = {
}
 override  def afterAll():Unit= {
 }

  "find Answers method " should " find User with Maximum number of posts " in {
  val actualResult = JsonAssignment.maxPost
  actualResult map { value => assert(value == "Leanne Graham") }
}


  "it " should " find User whose Post has maximum number of comments " in {
  val actualResult = JsonAssignment.maxPostComment
  actualResult map { value => assert(value == "Leanne Graham") }
}

}
