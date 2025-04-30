package posters.tests.smoke;

import org.junit.Assert;
import org.junit.;

import com.xceptance.neodymium.junit5.NeodymiumTest;

public class SimpleTest{

// This is a simple test to check if the sum of two integers is correct
public int sumOfTwoInts( int x, int y){
    int sum = x+y;
    return sum;
    }
    @NeodymiumTest
public  Void testSum(){
    Assert.assertEquals( 3, sumOfTwoInts (1,5));
    return null;
    
    }
}



