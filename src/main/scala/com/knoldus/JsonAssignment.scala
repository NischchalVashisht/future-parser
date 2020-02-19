package com.knoldus

object JsonAssignment extends  App{
  val cp=new CommentParse
  val up=new UserParse
  val pp=new PostParse
  val op=new OperationMethod
  val cmntList= cp.parseJson("https://jsonplaceholder.typicode.com/comments")
 val usrList= up.parseJson("https://jsonplaceholder.typicode.com/users")
 val pstList= pp.parseJson("https://jsonplaceholder.typicode.com/posts")

 val returnOfUserPost=op.getUsersPost(usrList,pstList)
  val returnOfCommentPost=op.getPostWithComment(cmntList,pstList)
  val maxPost=op.postWithMaxComment(returnOfCommentPost,usrList)
  println(maxPost)

}
