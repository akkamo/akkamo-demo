package com.github.jurajburian.makka.demo.ping

import akka.actor.{ActorRef, ActorSystem, Props}
import com.github.jurajburian.makka._
import com.github.jurajburian.makka.demo.pong.{Keys, PongMessage, PongModule}

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class PingModule extends Module with Initializable with Runnable {

	var firstActor: ActorRef = _

	@throws[InitializationError]("If initialization can't be finished")
	override def initialize(ctx: Context): Boolean = Try {
		if (ctx.initialized[AkkaModule] && ctx.initialized[LogModule] && ctx.initialized[PongModule]) {
			val system1 = ctx.inject[ActorSystem](Keys.ActorSystem1).get
			val system2 = ctx.inject[ActorSystem].get
			val secondActor = ctx.inject[ActorRef]("secondActor").get
			val log = ctx.inject[LoggingAdapterFactory].map(_ (getClass)).get

			log.info("Initializing 'Ping' Makka module")

			firstActor = system1.actorOf(Props(new FirstActor(secondActor, system1.name)))
			val fourthActor: ActorRef = system1.actorOf(Props(new FourthActor(system1.name)))
			true
		} else {
			false
		}
	} match {
		case Success(x) => x
		case Failure(th) => throw InitializationError(s"Can't initialize $toString", th)
	}

	override def run(ctx: Context): Unit = {
		implicit val system1 = ctx.inject[ActorSystem](Keys.ActorSystem1).get.dispatcher
		Future{
			Try(Thread.sleep(1000))
			firstActor ! PongMessage(List.empty[String])
		}
	}

	override def toString = getClass.getSimpleName
}
