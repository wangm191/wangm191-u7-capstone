package com.nashss.se.eartracker.dependency;

import com.nashss.se.eartracker.activity.AddListeningSessionActivity;
import com.nashss.se.eartracker.activity.DeleteListeningSessionActivity;
import com.nashss.se.eartracker.activity.EditListeningSessionActivity;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {
    /**
     * Provides the relevant activity.
     * @return AddListeningSessionActivity
     */
    AddListeningSessionActivity provideAddListeningSessionActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteListeningSessionActivity
     */
    DeleteListeningSessionActivity provideDeleteListeningSessionActivity();

    /**
     * Provides the relevant activity.
     * @return EditListeningSessionActivity
     */
    EditListeningSessionActivity provideEditListeningSessionActivity();
}
