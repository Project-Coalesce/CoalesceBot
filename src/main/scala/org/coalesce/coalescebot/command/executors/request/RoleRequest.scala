package org.coalesce.coalescebot.command.executors.request

import org.coalesce.coalescebot.command.{BotCommand, CommandContext}

class RoleRequest extends BotCommand {

  val availableRoles: Set[Long] = Set (388145098199924736L,
    388145098199924736L,
    388145098199924736L,
    388145098199924736L)

  override val name: String = "rolerequest"
  override val aliases: Set[String] = Set("request", "rr")
  override val desc: String = "Request a role to the server staff"

  override def execute(commandContext: CommandContext): Unit = {

  }

}
