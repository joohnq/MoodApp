package com.joohnq.shared_resources

import org.jetbrains.compose.resources.ExperimentalResourceApi

object ResourceFiles {
    @OptIn(ExperimentalResourceApi::class)
    suspend fun charsets() = Res.readBytes(path = "files/charsets.json")

    @OptIn(ExperimentalResourceApi::class)
    suspend fun languages() = Res.readBytes(path = "files/languages.json")
}