package com.kicker.controller.api

import com.kicker.annotation.CurrentPlayer
import com.kicker.domain.PageRequest
import com.kicker.domain.PageResponse
import com.kicker.domain.model.player.*
import com.kicker.model.Player
import com.kicker.service.PlayerService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @author Yauheni Efimenko
 */
@RestController
@RequestMapping("/api/players")
class PlayerController(
        private val service: PlayerService
) {

    @GetMapping("/current")
    fun getCurrent(@CurrentPlayer currentPlayer: Player): PlayerDto = PlayerDto(currentPlayer)

    @GetMapping("/{playerId}")
    fun get(@PathVariable playerId: Long): PlayerDto = PlayerDto(service.get(playerId))

    @GetMapping
    fun getAll(@Valid pageRequest: PageRequest): PageResponse<PlayerDto> =
            PageResponse(service.getAll(pageRequest).map { PlayerDto(it) })

    @GetMapping("/active")
    fun getAllActive(pageRequest: PlayerPageRequest): PageResponse<PlayerDto> =
            PageResponse(service.getAllActive(pageRequest).map { PlayerDto(it) })

    @PostMapping
    fun create(@Valid @RequestBody request: CreatePlayerRequest): PlayerDto = PlayerDto(service.create(request))

    @PutMapping("/{playerId}/username")
    fun updateUsername(@PathVariable playerId: Long, @Valid @RequestBody request: UpdatePlayerUsernameRequest): PlayerDto {
        return PlayerDto(service.updateUsername(playerId, request))
    }

    @PutMapping("/{playerId}/password-reset")
    fun updatePassword(@PathVariable playerId: Long, @Valid @RequestBody request: UpdatePlayerPasswordRequest): PlayerDto {
        return PlayerDto(service.updatePassword(playerId, request))
    }

}