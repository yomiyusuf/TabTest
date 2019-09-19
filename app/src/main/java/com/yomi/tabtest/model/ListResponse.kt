package com.yomi.tabtest.model

data class ListResponse(
    val cod: String,
    val cnt: Int,
    val list: List<City>
)

data class City(
    val id: Int,
    val name: String,
    val clouds: Clouds
)

data class Clouds(
    val all: Int
)
