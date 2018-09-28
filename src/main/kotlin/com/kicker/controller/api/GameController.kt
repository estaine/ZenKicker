package com.kicker.controller.api

import com.kicker.domain.PageResponse
import com.kicker.domain.model.game.GameDto
import com.kicker.domain.model.game.GamePageRequest
import com.kicker.domain.model.game.GameRegistrationRequest
import com.kicker.service.GameService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @author Yauheni Efimenko
 */
@RestController
@RequestMapping("/api/games")
class GameController(
        private val service: GameService
) {

    @GetMapping("/{gameId}")
    fun get(@PathVariable gameId: Long): GameDto = GameDto(service.get(gameId))

    @GetMapping
    fun getAll(pageRequest: GamePageRequest): PageResponse<GameDto> {
        return PageResponse(service.getAll(pageRequest).map { GameDto(it) })
    }

    @PostMapping("/{playerId}")
    fun gameRegistration(@PathVariable playerId: Long, @Valid @RequestBody request: GameRegistrationRequest): GameDto {
        return GameDto(service.gameRegistration(playerId, request))
    }

}