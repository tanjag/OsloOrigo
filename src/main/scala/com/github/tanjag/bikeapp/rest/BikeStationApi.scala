package com.github.tanjag.bikeapp.rest

import com.github.tanjag.bikeapp.controller.BikeStationController
import com.github.tanjag.bikeapp.model.{Error, RestErrors}
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

  error {
    case e: Throwable => {
      status = 503
      RestErrors(List(Error(503, s"${e.getMessage}")))
    }
  }


  get("/status/all/?") {
    val statuses = controller.getCurrentStationsStatus()
    statuses
  }

  get("/status/station_id/:id/?") {
    val id = params("id")
    controller.getCurrentStationsStatus().find(_.station_id == id) match {
      case Some(station) => {
        logger.info(s"found station $station")
        station
      }
      case None => {
        logger.info("NOT found set status to 404 and return empty json")
        status = 404
        RestErrors(List(Error(404, s"Status for station id:[$id] Not Found")))
      }
    }

  }

  get("/status/name/:name/?") {
    val name = params("name")
    controller.getCurrentStationsStatus().find(_.name.toLowerCase == name.toLowerCase()) match {
      case Some(station) => {
        station
      }
      case None => {
        status = 404
        RestErrors(List(Error(404, s"Status for station name:[$name] Not Found")))
      }
    }
  }

  get("/status/bikes_available/?") {
    val statuses = controller.getCurrentStationsStatus().filter(_.num_bikes_available > 0)
    statuses
  }

  get("/status/docks_available/?") {
    val statuses = controller.getCurrentStationsStatus().filter(_.num_docks_available > 0)
    statuses
  }

}
