package com.swifta.onerecharge.agent.resetagentpassword;

import com.swifta.onerecharge.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by moyinoluwa on 12/13/16.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ResetAgentPasswordModule.class})
public interface ResetAgentPasswordComponent {
    void inject(ProfileActivity profileActivity);
}
