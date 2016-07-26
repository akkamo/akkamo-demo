package eu.akkamo.demo.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import eu.akkamo._

import scala.util.Try


class HttpModule extends Module with Initializable {

  override def initialize(ctx: Context) = Try {
    val route: Route = get {
      path("todo") {
        complete("HELLO, world!")
      }
    }
    ctx.registerIn[RouteRegistry, Route](route)
  }

  override def dependencies(dependencies: Dependency): Dependency =
    dependencies.&&[AkkaHttpModule]

}
