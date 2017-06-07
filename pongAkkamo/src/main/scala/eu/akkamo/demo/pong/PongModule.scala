package eu.akkamo.demo.pong

import akka.actor.{ActorRef, ActorSystem, Props}
import eu.akkamo._

import scala.util.Try


class PongModule extends Module with Initializable {

	override def initialize(ctx: Context) = Try {
		val system1 = ctx.get[ActorSystem](Keys.ActorSystem1)
		val system2 = ctx.get[ActorSystem]
		val log = ctx.get[LoggingAdapterFactory].apply(getClass)

		log.info("Initializing 'Pong' Akkamo module")
		val thirdActor: ActorRef = system1.actorOf(Props(new ThirdActor(system1.name)))
		val secondActor: ActorRef = system2.actorOf(Props(new SecondActor(thirdActor, system2.name)))
		ctx.register(secondActor, Some("secondActor"))
	}

	override def dependencies(dependencies: TypeInfoChain): TypeInfoChain =
		dependencies.&&[AkkaModule].&&[LogModule]
}
