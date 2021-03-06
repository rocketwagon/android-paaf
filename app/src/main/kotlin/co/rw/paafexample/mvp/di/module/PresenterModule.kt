package co.rw.paafexample.mvp.di.module

import co.rw.paafexample.mvp.ui.signin.SignInPresenter
import co.rw.paafexample.mvp.ui.signin.SignInPresenterImpl
import dagger.Module
import dagger.Provides

@Module
open class PresenterModule {

    @Provides
    open fun providesSignInPresenter(): SignInPresenter = SignInPresenterImpl()
}
