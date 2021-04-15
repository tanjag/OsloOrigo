package com.github.tanjag.bikeapp.controller

import com.github.tanjag.bikeapp.data
import com.github.tanjag.bikeapp.data.OsloBysykkelClient
import com.github.tanjag.bikeapp.model.{BikeStationCapacityStatus, Station, StationStatus}
import org.json4s.MappingException
import org.mockito.Mockito.when
import org.scalamock.scalatest.MockFactory
import org.scalatest.PrivateMethodTester
import org.scalatest.funsuite.AnyFunSuite
import org.slf4j.LoggerFactory

import scala.io.Source
import scala.util.Success

class BikeStationControllerTest extends AnyFunSuite with MockFactory with PrivateMethodTester {
  val logger = LoggerFactory.getLogger(getClass)

  lazy val testStation1 = Station("1", "First Station", "Address no 1", 59.9130429, 10.7451127, 10)
  lazy val testStation2 = Station("2", "Second Station", "Address no 2", 59.9066044, 10.7710863, 16)
  lazy val testStationNoStatus = Station("3", "Third Station", "Address no 3", 50.9066044, 11.7710863, 7)

  lazy val testStatus1 = StationStatus("1", 1, 1, 1, 1618221258, 3, 5)
  lazy val testStatus1NoRentigBikes = StationStatus("1", 1, 0, 1, 1618221258, 3, 5)
  lazy val testStatus2 = StationStatus("2", 1, 1, 1, 1618221258, 6, 2)
  lazy val testStatus2NoReturningDocks = StationStatus("2", 1, 1, 0, 1618221258, 6, 2)

  val bysykkelClientMock = mock[OsloBysykkelClient]
  lazy val controller = new BikeStationController(bysykkelClientMock)


  test("that getCurrentStationsStatus returns Success") {

  }

  test("that station information json transformation returns list of Station") {
    val json = Source.fromURL(getClass.getResource("/station_information.json")).mkString
    val stations = controller.transformStationInformationJSONtoModel(Success(json))
    assert(stations.size === 248)
    assert(stations.find(_.station_id == "1").isEmpty)
    assert(stations.find(_.station_id == "623").nonEmpty)
  }

  test("that failed station information json transformation throws exception") {
    assertThrows[MappingException] {
      controller.transformStationStatusJSONtoModel(Success("{}"))
    }
  }

  test("that station status json transformation returns list of StationStatus") {
    val json = Source.fromURL(getClass.getResource("/station_status.json")).mkString
    val statuses = controller.transformStationStatusJSONtoModel(Success(json))
    assert(statuses.size === 248)
    assert(statuses.find(_.station_id == "1").isEmpty)
    assert(statuses.find(_.station_id == "623").nonEmpty)
  }

  test("that failed station status json transformation throws exception") {
    assertThrows[MappingException] {
      controller.transformStationStatusJSONtoModel(Success("{}"))
    }
  }

  test("that transform station status data returns valid list of BikeStationCapacityStatus") {
    val stations: List[Station] = List(testStation1, testStation2)
    val status: List[StationStatus] = List(testStatus1, testStatus2)
    val actual = controller.transformStationStatusData(stations, status)
    val url1 = controller.googleMapsLink(testStation1.lat, testStation1.lon)
    val url2 = controller.googleMapsLink(testStation2.lat, testStation2.lon)
    val expected: List[BikeStationCapacityStatus] = List(
      BikeStationCapacityStatus(testStation1.station_id, testStation1.name, testStation1.address, url1, testStatus1.num_bikes_available, testStatus1.num_docks_available),
      BikeStationCapacityStatus(testStation2.station_id, testStation2.name, testStation2.address, url2, testStatus2.num_bikes_available, testStatus2.num_docks_available))
    assert(actual.size === 2)
    assert(actual === expected)
  }

  test("that transform station status does not contain stations without station status information") {
    val expected = controller.transformStationStatusData(
      List(testStation1, testStation2, testStationNoStatus),
      List(testStatus1, testStatus2))
    assert(expected.size === 2)
    assert(expected.find(_.station_id === "3") === None)
  }

  test("that transform station status data returns list where bikes available set to 0 for non renting stations") {
    val expected = controller.transformStationStatusData(
      List(testStation1, testStation2),
      List(testStatus1NoRentigBikes, testStatus2)
    )
    assert(expected.size === 2)
    assert(expected.find(_.station_id === "1").map(_.num_bikes_available).get === 0)
  }


  test("that transform station status data returns list with 0 docks for non returning stations") {
    val expected = controller.transformStationStatusData(
      List(testStation1, testStation2),
      List(testStatus1, testStatus2NoReturningDocks)
    )
    assert(expected.size === 2)
    assert(expected.find(_.station_id === "2").map(_.num_docks_available).get === 0)
  }

  test("that we make a valid googleMapsLink with lat and lon") {
    val url = controller.googleMapsLink(testStation1.lat, testStation1.lon)
    assert(url === "https://www.google.no/maps/@59.9130429,10.7451127,19z")
  }

}
