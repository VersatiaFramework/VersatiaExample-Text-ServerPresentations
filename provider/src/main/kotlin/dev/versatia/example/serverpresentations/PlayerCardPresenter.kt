package dev.versatia.example.serverpresentations

import dev.versatia.messages.Messages
import dev.versatia.text.Presenter
import dev.versatia.text.ServerPresentation
import dev.versatia.text.arg
import dev.versatia.text.presentation
import org.bukkit.entity.Player

/**
 * How a player appears in the messages of every plugin on the server: the display name with a
 * card on hover, and `{player.rank}`. Everything else, `{player.name}`, `{player.ping}` and the
 * `player` style the server gives the name, is left to the built-in presentation.
 */
@ServerPresentation
class PlayerCardPresenter(
    private val ranks: Ranks,
    private val messages: Messages,
) : Presenter<Player> {
    override fun present(value: Player) = presentation {
        component {
            val card = messages["card"].get(arg("name", value.name), arg("rank", ranks.of(value)))
            value.displayName().hoverEvent(card)
        }
        property("rank") { ranks.of(value) }
    }
}
