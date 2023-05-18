package com.paulik.professionaldevelopment.di

import com.paulik.professionaldevelopment.data.ElapsedTimeCalculator
import com.paulik.professionaldevelopment.data.StopwatchListOrchestratorImpl
import com.paulik.professionaldevelopment.data.StopwatchStateCalculatorImpl
import com.paulik.professionaldevelopment.data.StopwatchStateHolderImpl
import com.paulik.professionaldevelopment.data.TimestampProviderImpl
import com.paulik.professionaldevelopment.domain.StopwatchListOrchestrator
import com.paulik.professionaldevelopment.domain.StopwatchStateCalculator
import com.paulik.professionaldevelopment.domain.StopwatchStateHolder
import com.paulik.professionaldevelopment.domain.TimestampProvider
import com.paulik.professionaldevelopment.ui.stopwatch.StopwatchViewModel
import com.paulik.professionaldevelopment.ui.utils.TimestampMillisecondsFormatter
import org.koin.dsl.module

val application = module {

    single<TimestampProvider> {
        TimestampProviderImpl()
    }

    single<TimestampMillisecondsFormatter> {
        TimestampMillisecondsFormatter()
    }

    factory<StopwatchStateHolder> {
        StopwatchStateHolderImpl(
            get<StopwatchStateCalculator>(),
            get<ElapsedTimeCalculator>(),
            get<TimestampMillisecondsFormatter>()
        )
    }

    single<ElapsedTimeCalculator> {
        ElapsedTimeCalculator(
            get<TimestampProvider>()
        )
    }

    single<StopwatchStateCalculator> {
        StopwatchStateCalculatorImpl(
            get<TimestampProvider>(),
            get<ElapsedTimeCalculator>()
        )
    }

    single<StopwatchListOrchestrator> {
        StopwatchListOrchestratorImpl()

    }

}

val stopwatchScreen = module {

    single {
        StopwatchViewModel(
            get<StopwatchListOrchestrator>()
        )
    }
}