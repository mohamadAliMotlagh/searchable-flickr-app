package com.motlagh.flickrlist.presenter

import app.cash.turbine.test
import com.motlagh.flickrlist.domain.GetListUseCase
import com.motlagh.flickrlist.domain.model.FlickrModel
import com.motlagh.quicksearch.domain.model.QueryModel
import com.motlagh.quicksearch.domain.usecase.GetSavedQueriesUseCase
import com.motlagh.quicksearch.domain.usecase.SaveQueryUseCase
import com.motlagh.uikit.*
import com.motlagh.uikit.navigator.Navigator
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description


@ExperimentalCoroutinesApi
class MainDispatcherRule(val dispatcher: TestDispatcher = StandardTestDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) = Dispatchers.setMain(dispatcher)

    override fun finished(description: Description?) = Dispatchers.resetMain()

}

class FlickrViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var getListUseCase: GetListUseCase

    @RelaxedMockK
    private lateinit var getQueries: GetSavedQueriesUseCase

    @RelaxedMockK
    private lateinit var saveQuery: SaveQueryUseCase

    @RelaxedMockK
    private lateinit var navigator: Navigator

    private lateinit var objectUnderTest: FlickrViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setupViewModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should show Empty list when save query use case comes to failure`() = runTest {

        coEvery { saveQuery.invoke("message") } returns Result.failure(Exception("message"))
        coEvery { getListUseCase.invoke("message") } returns Result.failure(Exception("message"))
        setupViewModel()

        //when
        objectUnderTest.requestSearchWithQueryText("message")
        mainDispatcherRule.dispatcher.scheduler.advanceTimeBy(600)//because of debounce


        objectUnderTest.recentSearchItems.test {
            assertTrue(awaitItem().isEmpty())
            cancelAndConsumeRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should show ViewError when call get image list request from server and result is failure`() =
        runTest {

            coEvery { saveQuery.invoke("message") } returns Result.failure(Exception("message"))
            coEvery { getListUseCase.invoke("message") } returns Result.failure(Exception("message"))
            setupViewModel()

            //when
            objectUnderTest.requestSearchWithQueryText("message")
            mainDispatcherRule.dispatcher.scheduler.advanceTimeBy(600)// because of debounce

            objectUnderTest.images.test {
                assertTrue(awaitItem() is ViewError)
                cancelAndConsumeRemainingEvents()
            }

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should show ViewData when call get image list request from server and result is success`() =
        runTest {
            coEvery { saveQuery.invoke("message") } returns Result.failure(Exception("message"))
            coEvery { getListUseCase.invoke("message") } returns Result.success(
                listOf(
                    FlickrModel(
                        "",
                        "",
                        "",
                        ""
                    )
                )
            )
            setupViewModel()

            //when
            objectUnderTest.requestSearchWithQueryText("message")
            mainDispatcherRule.dispatcher.scheduler.advanceTimeBy(600)// because of debounce

            objectUnderTest.images.test {
                assertTrue(awaitItem() is ViewData)
                cancelAndConsumeRemainingEvents()
            }
        }


    @Test
    fun `should show filled List when call recent search from database with success state`() =
        runTest {

            coEvery { getQueries.invoke() } returns flowOf(
                Result.success(
                    listOf(
                        QueryModel("test"),
                        QueryModel("test2")
                    )
                )
            )

            setupViewModel()
            objectUnderTest.recentSearchItems.test {

                assertTrue(awaitItem().isEmpty())
                assertTrue(awaitItem().isNotEmpty())

                cancelAndConsumeRemainingEvents()
            }
        }


    @Test
    fun `should show Empty list when call recent search with failure state`() = runTest {

        coEvery { getQueries.invoke() } returns flowOf(Result.failure(Exception()))
        setupViewModel()
        objectUnderTest.recentSearchItems.test {
            // val item = awaitItem()
            assertTrue(awaitItem().isEmpty())
            cancelAndConsumeRemainingEvents()
        }
    }


    private fun setupViewModel() {
        objectUnderTest = FlickrViewModel(getListUseCase, getQueries, saveQuery, navigator)
    }
}