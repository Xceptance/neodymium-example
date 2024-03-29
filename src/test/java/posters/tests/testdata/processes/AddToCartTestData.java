package posters.tests.testdata.processes;

public class AddToCartTestData
{
    private String topCategory;

    private int categoryResultPosition;

    private String subCategory;

    private int subCategoryResultPosition;
    
    private String sizeFirstProduct;
    
    private String styleFirstProduct;

    private String searchTerm;

    private int searchResultPosition;
    
    private String sizeSecondProduct;
    
    private String styleSecondProduct;
    
    private int productUpdatePosition;

    private int amountChange;

    /// ========== top category ========== ///
    
    public String getTopCategory()
    {
        return topCategory;
    }

    public int getCategoryResultPosition()
    {
        return categoryResultPosition;
    }
    
    /// ========== sub category ========== ///
    
    public String getSubCategory()
    {
        return subCategory;
    }
    
    public int getSubCategoryResultPosition()
    {
        return subCategoryResultPosition;
    }
    
    public String getSizeFirstProduct()
    {
        return sizeFirstProduct;
    }
    
    public String getStyleFirstProduct()
    {
        return styleFirstProduct;
    }

    /// ========== search term ========== ///
    
    public String getSearchTerm()
    {
        return searchTerm;
    }
    
    public int getSearchResultPosition()
    {
        return searchResultPosition;
    }
    
    public String getSizeSecondProduct()
    {
        return sizeSecondProduct;
    }
    
    public String getStyleSecondProduct()
    {
        return styleSecondProduct;
    }

    /// ========== update data ========== ///
    
    public int getProductUpdatePosition()
    {
        return productUpdatePosition;
    }
    
    public int getAmountChange()
    {
        return amountChange;
    }
}