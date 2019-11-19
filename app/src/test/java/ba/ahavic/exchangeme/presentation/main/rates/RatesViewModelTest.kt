package ba.ahavic.exchangeme.presentation.main.rates

import androidx.lifecycle.*
import ba.ahavic.exchangeme.R
import ba.ahavic.exchangeme.base.BaseViewModelTest
import ba.ahavic.exchangeme.base.TestUtil
import ba.ahavic.exchangeme.core.AppError
import ba.ahavic.exchangeme.core.AppException
import ba.ahavic.exchangeme.core.ReasonOfError
import ba.ahavic.exchangeme.data.rates.RatesRepository
import ba.ahavic.exchangeme.presentation.base.InvalidBaseError
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RatesViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: RatesViewModel

    @MockK
    private lateinit var ratesRepository: RatesRepository

    @MockK
    private lateinit var ratesViewObserver: Observer<List<RatesView>>

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = RatesViewModel(ratesRepository)
    }

    @Before
    fun prepare() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun `get rates invalid base error`() = runBlockingTest {
        // stubs
        coEvery { ratesRepository.getRates(any()) } throws AppException(AppError(reason = ReasonOfError.InvalidBase))

        // observers
        viewModel.error.observeForever(errorObserver)
        viewModel.ratesView.observeForever(ratesViewObserver)

        val lifecycle = LifecycleRegistry(mockkClass(LifecycleOwner::class))
        lifecycle.addObserver(viewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        // assertion
        verify(exactly = 0) { ratesViewObserver.onChanged(any()) }
        verify(exactly = 1) { errorObserver.onChanged(InvalidBaseError) }
    }

    @Test
    fun `get rates success`() = runBlockingTest {
        // stubs
        val mockRates = TestUtil.getRates()
        coEvery { ratesRepository.getRates(any()) } returns produce {
            send(mockRates)
            close()
        }
        // observers
        viewModel.error.observeForever(errorObserver)
        viewModel.ratesView.observeForever(ratesViewObserver)

        val lifecycle = LifecycleRegistry(mockkClass(LifecycleOwner::class))
        lifecycle.addObserver(viewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        // assertion
        verify(exactly = 1) {
            ratesViewObserver.onChanged(listOf(
                RatesView("EUR", R.string.all_eur, R.drawable.european_union, "1"),
                RatesView("AUD", R.string.all_aud, R.drawable.australia, "1.62"),
                RatesView("BRL", R.string.all_brl, R.drawable.brazil, "4.8"),
                RatesView("IDR", R.string.all_idr, R.drawable.indonesia, "17350"),
                RatesView("ZAR", R.string.all_zar, R.drawable.south_africa, "17.85")
            ))
        }

        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }

    @Test
    fun `change base rate amount`() = runBlockingTest {
        // stubs
        val mockRates = TestUtil.getRates()
        coEvery { ratesRepository.getRates(any()) } returns produce {
            send(mockRates)
            close()
        }
        // observers
        viewModel.error.observeForever(errorObserver)
        viewModel.ratesView.observeForever(ratesViewObserver)

        val lifecycle = LifecycleRegistry(mockkClass(LifecycleOwner::class))
        lifecycle.addObserver(viewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        viewModel.onRateEdited("1.8", viewModel.ratesView.value!![0])

        // assertion
        verify(exactly = 1) {
            ratesViewObserver.onChanged(listOf(
                RatesView("EUR", R.string.all_eur, R.drawable.european_union, "1.8"),
                RatesView("AUD", R.string.all_aud, R.drawable.australia, "2.91"),
                RatesView("BRL", R.string.all_brl, R.drawable.brazil, "8.64"),
                RatesView("IDR", R.string.all_idr, R.drawable.indonesia, "31230"),
                RatesView("ZAR", R.string.all_zar, R.drawable.south_africa, "32.13")
            ))
        }

        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }

    @Test
    fun `change base rate amount to zero`() = runBlockingTest {
        // stubs
        val mockRates = TestUtil.getRates()
        coEvery { ratesRepository.getRates(any()) } returns produce {
            send(mockRates)
            close()
        }

        // observers
        viewModel.error.observeForever(errorObserver)
        viewModel.ratesView.observeForever(ratesViewObserver)

        val lifecycle = LifecycleRegistry(mockkClass(LifecycleOwner::class))
        lifecycle.addObserver(viewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        viewModel.onRateEdited("0", viewModel.ratesView.value!![0])

        // assertion
        verify(exactly = 1) {
            ratesViewObserver.onChanged(listOf(
                RatesView("EUR", R.string.all_eur, R.drawable.european_union, "0"),
                RatesView("AUD", R.string.all_aud, R.drawable.australia, "0"),
                RatesView("BRL", R.string.all_brl, R.drawable.brazil, "0"),
                RatesView("IDR", R.string.all_idr, R.drawable.indonesia, "0"),
                RatesView("ZAR", R.string.all_zar, R.drawable.south_africa, "0")
            ))
        }

        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }

    @Test
    fun `on aud rate selected`() = runBlockingTest {
        // stubs
        val mockRates = TestUtil.getRates()
        val mockAudRates = TestUtil.getRatesAUD()
        coEvery { ratesRepository.getRates("EUR") } returns produce {
            send(mockRates)
            close()
        }

        coEvery { ratesRepository.getRates("AUD") } returns produce {
            send(mockAudRates)
            close()
        }

        // observers
        viewModel.error.observeForever(errorObserver)
        viewModel.ratesView.observeForever(ratesViewObserver)

        val lifecycle = LifecycleRegistry(mockkClass(LifecycleOwner::class))
        lifecycle.addObserver(viewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        viewModel.onRateSelected(1)

        // assertion
        verify(exactly = 1) {
            ratesViewObserver.onChanged(listOf(
                RatesView("EUR", R.string.all_eur, R.drawable.european_union, "1"),
                RatesView("AUD", R.string.all_aud, R.drawable.australia, "1.62"),
                RatesView("BRL", R.string.all_brl, R.drawable.brazil, "4.8"),
                RatesView("IDR", R.string.all_idr, R.drawable.indonesia, "17350"),
                RatesView("ZAR", R.string.all_zar, R.drawable.south_africa, "17.85")
            ))

            ratesViewObserver.onChanged(listOf(
                RatesView("AUD", R.string.all_aud, R.drawable.australia, "1.62"),
                RatesView("EUR", R.string.all_eur, R.drawable.european_union, "1"),
                RatesView("BRL", R.string.all_brl, R.drawable.brazil, "4.81"),
                RatesView("IDR", R.string.all_idr, R.drawable.indonesia, "17382.6"),
                RatesView("ZAR", R.string.all_zar, R.drawable.south_africa, "17.88")
            ))
        }
        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }
}
