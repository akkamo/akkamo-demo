package com.github.jurajburian.makka.demo.ping

import akka.actor.{Actor, ActorRef}
import com.github.jurajburian.makka.demo.shared.PingPongMessage

class FirstActor(val secondActor: ActorRef, sysName: String) extends Actor {

  override def receive: Receive = {
    case m@PingPongMessage(passed) => secondActor ! m.copy(sign(passed))
  }

  private def sign(passed: List[String]): List[String] =
    s"${getClass.getSimpleName}@$sysName" :: passed
}
