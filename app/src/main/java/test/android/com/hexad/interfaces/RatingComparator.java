package test.android.com.hexad.interfaces;

import java.util.Comparator;

import test.android.com.hexad.model.FavoriteItem;

/**
 * Created by shetts7 on 3/10/2019.
 */

public class RatingComparator implements Comparator<FavoriteItem> {

    @Override
    public int compare(FavoriteItem object1, FavoriteItem object2) {
        return  object2.getRating()-object1.getRating();
    }
}
