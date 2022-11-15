package com.sailinghawklabs.moviesearch.util.paginator

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}