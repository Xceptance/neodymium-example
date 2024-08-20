package posters.tests.testdata.pageobjects.components;

public class SearchTestData
{
    private String searchTerm;
    
    private int expectedResultCount;
    
    public String getSearchTerm()
    {
        return searchTerm;
    }
    
    public int getExpectedResultCount()
    {
        return expectedResultCount;
    }
    
    @Override
    public String toString()
    {
        return "SearchTestData [searchTerm=" + searchTerm + ", expectedResultCount=" + expectedResultCount + "]";
    }
}