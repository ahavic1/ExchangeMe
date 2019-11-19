package ba.ahavic.exchangeme.base

import android.os.Bundle
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ba.ahavic.exchangeme.presentation.base.BaseError
import ba.ahavic.exchangeme.presentation.base.NavigationEvent
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

abstract class BaseViewModelTest {

    /**
     * This rule forces LiveData updates to happen on calling thread
     */
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    lateinit var arguments: Bundle

    @MockK
    lateinit var errorObserver: Observer<BaseError>

    @MockK
    lateinit var navigationObserver: Observer<NavigationEvent>

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Before
    open fun setUp() {
        Log.d("TEST", "setUp")
    }
}


@ExperimentalCoroutinesApi
class CoroutineTestRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement =
        object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(TestCoroutineDispatcher())
                try {
                    base?.evaluate()
                } finally {
                    Dispatchers.resetMain()
                }
            }
        }
}