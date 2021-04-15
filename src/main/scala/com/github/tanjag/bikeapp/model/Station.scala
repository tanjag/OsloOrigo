package com.github.tanjag.bikeapp.model

case class Station(station_id: String,
                   name: String,
                   address: String,
                   lat: Double,
                   lon: Double,
                   capacity: Integer)

case class AllStations(last_updated: Integer,
                       ttl: Integer,
                       data: Map[String, List[Station]]) {

  def getListOfStations(): List[Station] = {
    data.map(_._2).toList.flatten
  }
}

case class StationStatus(station_id: String,
                         is_installed: Integer,
                         is_renting: Integer,
                         is_returning: Integer,
                         last_reported: Integer,
                         num_bikes_available: Integer,
                         num_docks_available: Integer)

case class RetriveStationStatus(last_updated: Integer,
                                ttl: Integer,
                                data: Map[String, List[StationStatus]]) {

  def getListOfStationStatus(): List[StationStatus] = {
    data.map(_._2).toList.flatten
  }
}

case class BikeStationCapacityStatus(station_id: String,
                                     name: String,
                                     address: String,
                                     googleMapUrl: String,
                                     num_bikes_available: Integer,
                                     num_docks_available: Integer
                                    )






