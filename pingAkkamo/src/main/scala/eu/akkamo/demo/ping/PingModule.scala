package eu.akkamo.demo.ping

import akka.actor.{ActorRef, ActorSystem, Props}
import eu.akkamo._
import eu.akkamo.demo.pong.{Keys, PongMessage, PongModule}

import scala.concurrent.Future
import scala.util.Try

class PingModule extends Module with Initializable with Runnable {

  var firstActor: ActorRef = _

  override def initialize(ctx: Context) = Try {
    val system1 = ctx.inject[ActorSystem](Keys.ActorSystem1).get
    val system2 = ctx.inject[ActorSystem].get
    val secondActor = ctx.inject[ActorRef]("secondActor").get
    val log = ctx.inject[LoggingAdapterFactory].map(_ (getClass)).get

    log.info("Initializing 'Ping' Akkamo module")

    firstActor = system1.actorOf(Props(new FirstActor(secondActor, system1.name)))
    val fourthActor: ActorRef = system1.actorOf(Props(new FourthActor(system1.name)))
    ctx
  }

  override def dependencies(dependencies: Dependency): Dependency =
    dependencies.&&[AkkaModule].&&[LogModule].&&[PongModule]

  override def run(ctx: Context) = Try {
    implicit val system1 = ctx.inject[ActorSystem](Keys.ActorSystem1).get.dispatcher
    Future {
      Try(Thread.sleep(5000))
      firstActor ! PongMessage(List.empty[String])
    }
    ctx
  }
}
