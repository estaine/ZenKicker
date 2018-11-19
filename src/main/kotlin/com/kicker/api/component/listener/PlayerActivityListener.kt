package com.kicker.api.component.listener

import com.kicker.api.config.property.PlayerSettingsProperties
import com.kicker.api.model.Game
import com.kicker.api.model.Player
import com.kicker.api.service.GameService
import com.kicker.api.service.PlayerService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * @author Yauheni Efimenko
 */

@Component
class PlayerActivityListener(
        private val gameService: GameService,
        private val playerService: PlayerService,
        private val playerSettingsProperties: PlayerSettingsProperties
) {

    @EventListener
    @Transactional
    fun handleRegistrationGame(game: Game) {
        handlePlayerActivity(game.winner1)
        handlePlayerActivity(game.winner2)
        handlePlayerActivity(game.loser1)
        handlePlayerActivity(game.loser2)
    }

    private fun handlePlayerActivity(player: Player) {
        val currentCountGames = gameService.countDuring10WeeksByPlayer(player.id)
        if (!player.active && currentCountGames >= playerSettingsProperties.countGames!!
                || player.active && currentCountGames < playerSettingsProperties.countGames!!) {
            playerService.updateActivity(player.id, !player.active)
        }
    }

}
