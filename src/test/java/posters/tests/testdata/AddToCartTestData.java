package posters.tests.testdata;

public class AddToCartTestData
{
    private String topCategory;
    private String subCategory;
    private int expectedCategoryResultCount;
    private int expectedSubCategoryResultCount;
    private int position;
    private String searchTerm;
    private int searchResultCount;
    private int amountChange;
    
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
    
    public int getPosition()
    {
        return position;
    }
    
    public String getSearchTerm()
    {
        return searchTerm;
    }
    
    public int getSearchResultCount()
    {
        return searchResultCount;
    }
    
    public int getAmountChange()
    {
        return amountChange;
    }
}