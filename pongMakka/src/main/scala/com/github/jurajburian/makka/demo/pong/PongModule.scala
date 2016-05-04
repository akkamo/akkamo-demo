package com.github.jurajburian.makka.demo.pong

import akka.actor.{ActorRef, ActorSystem, Props}
import com.github.jurajburian.makka._

import scala.util.{Failure, Success, Try}


class PongModule extends Module with Initializable {

	@throws[InitializationError]("If initialization can't be finished")
	override def initialize(ctx: Context): Boolean = Try {
		if (ctx.initialized[AkkaModule] && ctx.initialized[LogModule]) {
			val system1 = ctx.inject[ActorSystem](Keys.ActorSystem1).get
			val system2 = ctx.inject[ActorSystem].get
			val log = ctx.inject[LoggingAdapterFactory].map(_ (getClass)).get

			log.info("Initializing 'Pong' Makka module")
			val thirdActor: ActorRef = system1.actorOf(Props(new ThirdActor(system1.name)))
			val secondActor: ActorRef = system2.actorOf(Props(new SecondActor(thirdActor, system2.name)))
			ctx.register(secondActor, Some("secondActor"))
			true
		} else {
			false
		}
	} match {
		case Success(x) => x
		case Failure(th) => throw InitializationError(s"Can't initialize $toString", th)
	}

	override def toString = getClass.getSimpleName

}
