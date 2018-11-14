package com.kicker.domain.model.playerRelations

import com.kicker.domain.PageRequest
import org.springframework.data.domain.Sort.Direction.DESC

/**
 * @author Yauheni Efimenko
 */
class PlayerRelationsPageRequest : PageRequest(
        sortBy = "winningPercentage",
        sortDirection = DESC,
        maySortBy = mapOf("id" to "id", "winningPercentage" to "winningPercentage")
)