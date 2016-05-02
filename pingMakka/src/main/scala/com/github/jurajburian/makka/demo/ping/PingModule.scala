package com.github.jurajburian.makka.demo.ping

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.event.LoggingAdapter
import com.github.jurajburian.makka._
import com.github.jurajburian.makka.demo.shared.{Keys, PingPongMessage}

import scalaz.Scalaz._

class PingModule extends Module with Initializable with Runnable {

	var firstActor: ActorRef = _

	@throws[InitializationError]("If initialization can't be finished")
	override def initialize(ctx: Context): Boolean = {
		val system1: Option[ActorSystem] = ctx.inject[ActorSystem](Keys.ActorSystem1)
		val system2: Option[ActorSystem] = ctx.inject[ActorSystem]
		val secondActor: Option[ActorRef] = ctx.inject[ActorRef]("secondActor")
		val log: Option[LoggingAdapter] = ctx.inject[LoggingAdapterFactory] map (_ apply getClass)

		(system1 |@| system2 |@| secondActor |@| log) (bootstrap) getOrElse false
	}

	private def bootstrap(sys1: ActorSystem, sys2: ActorSystem,
												secondActor: ActorRef, log: LoggingAdapter): Boolean = {

		log.info("Initializing 'Ping' Makka module")
		firstActor = sys1.actorOf(Props(new FirstActor(secondActor, sys1.name)))
		val fourthActor: ActorRef = sys1.actorOf(Props(new FourthActor(sys1.name)))

		true
	}

	override def run(ctx: Context): Unit = {
		firstActor ! PingPongMessage(List.empty[String])
	}

	override def toString = getClass.getSimpleName
}
