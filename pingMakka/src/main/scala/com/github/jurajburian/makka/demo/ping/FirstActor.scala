package com.github.jurajburian.makka.demo.ping

import akka.actor.{Actor, ActorRef}
import com.github.jurajburian.makka.demo.pong.PongMessage

class FirstActor(val secondActor: ActorRef, sysName: String) extends Actor {

	override def receive: Receive = {
		case m@PongMessage(passed) => secondActor ! m.copy(sign(passed))
	}

	private def sign(passed: List[String]): List[String] = s"${getClass.getSimpleName}@$sysName" :: passed
}
