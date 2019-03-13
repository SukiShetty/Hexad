package test.android.com.hexad.model;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by shetts7 on 3/10/2019.
 */

public class RatingActivityModelTest {

    @Mock
    RatingActivityModel ratingActivityModel;

    @Test
    public void testGetInstance() {

        RatingActivityModel lRatingActivityModel1 = RatingActivityModel.getInstance();
        RatingActivityModel lRatingActivityModel2 = RatingActivityModel.getInstance();

        assertNotNull(lRatingActivityModel1);
        assertNotNull(lRatingActivityModel2);

        assertEquals(lRatingActivityModel1,lRatingActivityModel2);
    }


    @Test(expected = NullPointerException.class)
    public void testCreateInitialSetOfData() {

        RatingActivityModel lRatingActivityModel= RatingActivityModel.getInstance();
        lRatingActivityModel.createInitialSetOfData(null,null);
        assertEquals(0,lRatingActivityModel.getDataList().size());
    }

    @Test
    public void testCreateInitialSetOfData1() {

        String[] array1 ={"Hi"};
        String[] array2 ={"Model"};
        RatingActivityModel lRatingActivityModel= RatingActivityModel.getInstance();
        lRatingActivityModel.createInitialSetOfData(array1,array2);
        assertEquals(1,lRatingActivityModel.getDataList().size());
    }

    @Test
    public void testUserTaskAndSortList() {
        String[] array1 ={"Hi","Simba","Mom"};
        String[] array2 ={"Model","Sarkar",null};
        RatingActivityModel lRatingActivityModel= RatingActivityModel.getInstance();
        lRatingActivityModel.createInitialSetOfData(array1,array2);
        lRatingActivityModel.executeUserTask(2,5);
        assertEquals("Mom",lRatingActivityModel.getDataList().get(0).getName());
    }
}
