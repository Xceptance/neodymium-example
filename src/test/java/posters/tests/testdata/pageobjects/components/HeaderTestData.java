package posters.tests.testdata.pageobjects.components;

public class HeaderTestData
{
    private String topCategory;

    private int resultPosition;
    
    public String getTopCategory()
    {
        return topCategory;
    }
    
    public int getResultPosition()
    {
        return resultPosition;
    }
    
    @Override
    public String toString()
    {
        return "HeaderTestData [topCategory=" + topCategory + ", resultPosition=" + resultPosition + "]";
    }
}