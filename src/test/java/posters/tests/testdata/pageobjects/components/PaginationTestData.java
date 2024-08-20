package posters.tests.testdata.pageobjects.components;

public class PaginationTestData
{
    private String topCategory;
    
    private int expectedResultCount;
    
    public String getTopCategory()
    {
        return topCategory;
    }
    
    public int getExpectedResultCount() 
    {
        return expectedResultCount;
    }
    
    @Override
    public String toString()
    {
        return "PaginationTestData [topCategory=" + topCategory + ", expectedResultCount=" + expectedResultCount + "]";
    }
}