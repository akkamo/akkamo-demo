package com.github.jurajburian.makka.demo.pong

import akka.actor.{Actor, ActorRef}
import com.github.jurajburian.makka.demo.shared.PingPongMessage

import scalaz.Scalaz._

class SecondActor(thirdActor: ActorRef, sysName: String) extends Actor {

	override def receive: Receive = {
		case m@PingPongMessage(passed) => thirdActor ! m.copy(passed |> sign)
	}

	private def sign(passed: List[String]): List[String] =
		s"${getClass.getSimpleName}@$sysName" :: passed
}
