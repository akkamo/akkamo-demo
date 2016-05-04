package com.github.jurajburian.makka.demo.ping

import akka.actor.{Actor, ActorRef}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import com.github.jurajburian.makka.demo.pong.{Keys, PongMessage}

class FourthActor(sysName: String) extends Actor {

	val mediator: ActorRef = DistributedPubSub(context.system).mediator

	mediator ! Subscribe(Keys.Topic, self)

	override def receive: Receive = {
		case m@PongMessage(passed) => println("RECEIVED: " + (format(sign(passed))))
	}

	private def format(passed: List[String]) = passed.reverse.mkString(" -> ")

	private def sign(passed: List[String]): List[String] = s"${getClass.getSimpleName}@$sysName" :: passed
}
