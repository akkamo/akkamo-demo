package eu.akkamo.demo.pong

import akka.actor.{ActorRef, ActorSystem, Props}
import eu.akkamo._

import scala.util.Try


class PongModule extends Module with Initializable {

	override def initialize(ctx: Context) = Try {
		val system1 = ctx.inject[ActorSystem](Keys.ActorSystem1).get
		val system2 = ctx.inject[ActorSystem].get
		val log = ctx.inject[LoggingAdapterFactory].map(_ (getClass)).get

		log.info("Initializing 'Pong' Akkamo module")
		val thirdActor: ActorRef = system1.actorOf(Props(new ThirdActor(system1.name)))
		val secondActor: ActorRef = system2.actorOf(Props(new SecondActor(thirdActor, system2.name)))
		ctx.register(secondActor, Some("secondActor"))
	}

	override def dependencies(dependencies: Dependency): Dependency =
		dependencies.&&[AkkaModule].&&[LogModule]
}
