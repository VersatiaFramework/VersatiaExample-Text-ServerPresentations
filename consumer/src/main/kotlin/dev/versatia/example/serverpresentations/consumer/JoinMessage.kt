package dev.versatia.example.serverpresentations.consumer

import dev.versatia.messages.Messages
import dev.versatia.text.arg
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/** Knows nothing of the provider: `{player}` and `{player.rank}` come from the presentation the server has for players. */
class JoinMessage(private val messages: Messages) : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) = event.joinMessage(messages["joined"].get(arg("player", event.player)))
}
