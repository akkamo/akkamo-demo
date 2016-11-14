package eu.akkamo.demo.ping

import akka.actor.{Actor, ActorRef}
import eu.akkamo.demo.pong.PongMessage

class FirstActor(val secondActor: ActorRef, sysName: String) extends Actor {

  override def receive: Receive = {
    case m@PongMessage(passed) => secondActor ! m.copy(sign(passed))
  }

  private def sign(passed: List[String]): List[String] =
    s"${getClass.getSimpleName}@$sysName" :: passed
}
