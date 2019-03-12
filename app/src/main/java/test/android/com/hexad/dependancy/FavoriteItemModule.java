package test.android.com.hexad.dependancy;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shetts7 on 3/9/2019.
 */
@Module
public class FavoriteItemModule {

    @Provides
    FavoriteDescription provideFavoriteDescription(String description){
        return new FavoriteDescription(description);
    }

    @Provides
    FavoriteTitle provideFavoriteTitle(String title){
        return new FavoriteTitle(title);
    }

    @Provides
    Rating provideRating(int rating){
        return new Rating(rating);
    }

    @Provides
    FavoriteItem provideFavoriteItem(){
        return  new FavoriteItem();
    }
}
