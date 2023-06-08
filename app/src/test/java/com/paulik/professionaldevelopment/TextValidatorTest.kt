package com.paulik.professionaldevelopment

import com.paulik.professionaldevelopment.ui.translation.descriptios.TextValidator
import org.junit.Assert.assertFalse
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
    fun textValidatorCorrectTextReturnsTrue() {
        assertTrue(
            validator.isValidText(
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            )
        )
    }

    @Test
    fun textValidatorCorrectCharReturnsTrue() {
        assertTrue(
            validator.isValidText(
                "r"
            )
        )
    }

    @Test
    fun textValidatorEmptyStringReturnsFalse() {
        assertFalse(validator.isValidText(">"))
    }

    @Test
    fun textValidatorNullTextReturnsFalse() {
        assertFalse(validator.isValidText("/"))
    }
}