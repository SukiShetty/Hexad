package test.android.com.hexad.dependancy;

import dagger.Component;

/**
 * Created by shetts7 on 3/9/2019.
 */
@Component(modules = FavoriteItemModule.class)
public interface FavoriteComponent {

     FavoriteItem getFavoriteItem();
}
