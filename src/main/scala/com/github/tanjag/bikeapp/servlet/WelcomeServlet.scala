package com.github.tanjag.bikeapp.servlet

import org.scalatra.ScalatraServlet

class WelcomeServlet extends ScalatraServlet {

  get("/?"){
    views.html.welcome()
  }

}
