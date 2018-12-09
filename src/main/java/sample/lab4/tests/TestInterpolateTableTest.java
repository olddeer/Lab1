package sample.lab4;

import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestInterpolateTableTest {

    @Test
    public void TestInterpolateTable() {
        TestInterpolateTable testInterpolateTable =
            new TestInterpolateTable(1,
                new double[]{7, 8, 9, 10, 11},
                new double[]{5, 6, 7, 3, 12});

        double[][] substractionTable = testInterpolateTable.fillSubstractionYValuesTable();
        double[][] expectedSubstractionTable = {{5, 6, 7, 3, 12},{1,1,-4,9,0},{0,-5,13,0,0},{-5,18,0,0,0},{23,0,0,0,0}};

        assertArrayEquals(substractionTable,expectedSubstractionTable);

    }


}
