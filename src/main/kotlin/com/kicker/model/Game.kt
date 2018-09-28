package com.kicker.model

import com.kicker.model.base.BaseModel
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author Yauheni Efimenko
 */
@Entity
@Table(name = "games")
class Game(

        @Column(name = "losers_goals", nullable = false)
        val losersGoals: Int,

        @ManyToOne
        @JoinColumn(name = "winner1", nullable = false)
        val winner1: Player,

        @ManyToOne
        @JoinColumn(name = "winner2", nullable = false)
        val winner2: Player,

        @ManyToOne
        @JoinColumn(name = "loser1", nullable = false)
        val loser1: Player,

        @ManyToOne
        @JoinColumn(name = "loser2", nullable = false)
        val loser2: Player,

        @ManyToOne
        @JoinColumn(name = "reported_by", nullable = false)
        val reportedBy: Player,

        @Column(name = "date", nullable = false, columnDefinition = "DATE")
        val date: LocalDateTime = LocalDateTime.now()

) : BaseModel() {

    fun getWinners(): List<Player> = listOf(winner1, winner2)

    fun getLosers(): List<Player> = listOf(loser1, loser2)

}