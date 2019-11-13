package ba.ahavic.exchangeme.data.base

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

abstract class BaseRepository {
    @Inject
    protected lateinit var dispachers: Dispatchers
}