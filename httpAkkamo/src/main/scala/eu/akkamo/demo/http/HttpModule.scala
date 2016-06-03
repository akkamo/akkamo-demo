package eu.akkamo.demo.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import eu.akkamo._


class HttpModule extends Module with Initializable {

	override def initialize(ctx: Context) = {
		ctx.inject[RouteRegistry].map(bootstrap)
	}

	override def dependencies(dependencies: Dependency): Dependency =
		dependencies.&&[ConfigModule].&&[LogModule].&&[AkkaModule].&&[AkkaModule]

	private def bootstrap(registry: RouteRegistry) = {
		val route: Route = get {
			path("todo") {
				complete("HELLO, world!")
			}
		}
		registry.register(route)
	}
}
