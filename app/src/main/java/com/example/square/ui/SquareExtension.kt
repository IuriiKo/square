package com.example.square.ui

import com.example.square.api.models.SquareApiModel

fun SquareApiModel.convert(): SquareModel =
    SquareModel(
        id = id,
        name = name,
        description = description
    )