package com.ayannah.asira.screen.agent.tab_agent_profile;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.google.gson.JsonObject;

import java.util.List;

public interface AgentProfileContract {
    interface View extends BaseView<Presenter> {

        void showErrorMessage(String message);

        void loadAgentProfile(PreferenceRepository preferenceRepository);

        void loadMenus(List<String> akuns, List<String> tentangs);

//        void successUpdateProfileAgent();
//
//        void successUpdatePhotoAgent();
//
//        void setAgentProviderName(String name);

        void logoutComplete();
    }

    interface Presenter extends BasePresenter<View> {

        void getAgentProfile();

        void getMenus();

//        void setAgentProfile();

//        void patchDataAgent(JsonObject jsonPatchAgentProfile, String email, boolean isEmailChange);
//
//        void patchAgentPhotoProfile(String pict);
//
//        void getProviderName(String agentProvider);

        void doLogout();
    }
}
