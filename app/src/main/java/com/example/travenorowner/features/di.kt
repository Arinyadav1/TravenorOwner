package com.example.travenorowner.features

import com.example.travenorowner.features.ownerRequest.OwnerRequestViewModel
import com.example.travenorowner.features.search.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::OwnerRequestViewModel)
}