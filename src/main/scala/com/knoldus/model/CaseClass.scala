package com.knoldus.model

case class Comments(postId:String,id:String,name:String,email:String,body:String)
case class AddressDetail(street:String,suite:String,city:String,zipcode:String,geo:GeoDetail)
case class GeoDetail(lat:String,lng:String)
case class CompanyDetail(name:String,catchPhrase:String,bs:String)

case class User(id:String,name:String,username:String,email:String,address:AddressDetail,phone:String,website:String,company:CompanyDetail)

case class Post(userId:String,id:String,title:String,body:String)

case class UserAndPost(id: User, post: List[Post])

case class PostAndComment(id: Post, comment: List[Comments])
