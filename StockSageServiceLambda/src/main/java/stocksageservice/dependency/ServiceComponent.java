package stocksageservice.dependency;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import stocksageservice.activity.*;

import dagger.Component;
import stocksageservice.activity.requests.GetRecentQueriesRequest;
import stocksageservice.activity.requests.GetSavedQueriesRequest;
import stocksageservice.activity.requests.UnsaveQueryRequest;
import stocksageservice.lambda.SaveQueryLambda;

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
    GetSavedQueriesActivity provideGetSavedQueriesActivity();
    SaveQueryActivity provideSaveQueryActivity();
    UnsaveQueryActivity provideUnsaveQueryActivity();
}
