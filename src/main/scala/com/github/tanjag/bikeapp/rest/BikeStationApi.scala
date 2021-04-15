package com.github.tanjag.bikeapp.rest

import com.github.tanjag.bikeapp.controller.BikeStationController
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.slf4j.LoggerFactory
// JSON handling support from Scalatra
import org.scalatra.json._

class BikeStationApi(controller: BikeStationController) extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  val logger = LoggerFactory.getLogger(getClass)
  before() {
    contentType = formats("json")
  }
  get("/status/all") {

    logger.info("getting status for all stations")
    val statuses = controller.getCurrentStationsStatus()
    statuses
  }

  get("/status/station_id/:id") {

    val id = params("id")
    logger.info(s"GET station id:$id")

    controller.getCurrentStationsStatus().find(_.station_id == id) match {
      case Some(station) => {
        logger.info(s"found station $station")
        station
      }
      case None => {
        logger.info("NOT found set status to 404 and return empty json")
        status = 404
        "{}"
      }
    }

  }

  get("/status/name/:name") {
    val name = params("name")
    controller.getCurrentStationsStatus().find(_.name.toLowerCase == name.toLowerCase()) match {
      case Some(station) => {
        logger.info(s"found station $station")
        station
      }
      case None => {
        logger.info("NOT found set status to 404 and return empty json")
        status = 404
        "{}"
      }
    }
  }

  get("/status/havebikes") {
    val statuses = controller.getCurrentStationsStatus().filter(_.num_bikes_available > 0)
    logger.info(s"No of stations with bikes ${statuses.size}")
    statuses
  }

  get("/status/havedocks") {
    val statuses = controller.getCurrentStationsStatus().filter(_.num_docks_available > 0)
    logger.info(s"No of stations with docks ${statuses.size}")
    statuses
  }

}
