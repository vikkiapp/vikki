package ir.nevercom.somu.di


import ir.nevercom.somu.ui.screen.HomeViewModel
import ir.nevercom.somu.ui.screen.LoginScreenViewModel
import ir.nevercom.somu.ui.screen.MovieDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { params -> MovieDetailsViewModel(get(), movieId = params.get()) }
}