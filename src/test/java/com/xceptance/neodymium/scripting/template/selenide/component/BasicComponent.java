package com.xceptance.neodymium.scripting.template.selenide.component;

import org.junit.Assert;

public abstract class BasicComponent
{

    public BasicComponent()
    {
        validateComponent();
    }

    private void validateComponent()
    {
        Assert.assertTrue("The component is not available on the current page!", isComponentAvailable());
    }

    abstract protected boolean isComponentAvailable();
}
