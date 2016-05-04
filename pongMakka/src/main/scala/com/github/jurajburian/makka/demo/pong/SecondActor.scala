package com.github.jurajburian.makka.demo.pong

import akka.actor.{Actor, ActorRef}

class SecondActor(thirdActor: ActorRef, sysName: String) extends Actor {

	override def receive: Receive = {
		case m@PongMessage(passed) => thirdActor ! m.copy(sign(passed))
	}

	private def sign(passed: List[String]): List[String] =
		s"${getClass.getSimpleName}@$sysName" :: passed
}
