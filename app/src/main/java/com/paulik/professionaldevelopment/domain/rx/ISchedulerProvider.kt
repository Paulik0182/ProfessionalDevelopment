package com.paulik.professionaldevelopment.domain.rx

import io.reactivex.Scheduler

// Для тестирования
interface ISchedulerProvider {

    fun ui(): Scheduler

    fun io(): Scheduler
}