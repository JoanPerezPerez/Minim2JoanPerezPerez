package com.example.robacobres_androidclient.callbacks;

import com.example.robacobres_androidclient.models.Forum;

import java.util.List;

public interface ForumCallback {
    void onForumCallback(List<Forum> lista);
    void onError();
    void onMessage(String errorMessage);
}
