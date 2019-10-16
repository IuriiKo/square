package com.example.square.api

import com.example.square.api.services.SquareService
/**
 * The repository manages all square services
 */
interface SquareRepository {
    fun getSquareService(): SquareService
}