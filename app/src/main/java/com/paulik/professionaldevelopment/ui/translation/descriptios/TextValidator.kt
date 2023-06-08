package com.paulik.professionaldevelopment.ui.translation.descriptios

import android.text.Editable
import android.text.TextWatcher
import java.util.regex.Pattern

class TextValidator : TextWatcher {

    internal var isValid = false

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun afterTextChanged(editableText: Editable?) {
        isValid = isValidText(editableText)
    }

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