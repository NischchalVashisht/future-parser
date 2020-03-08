package com.knoldus.model

import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

class GetUrl {
  def readData(url :String) : String = {
  val request = new HttpGet(url)
  val client = HttpClientBuilder.create().build()
  val response = client.execute(request)
  IOUtils.toString(response.getEntity.getContent)
}


}
