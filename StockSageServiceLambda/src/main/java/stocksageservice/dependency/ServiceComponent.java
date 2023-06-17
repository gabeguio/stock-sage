package stocksageservice.dependency;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import stocksageservice.activity.CreateQueryActivity;
import stocksageservice.activity.GetQueryActivity;

import dagger.Component;
import stocksageservice.activity.GetRecentQueriesActivity;
import stocksageservice.activity.requests.GetRecentQueriesRequest;

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
    CreateQueryActivity provideCreateQueryActivity();

    GetRecentQueriesActivity provideGetRecentQueriesActivity();
}
