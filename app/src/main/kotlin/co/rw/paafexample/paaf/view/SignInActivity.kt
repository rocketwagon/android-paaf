package co.rw.paafexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.rw.paafexample.paaf.base.SignInAction
import co.rw.paafexample.paaf.base.SignInClickEvent
import co.rw.paafexample.paaf.presenter.SignInViewModel
import co.rw.paafexample.paaf.presenter.signInPresenter
import kotlinx.android.synthetic.main.activity_signin.email_sign_in_button
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.selects.whileSelect
import org.jetbrains.anko.sdk23.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * A login screen that offers login via email/password.
 */
class SignInActivity : AppCompatActivity() {

    private val clickEventChannel = Channel<SignInClickEvent>()
    private var viewModel: SignInViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        email_sign_in_button.onClick {
            clickEventChannel.send(SignInClickEvent.SignInButton)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel = signInPresenter(clickEventChannel)

        launch(UI) {
            whileSelect {
                viewModel?.signInActionChannel?.onReceive { signInAction ->
                    when (signInAction) {
                        SignInAction.SignInSuccessful -> {
                            toast("Sign In Success!").show()
                            true
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel = null
    }
}