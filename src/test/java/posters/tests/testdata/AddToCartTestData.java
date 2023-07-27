package posters.tests.testdata;

public class AddToCartTestData
{
    private int position;
    private String topCategory;
    private String subCategory;
    private int expectedCategoryResultCount;
    private int expectedSubCategoryResultCount;
    private String searchTerm;
    private int expectedSearchResultCount;
    private int amountChange;
    
    public int getPosition()
    {
        return position;
    }
    
    public String getTopCategory() 
    {
        return topCategory;
    }
    
    public String getSubCategory() 
    {
        return subCategory;
    }
    
    public int getExpectedCategoryResultCount()
    {
        return expectedCategoryResultCount;
    }
    
    public int getExpectedSubCategoryResultCount()
    {
        return expectedSubCategoryResultCount;
    }
    
    public String getSearchTerm()
    {
        return searchTerm;
    }
    
    public int getExpectedSearchResultCount()
    {
        return expectedSearchResultCount;
    }
    
    public int getAmountChange()
    {
        return amountChange;
    }
}