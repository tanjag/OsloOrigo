package com.github.tanjag.bikeapp.model

import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.read
import org.scalatest.funsuite.AnyFunSuite
import org.slf4j.LoggerFactory

import scala.io.Source

class StationTest extends AnyFunSuite {
  val logger =  LoggerFactory.getLogger(getClass)
  implicit val formats = DefaultFormats

  test("that the list of stations is from the data map"){
    val stationInfo = Source.fromURL(getClass.getResource("/station_information.json")).mkString
    val allStations: AllStations = read[AllStations](stationInfo)
    val stations: List[Station] = allStations.getListOfStations()
    assert(allStations.data.size === 1)
    assert(stations.size === 248)
  }

  test("that the list of statuses is from the data map"){
    val stationStatus = Source.fromURL(getClass.getResource("/station_status.json")).mkString
    val allStatus = read[RetriveStationStatus](stationStatus)
    val statuses: List[StationStatus] = allStatus.getListOfStationStatus()
    assert(allStatus.data.size === 1)
    assert(statuses.size === 248)
  }



}
