package com.example.democompose.data.model

import com.google.gson.annotations.SerializedName

class BaseModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)