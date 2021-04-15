import com.github.tanjag.bikeapp._
import com.github.tanjag.bikeapp.controller.BikeStationController
import com.github.tanjag.bikeapp.data.OsloBysykkelClient
import com.github.tanjag.bikeapp.rest.BikeStationApi
import com.github.tanjag.bikeapp.servlet.{BikeStationServlet, WelcomeServlet}
import org.scalatra._

import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  lazy val client = new OsloBysykkelClient()
  lazy val controller = new BikeStationController(client)

  override def init(context: ServletContext) {
    context.mount(new WelcomeServlet, "/*")
    context.mount(new BikeStationServlet(controller), "/bysykkelstativ/*")
    context.mount(new BikeStationApi(controller), "/api/*")
  }
}
