package com.paulik.professionaldevelopment.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * С помощью данной функции получаем состояние подключения к интернету
 */

fun isOnline(context: Context): Boolean {
    /** Получаем connectivityManager, обычно через Context получаем необходимый сервис и приводим
     * его к соответствующему типу (as ConnectivityManager) потому что getSystemService возращает
     * просто объект*/
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /** после перых действий получаем состояние сети (с помощью класса NetworkCapabilities)*/
    val netInfo: NetworkCapabilities? =
        connectivityManager
            .getNetworkCapabilities(connectivityManager.activeNetwork)

    /** делаем проверку что netInfo не null и проверяем текущий статус (fales - сети нет,
     * true - сеть есть)*/
    return netInfo != null && netInfo.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}