package com.ayannah.asira.screen.agent.navigationmenu.agentprofile;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public interface AgentProfileContract {

    interface View extends BaseView<Presenter> {

        void showErrorMessage(String message);

        void loadAgentProfile(PreferenceRepository preferenceRepository);

        void successUpdateProfileAgent();

        void successUpdatePhotoAgent();
    }

    interface Presenter extends BasePresenter<View> {

        void setAgentProfile();

        void patchDataAgent(JSONObject jsonPatchAgentProfile);

        void patchAgentPhotoProfile(String pict);
    }
}
