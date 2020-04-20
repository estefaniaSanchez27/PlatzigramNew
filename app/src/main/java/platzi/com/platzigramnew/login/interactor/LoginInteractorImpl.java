package platzi.com.platzigramnew.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

import platzi.com.platzigramnew.login.presenter.LoginPresenter;
import platzi.com.platzigramnew.login.repository.LoginRepository;
import platzi.com.platzigramnew.login.repository.LoginRepositoryImpl;

public class LoginInteractorImpl implements LoginInteractor {

    private LoginPresenter loginPresenter;
    private LoginRepository repository;

    public LoginInteractorImpl(LoginPresenter presenter) {
        this.loginPresenter=presenter;
        repository = new LoginRepositoryImpl(loginPresenter);
    }

    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        repository.signIn(username,password,activity,firebaseAuth);
    }
}
