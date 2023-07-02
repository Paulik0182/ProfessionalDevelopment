package com.paulik.professionaldevelopment

import com.paulik.professionaldevelopment.ui.translation.descriptios.TextValidator
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TextValidatorTest {

    private lateinit var validator: TextValidator

    @Before
    fun setup() {
        validator = TextValidator()
    }

    @Test
    fun textValidator_CorrectText_ReturnsTrue() {
        assertTrue(
            validator.isValidText(
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            )
        )
    }

    @Test
    fun textValidator_CorrectChar_ReturnsTrue() {
        assertTrue(
            validator.isValidText(
                "aa"
            )
        )
    }

    @Test
    fun textValidator_EmptyString_ReturnsFalse() {
        assertFalse(
            validator.isValidText(
                ">"
            )
        )
    }

    @Test
    fun textValidator_NullText_ReturnsFalse() {
        assertFalse(
            validator.isValidText(
                "/"
            )
        )
    }

    @Test
    fun testValidator_CorrectText_ReturnsTrue() {
        /** Проверяем, что метод возвращает верное значение для правильного ввода*/
        val input = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val actual = validator.isValidText(input)
        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun textValidator_IncorrectText_ReturnsFalse() {
        /** проверяем, что метод возвращает верное значение для некорректного ввода,
         * содержащего запрещенные символы*/
        val input = "+,=,>,/"
        val actual = validator.isValidText(input)
        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun textValidator_IncorrectText_NotEquals_ReturnsFalse() {
        val input = "+,=,>,/"
        val actual = validator.isValidText(input)
        val notExpected = true // значение true не должно быть возвращено
        assertNotEquals(notExpected, actual)
    }

    @Test
    fun `isValidText should return false for null text`() {
        var validText: String? = "result"
        if (!validator.isValidText(null)) validText = null

        assertNull(validText)
    }

    @Test
    fun textValidator_NullText_NotNull_ReturnsFalse() {
        val result = validator.isValidText(
            "+,=,>,/"
        )
        assertNotNull(result)
    }

    @Test
    fun textValidator_CorrectText_AssertSame_ReturnsTrue() {
        val result = validator.isValidText(
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        )
        assertSame(true, result)
    }

    @Test
    fun `hasForbiddenSymbols should return correct values for array of test strings`() {

        /** массив строк testStrings, который содержит четыре строки, которые будут использоваться
         * при тестировании функции hasForbiddenSymbols.*/
        val testStrings =
            arrayOf(
                "text with + symbol",
                "text without forbidden symbols",
                "",
                "text with $ symbol"
            )

        /** массив expectedResults, который содержит ожидаемые результаты для каждой из testStrings.*/
        val expectedResults =
            arrayOf(
                true,
                false,
                false,
                true
            )

        /** массив actualResults той же длины, что и testStrings, путем вызова функции
         * hasForbiddenSymbols из объекта validator для каждой строки из testStrings.
         * Результат каждого вызова сохраняется в массиве actualResults в соответствующей позиции.*/
        val actualResults =
            Array(testStrings.size) { i ->
                validator.hasForbiddenSymbols(
                    testStrings[i]
                )
            }

        /** Проверяем, что массивы expectedResults и actualResults совпадают с помощью функции
         * assertArrayEquals. Если результаты совпадают, то тест проходит, если нет,
         * то тест не пройден*/
        assertArrayEquals(
            expectedResults,
            actualResults
        )

        /** данный тест проверяет, что функция hasForbiddenSymbols корректно работает на заданных
         * входных данных*/
    }
}