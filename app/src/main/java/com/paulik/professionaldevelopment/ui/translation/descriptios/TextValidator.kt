package com.paulik.professionaldevelopment.ui.translation.descriptios

import java.util.regex.Pattern

class TextValidator {

    fun isValidText(text: CharSequence?): Boolean {
        if (text == null) return false
        return !hasForbiddenSymbols(text) && TEXT_PATTERN.matcher(text).matches()
    }

    fun hasForbiddenSymbols(text: CharSequence?): Boolean {
        text?.let {
            // Проверяем, содержит ли текст запрещенные символы
            if (it.contains("+") || it.contains("=") || it.contains(",") || it.contains("/") ||
                it.contains("|") || it.contains("[") || it.contains("]") || it.contains("{") ||
                it.contains("}") || it.contains("<") || it.contains(">") || it.contains("*") ||
                it.contains("$")
            ) {
                return true // Есть запрещенные символы
            }
        }
        return false // Все символы допустимы, либо текст пустой
    }

    companion object {
        private val TEXT_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\p{Punct}\\s]{1,256}([ -]?[a-zA-Z0-9\\p{Punct}\\s]+)*"
        )
    }
}