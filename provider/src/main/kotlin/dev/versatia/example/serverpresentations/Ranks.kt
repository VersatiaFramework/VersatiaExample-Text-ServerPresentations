package dev.versatia.example.serverpresentations

import dev.versatia.di.LocalBean
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * The rank of every player online, decided when the player joins, before any other plugin hears
 * of the join. Kept in a concurrent map, because a presentation reads it on chat threads too.
 */
@LocalBean
class Ranks : Listener {
    private val ranks = ConcurrentHashMap<UUID, String>()

    fun of(player: Player) = ranks[player.uniqueId] ?: "Newcomer"

    @EventHandler(priority = EventPriority.LOWEST)
    fun onJoin(event: PlayerJoinEvent) {
        ranks[event.player.uniqueId] = if (event.player.hasPlayedBefore()) "Veteran" else "Newcomer"
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        ranks.remove(event.player.uniqueId)
    }
}
