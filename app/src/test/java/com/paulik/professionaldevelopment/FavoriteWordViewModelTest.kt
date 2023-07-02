package com.paulik.professionaldevelopment

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.paulik.models.entity.FavoriteEntity
import com.paulik.professionaldevelopment.ui.favorite.FavoriteWordViewModel
import com.paulik.repository.data.room.favorite.FavoriteDataBaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class FavoriteWordViewModelTest {

    private lateinit var viewModel: FavoriteWordViewModel
    private lateinit var favoriteLocalRepo: FavoriteDataBaseImpl

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        favoriteLocalRepo = mock()
        viewModel = FavoriteWordViewModel(favoriteLocalRepo)
    }

    @Test
    fun testOnRefresh() {
        coroutineTestRule.runBlockingTest {
            val word = "word"
            // Устанавливаем ожидаемое значение
            val expectedFavorites = listOf<FavoriteEntity>(FavoriteEntity("word", ""))
            whenever(favoriteLocalRepo.getFavoriteEntities()).thenReturn(expectedFavorites)

            // Вызываем метод, который хотим протестировать
            viewModel.onRefresh()

            // Проверяем, что полученное значение совпадает с ожидаемым
            assert(viewModel.favoriteEntityLiveData.value == expectedFavorites)
        }
    }

    @Test
    fun testOnWordClick() {
        // Создаем тестовые данные
        val favoriteEntity = FavoriteEntity("word", "")

        // Вызываем метод, который хотим протестировать
        viewModel.onWordClick(favoriteEntity)

        // Проверяем, что полученное значение совпадает с ожидаемым
        assert(viewModel.selectedDetailsWordLiveData.value == favoriteEntity)
    }

    @Test
    fun testSearchData() {
        coroutineTestRule.runBlockingTest {
            // Устанавливаем ожидаемые значения и состояния
            val word = "word"
            val expectedData = listOf<FavoriteEntity>(FavoriteEntity("word", ""))
            whenever(favoriteLocalRepo.getFavoriteWord(word)).thenReturn(expectedData)

            // Вызываем метод, который хотим протестировать
            viewModel.searchData(word)

            val value = viewModel.favoriteEntityLiveData.value

            val containsWord = value?.any{
                it.word == word
            }?: false

            assertTrue(containsWord)
        }
    }

    @Test
    fun testDeleteWord() {
        coroutineTestRule.runBlockingTest {
            // Устанавливаем ожидаемые значения
            val word = "word"
            val updatedList = listOf<FavoriteEntity>(FavoriteEntity("word", ""))
            whenever(favoriteLocalRepo.getFavoriteEntities()).thenReturn(updatedList)

            // Вызываем метод, который хотим протестировать
            viewModel.deleteWord(word)

            // Проверяем, что полученное значение совпадает с ожидаемым
            assert(viewModel.favoriteEntityLiveData.value == updatedList)

            // Проверяем, что метод deleteWord был вызван с правильными аргументами
            verify(favoriteLocalRepo).deleteWord(word)
        }
    }
}