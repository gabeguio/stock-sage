package stocksageservice.dependency;

import stocksageservice.activity.GetQueryActivity;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return GetPlaylistActivity
     */
    GetQueryActivity provideGetQueryActivity();

}