package com.github.tanjag.bikeapp.controller

import com.github.tanjag.bikeapp.data.OsloBysykkelClient
import com.github.tanjag.bikeapp.model.{AllStations, BikeStationCapacityStatus, RetriveStationStatus, Station, StationStatus}
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.read
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success, Try}

class BikeStationController(bysykkelClient: OsloBysykkelClient) {
  implicit val formats = DefaultFormats

  val logger = LoggerFactory.getLogger(getClass)

  def getCurrentStationsStatus(): List[BikeStationCapacityStatus] = {
    callBikeApis() match {
      case Success(data: (List[Station], List[StationStatus])) => {
        transformStationStatusData(data._1, data._2).sortBy(_.name)
      }
      case Failure(exception) => {
        logger.error(s"Failed to get data from external api, failed with message: [${exception.getMessage}]")
        Nil
      }
    }
  }

  def callBikeApis(): Try[(List[Station], List[StationStatus])] = {
    Try {
      val stations: List[Station] = transformStationInformationJSONtoModel(bysykkelClient.getStationInformation())
      val status: List[StationStatus] = transformStationStatusJSONtoModel(bysykkelClient.getStationStatus())
      (stations, status)
    }
  }

  def transformStationStatusJSONtoModel(json: Try[String]): List[StationStatus] = {
    json match {
      case Success(stationStatus) => {
        val allStatus = read[RetriveStationStatus](stationStatus)
        allStatus.getListOfStationStatus()
      }
      case Failure(exception) => {
        logger.error(s"Failed to get status for stations ${exception.getMessage}")
        throw exception
      }
    }
  }

  def transformStationInformationJSONtoModel(json: Try[String]): List[Station] = {
    json match {
      case Success(stationInfo) => {
        val allStations: AllStations = read[AllStations](stationInfo)
        allStations.getListOfStations()
      }
      case Failure(exception) => {
        logger.error(s"Failed to get all stations ${exception.getMessage}")
        throw exception
      }
    }
  }

  //Combine list of stations with current status of stations that are renting out bikes and returning bikes
  //If the station is not renting/returning bikes the availablity is set to 0
  def transformStationStatusData(stations: List[Station], status: List[StationStatus]): List[BikeStationCapacityStatus] = {
    stations.map(station => {
      //Find the status for the station, do not add the station if status is not found
      val maybeStatus = status.find(status => station.station_id == status.station_id)
      maybeStatus.map(s => {
        BikeStationCapacityStatus(
          station.station_id,
          station.name,
          station.address,
          googleMapsLink(station.lat, station.lon),
          if (s.is_renting == 1) s.num_bikes_available else 0,
          if (s.is_returning == 1) s.num_docks_available else 0,
        )
      })
    }).flatten
  }


  def googleMapsLink(lat: Double, lon: Double): String = {
    //Will go to the location but not make a pointer on the map, for pointer one needs to use googles map API
    s"https://www.google.no/maps/@$lat,$lon,19z"
  }

}
