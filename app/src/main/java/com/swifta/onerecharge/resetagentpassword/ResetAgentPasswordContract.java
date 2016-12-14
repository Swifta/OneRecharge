package com.swifta.onerecharge.resetagentpassword;

import com.swifta.onerecharge.BasePresenter;
import com.swifta.onerecharge.BaseView;

/**
 * Created by moyinoluwa on 11/28/16.
 */

public interface ResetAgentPasswordContract {

    interface View extends BaseView<Presenter> {

        void showNoInternetMessage();

        void showProgressBar();

        void hideProgressBar();

        void showPasswordLayout();

        void hidePasswordLayout();

        void showSuccessfullyChangedMessage(String message);

        void showEmptyTextFields();

        void showErrorMessage();
    }

    interface Presenter extends BasePresenter {

        String getStoredEmailAddress(String emailId);

        String getStoredAuthToken(String authTokenId);

        void submitPasswordRequest(String oldPassword, String newPassword);
    }
}
