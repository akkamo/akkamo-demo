package com.github.jurajburian.makka.demo.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.jurajburian.makka.{AkkaModule, LogModule, _}

class HttpModule extends Module with Initializable {

  @scala.throws[InitializationError]("If initialization can't be finished")
  override def initialize(ctx: Context): Boolean = {
    if (ctx.initialized[ConfigModule] && ctx.initialized[LogModule]
			&& ctx.initialized[AkkaModule] && ctx.initialized[AkkaHttpModule]) {

      ctx.inject[RouteRegistry] exists bootstrap
    } else {
      false
    }
  }

  private def bootstrap(registry: RouteRegistry): Boolean = {
    val route: Route = get {
      path("todo") {
        complete("HELLO, world!")
      }
    }

    registry.register(route)
    true
  }

  override def toString = getClass.getSimpleName
}
