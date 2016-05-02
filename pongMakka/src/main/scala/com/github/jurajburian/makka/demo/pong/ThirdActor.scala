package com.github.jurajburian.makka.demo.pong

import akka.actor.{Actor, ActorRef}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Publish, Put}
import com.github.jurajburian.makka.demo.shared.{Keys, PingPongMessage}

class ThirdActor(sysName: String) extends Actor {

  val mediator: ActorRef = DistributedPubSub(context.system).mediator


  override def receive: Receive = {
    case m@PingPongMessage(passed) => publish(m)
  }

  private def publish(m: PingPongMessage): Unit = {
    mediator ! Publish(Keys.Topic, m copy sign(m.passed))
  }

  private def sign(passed: List[String]): List[String] =
    s"${getClass.getSimpleName}@$sysName" :: passed
}
