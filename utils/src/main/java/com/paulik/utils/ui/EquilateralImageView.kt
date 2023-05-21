package com.paulik.utils.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * Это кастомная View
 *
 * @JvmOverloads - аннотация позволяет автоматически генерировать несколько перегруженных методов
 * в байткоде с различным числом аргументов. Аннотация @JvmOverloads доносит до компилятора Kotlin,
 * что нужно сгенерировать перегруженные методы для всех возможных комбинаций аргументов,
 * включая те, которые имеют значения по умолчанию.
 */

// TODO Требует пояснения

class EquilateralImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    /** Метод для отрисовки элемента, то-есть элемент делаем просто квадратным*/
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
