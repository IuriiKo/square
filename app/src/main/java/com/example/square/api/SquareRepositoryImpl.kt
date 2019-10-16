package com.example.square.api

import com.example.square.api.services.SquareService
import javax.inject.Inject

class SquareRepositoryImpl @Inject constructor(
    private val squareService: SquareService
) : SquareRepository {
    override fun getSquareService(): SquareService = squareService
}