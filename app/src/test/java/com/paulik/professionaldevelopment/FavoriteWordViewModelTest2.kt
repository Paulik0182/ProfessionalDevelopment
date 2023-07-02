package com.paulik.professionaldevelopment

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.paulik.models.entity.FavoriteEntity
import com.paulik.professionaldevelopment.ui.favorite.FavoriteWordViewModel
import com.paulik.repository.data.room.favorite.FavoriteDataBaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class FavoriteWordViewModelTest2 {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var favoriteLocalRepo: FavoriteDataBaseImpl

    private lateinit var viewModel: FavoriteWordViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = FavoriteWordViewModel(favoriteLocalRepo)
    }

    @Test
    fun `test onRefresh`() {
        runBlocking {
            val favorites = listOf(
                FavoriteEntity("Word 1"),
                FavoriteEntity("Word 2"),
                FavoriteEntity("Word 3")
            )
            `when`(favoriteLocalRepo.getFavoriteEntities()).thenReturn(favorites)

            viewModel.onRefresh()

            val result = viewModel.favoriteEntityLiveData.getOrAwaitValue()

            assertEquals(favorites, result)
        }
    }

    @Test
    fun `test onWordClick`() {
        val favoriteEntity = FavoriteEntity("Selected word")

        viewModel.onWordClick(favoriteEntity)

        val result = viewModel.selectedDetailsWordLiveData.value

        assertEquals(favoriteEntity, result)
    }

    @Test
    fun `test searchData`() {
        runBlocking {
            val word = "Test"
            val data = listOf(
                FavoriteEntity("Test word 1"),
                FavoriteEntity("Test word 2"),
                FavoriteEntity("Test word 3")
            )
            `when`(favoriteLocalRepo.getFavoriteWord(word)).thenReturn(data)

            viewModel.searchData(word)

            val result = viewModel.favoriteEntityLiveData.getOrAwaitValue()

            assertEquals(data, result)
        }
    }

    @Test
    fun `test deleteWord`() {
        runBlocking {
            val word = "Delete"
            val updatedList = listOf(
                FavoriteEntity("Word 1"),
                FavoriteEntity("Word 2"),
                FavoriteEntity("Word 3")
            )

            `when`(favoriteLocalRepo.getFavoriteEntities()).thenReturn(updatedList)

            viewModel.deleteWord(word)

            val result = viewModel.favoriteEntityLiveData.getOrAwaitValue()

            assertEquals(updatedList, result)
        }
    }
}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)
    afterObserve.invoke()
    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}