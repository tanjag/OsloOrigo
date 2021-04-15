package com.github.tanjag.bikeapp.servlet

import com.github.tanjag.bikeapp.controller.BikeStationController
import com.github.tanjag.bikeapp.model.BikeStationCapacityStatus
import org.scalatra._
import org.slf4j.LoggerFactory

class BikeStationServlet(controller: BikeStationController) extends ScalatraServlet {
  val logger = LoggerFactory.getLogger(getClass)

  get("/") {
    redirect("/bysykkelstativ/status")
  }

  get("/status/?") {
    logger.debug("hit on GET status")
    val status: List[BikeStationCapacityStatus] = controller.getCurrentStationsStatus()
    views.html.bikeStatus(status)
  }

}
