/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import org.openqa.selenium.By;

import com.xceptance.neodymium.scripting.template.selenide.page.PCategory;

/**
 * @author pfotenhauer
 */
public class CTopNav extends BasicComponent
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.component.BasicComponent#isComponentAvailable()
     */
    @Override
    protected boolean isComponentAvailable()
    {
        return $("#categoryMenu").exists();
    }

    /**
     * @return
     */
    public PCategory clickCategory(String categoryName)
    {
        $(By.linkText(categoryName)).click();
        return page(PCategory.class);
    }

    public String getSubCategoryNameByIndex(int categoryIndex, int subCategoryIndex)
    {
        return $("#categoryMenu > ul > li:nth-of-type(" + categoryIndex
                 + ") ul.dropdown-menu li:nth-of-type(" + subCategoryIndex + ") a").attr("title");
    }

    public PCategory clickSubCategoryByIndex(int categoryIndex, int subCategoryIndex)
    {
        // Open the category page
        $("#categoryMenu > ul > li:nth-of-type(" + categoryIndex + ") a").hover();
        // Clicks the subcategory with index @{subcategoryIndex} belonging to the category with index @{categoryIndex}
        $("#categoryMenu > ul > li:nth-of-type(" + categoryIndex + ") ul.dropdown-menu li:nth-of-type(" + subCategoryIndex + ") a").click();
        return page(PCategory.class);
    }
}
