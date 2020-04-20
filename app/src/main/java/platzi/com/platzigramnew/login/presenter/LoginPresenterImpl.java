package platzi.com.platzigramnew.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

import platzi.com.platzigramnew.login.interactor.LoginInteractor;
import platzi.com.platzigramnew.login.interactor.LoginInteractorImpl;
import platzi.com.platzigramnew.login.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginInteractor = new LoginInteractorImpl(this);
    }

    @Override
    public void signIn(String username, String password, Activity activity,FirebaseAuth firebaseAuth) {
        loginView.disableInputs();
        loginView.showProgressBar();
        loginInteractor.signIn(username,password,activity,firebaseAuth);
    }

    @Override
    public void loginSuccess() {
        loginView.goHome();
        loginView.hideProgressBar();
    }

    @Override
    public void loginError(String error) {
        loginView.enableInputs();
        loginView.hideProgressBar();
        loginView.loginError(error);

    }
}
