package ir.nevercom.somu.di


import ir.nevercom.somu.ui.screen.home.HomeViewModel
import ir.nevercom.somu.ui.screen.login.LoginScreenViewModel
import ir.nevercom.somu.ui.screen.movieDetails.MovieDetailsViewModel
import ir.nevercom.somu.ui.screen.search.SearchScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { SearchScreenViewModel(get()) }
    viewModel { params -> MovieDetailsViewModel(get(), params.get()) }
}