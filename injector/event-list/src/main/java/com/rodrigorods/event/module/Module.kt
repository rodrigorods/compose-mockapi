package com.rodrigorods.event.module

import com.rodrigorods.events.EventApi
import com.rodrigorods.events.EventListViewModel
import com.rodrigorods.events.detail.EventDetailViewModel
import com.rodrigorods.events.data.repository.EventListRepositoryImpl
import com.rodrigorods.events.repository.EventListRepository
import com.rodrigorods.events.usecase.EventDetailUseCase
import com.rodrigorods.events.usecase.EventListUseCase
import com.rodrigorods.events.usecase.EventListUseCaseImpl
import com.rodrigorods.events.usecase.IEventDetailUseCaseImpl
import com.rodrigorods.network.builder.createApi
import com.rodrigorods.network.builder.provideOkHttpClient
import com.rodrigorods.network.builder.provideRetrofit
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val eventListUiModule = module {
    viewModelOf(::EventListViewModel)
    viewModelOf(::EventDetailViewModel)
}

val eventListDomainModule = module {
    factoryOf(::EventListUseCaseImpl) { bind<EventListUseCase>() }
    factoryOf(::IEventDetailUseCaseImpl) { bind<EventDetailUseCase>() }
}

val eventLisApiModule = module {
    factory { createApi<EventApi>(get()) }
    factoryOf(::EventListRepositoryImpl) { bind<EventListRepository>() }
}

val networkModule = module {
    factory { provideOkHttpClient() }
    single {
        provideRetrofit(
            apiUrl = "https://5f5a8f24d44d640016169133.mockapi.io/",
            okHttpClient = get()
        )
    }
}
