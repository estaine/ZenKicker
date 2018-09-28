package com.kicker.service

import com.kicker.domain.PageRequest
import com.kicker.domain.model.game.GameRegistrationRequest
import com.kicker.model.Game
import com.kicker.repository.GameRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Yauheni Efimenko
 */
@Service
@Transactional(readOnly = true)
class DefaultGameService(
        private val repository: GameRepository,
        private val playerService: PlayerService
) : DefaultBaseService<Game, GameRepository>(repository), GameService {

    override fun getAllBelongGames(playerId: Long, pageRequest: PageRequest): Page<Game> {
        val player = playerService.get(playerId)
        return repository.findAllBelongGames(player, pageRequest)
    }

    @Transactional
    override fun gameRegistration(playerId: Long, request: GameRegistrationRequest): Game {
        val reporter = playerService.get(playerId)

        val winner1 = playerService.get(request.winner1Id!!)
        val winner2 = playerService.get(request.winner2Id!!)
        val loser1 = playerService.get(request.loser1Id!!)
        val loser2 = playerService.get(request.loser2Id!!)

        val game = Game(request.losersGoals!!, winner1, winner2, loser1, loser2, reporter)

//        updatePlayersRating(game)

        return repository.save(game)
    }

//    private fun updatePlayersRating(game: Game) {
//        val loserPlayer1 = if (game.redTeamGoals > game.yellowTeamGoals) game.yellowPlayer1 else game.redPlayer1
//        val loserPlayer2 = if (loserPlayer1 == game.yellowPlayer1) game.yellowPlayer2 else game.redPlayer2
//        val winnerPlayer1 = if (loserPlayer1 == game.yellowPlayer1) game.redPlayer1 else game.yellowPlayer1
//        val winnerPlayer2 = if (loserPlayer1 == game.yellowPlayer1) game.redPlayer2 else game.yellowPlayer2
//
//        val loserGoals: Int = if (loserPlayer1 == game.yellowPlayer1) game.yellowTeamGoals else game.redTeamGoals
//        val losersTotalRating: Double = loserPlayer1.currentRating + loserPlayer2.currentRating
//        val winnersTotalRating: Double = winnerPlayer1.currentRating + winnerPlayer2.currentRating
//
//        val skillCorrection: Double = RatingUtils.getSkillCorrection(losersTotalRating, winnersTotalRating)
//        val losingPercents: Double = RatingUtils.getLosingPercents(loserGoals, skillCorrection)
//
//        val loser1Delta = loserPlayer1.currentRating * losingPercents / 100.0
//        val loser2Delta = loserPlayer2.currentRating * losingPercents / 100.0
//        val winnerDelta = (loser1Delta + loser2Delta) / 2.0
//
//        playerService.updateRating(loserPlayer1.id, (loserPlayer1.currentRating - loser1Delta))
//        playerService.updateRating(loserPlayer2.id, (loserPlayer2.currentRating - loser2Delta))
//        playerService.updateRating(winnerPlayer1.id, (winnerPlayer1.currentRating + winnerDelta))
//        playerService.updateRating(winnerPlayer2.id, (winnerPlayer2.currentRating + winnerDelta))
//    }

}