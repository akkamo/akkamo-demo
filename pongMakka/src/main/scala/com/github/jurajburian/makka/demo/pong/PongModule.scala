package com.github.jurajburian.makka.demo.pong

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import com.github.jurajburian.makka._

import scalaz.Scalaz._


class PongModule extends Module with Initializable {

  @throws[InitializationError]("If initialization can't be finished")
  override def initialize(ctx: Context): Boolean = {
    val system1: Option[ActorSystem] = ctx.inject[ActorSystem]
    val system2: Option[ActorSystem] = ctx.inject[ActorSystem]("system2")
    val log: Option[LoggingAdapter] = ctx.inject[LoggingAdapterFactory] map (_ apply getClass)

    (system1 |@| system2 |@| log) (bootstrap) getOrElse false
  }

  private def bootstrap(system1: ActorSystem,
                        system2: ActorSystem, log: LoggingAdapter): Boolean = {

    log.info("Initializing 'Pong' Makka module")
    // TODO add actual implementation
    true
  }

  override def toString = getClass.getSimpleName

}
