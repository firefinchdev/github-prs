package com.company.githubprs.utils

import kotlinx.serialization.json.Json

val json = Json {
    this.ignoreUnknownKeys = true
}