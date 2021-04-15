package com.github.tanjag.bikeapp.data

import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.apache.http.util.EntityUtils
import org.slf4j.LoggerFactory

import scala.io.Source
import scala.util.Try

//Requests data from https://oslobysykkel.no/apne-data/sanntid
class OsloBysykkelClient {
  val logger = LoggerFactory.getLogger(getClass)
  lazy val system_status: String = "https://gbfs.urbansharing.com/oslobysykkel.no/station_status.json"
  lazy val station_information: String = "https://gbfs.urbansharing.com/oslobysykkel.no/station_information.json"

  //Header parameters
  lazy val client_identifier = "Client-Identifier"
  lazy val api_caller = "tanjas-stasjonsoversikt"


  def getStationInformation(): Try[String] = {
    callUrbansharingApi(station_information)
  }

  def getStationStatus(): Try[String] = {
    callUrbansharingApi(system_status)
  }

  def callUrbansharingApi(url: String): Try[String] = {
    Try {
      val httpclient: CloseableHttpClient = HttpClients.createDefault()
      val httpGet = new HttpGet(url)
      httpGet.addHeader(client_identifier, api_caller)
      val response: CloseableHttpResponse = httpclient.execute(httpGet)

      val entity = response.getEntity
      val content = entity.getContent

      val jsonResponse = Source.fromInputStream(content).mkString
      EntityUtils.consume(entity)
      jsonResponse
    }
  }


}
