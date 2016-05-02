package com.github.jurajburian.makka.demo.pong

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.event.LoggingAdapter
import com.github.jurajburian.makka._

import scalaz.Scalaz._


class PongModule extends Module with Initializable {

  @throws[InitializationError]("If initialization can't be finished")
  override def initialize(ctx: Context): Boolean = {
    val system1: Option[ActorSystem] = ctx.inject[ActorSystem]("actorSystem1")
    val system2: Option[ActorSystem] = ctx.inject[ActorSystem]
    val log: Option[LoggingAdapter] = ctx.inject[LoggingAdapterFactory] map (_ apply getClass)

    (Option(ctx) |@| system1 |@| system2 |@| log) (bootstrap) getOrElse false
  }

  private def bootstrap(ctx: Context, sys1: ActorSystem,
                        sys2: ActorSystem, log: LoggingAdapter): Boolean = {

    log.info("Initializing 'Pong' Makka module")
    val thirdActor: ActorRef = sys1.actorOf(Props(new ThirdActor(sys1.name)))
    val secondActor: ActorRef = sys2.actorOf(Props(new SecondActor(thirdActor, sys2.name)))
    ctx.register(secondActor, Some("secondActor"))
    true
  }

  override def toString = getClass.getSimpleName

}
