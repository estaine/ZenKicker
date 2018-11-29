package com.kicker.api.service

import com.kicker.api.domain.PageRequest
import com.kicker.api.domain.model.playerStats.PlayerStatsPageRequest
import com.kicker.api.domain.model.playerStats.PlayersDashboard
import com.kicker.api.model.PlayerStats
import com.kicker.api.repository.PlayerStatsRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class DefaultPlayerStatsService(
        private val playerService: PlayerService,
        private val repository: PlayerStatsRepository
) : DefaultBaseService<PlayerStats, PlayerStatsRepository>(repository), PlayerStatsService {

    override fun getByPlayer(playerId: Long): PlayerStats {
        val player = playerService.get(playerId)
        return repository.findByPlayer(player)
    }

    @Cacheable("playersDashboard")
    override fun getDashboard(): PlayersDashboard {
        val pageRequest = PlayerStatsPageRequest().apply { limit = 0; sortBy = "rating" }
        if (getAllActive(pageRequest).totalElements < 4) {
            return PlayersDashboard()
        }

        val top3 = repository.findAllByActiveTrue(pageRequest.apply {
            limit = 3;
            sortBy = "rating";
            sortDirection = DESC
        })
        val loser = repository.findFirstByActiveTrueOrderByRatingAscIdDesc()

        return PlayersDashboard(
                top3.content[0].player,
                top3.content[1].player,
                top3.content[2].player,
                loser.player
        )
    }

    @Cacheable("statsPlayers")
    override fun getAll(pageRequest: PageRequest): Page<PlayerStats> = super.getAll(pageRequest)

    @Cacheable("statsActivePlayers")
    override fun getAllActive(pageRequest: PageRequest): Page<PlayerStats> = repository.findAllByActiveTrue(pageRequest)

    @CacheEvict("statsPlayers", "statsActivePlayers", allEntries = true)
    @Transactional
    override fun updateActivity(playerId: Long, active: Boolean): PlayerStats {
        val playerStats = getByPlayer(playerId)
        playerStats.active = active

        return super.save(playerStats)
    }

}