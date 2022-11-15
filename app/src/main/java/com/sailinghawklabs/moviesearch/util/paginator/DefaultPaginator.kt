package com.sailinghawklabs.moviesearch.util.paginator

// https://github.com/philipplackner/ComposePagingYT/blob/final/app/src/main/java/com/plcoding/composepagingyt/DefaultPaginator.kt

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val isLoading: (Boolean) -> Unit,
    private inline val requestPage: suspend (page: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (existingKey: Key) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit,
): Paginator<Key, Item> {
    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true
        isLoading(true)
        val result = requestPage(currentKey)
        isMakingRequest = false
        val items = result.getOrElse {
            onError(it)
            isLoading(false)
            return
        }
        currentKey = getNextKey(currentKey)
        onSuccess(items, currentKey)
        isLoading(false)
    }

    override fun reset() {
        currentKey = initialKey
    }

}