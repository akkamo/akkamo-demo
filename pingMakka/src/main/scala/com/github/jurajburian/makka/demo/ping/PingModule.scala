package com.github.jurajburian.makka.demo.ping

import com.github.jurajburian.makka._


class PingModule extends Module with Initializable {

  @throws[InitializationError]("If initialization can't be finished")
  override def initialize(ctx: Context): Boolean = {
    // TODO add actual implementation
    true
  }

  override def toString = getClass.getSimpleName
}
