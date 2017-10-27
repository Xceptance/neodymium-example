package com.xceptance.neodymium.scripting.template.selenide.component;

import org.junit.Assert;

public abstract class AbstractComponent
{
    public void isComponentAvailable()
    {
        Assert.assertTrue("The component is not available on the current page!", exists());
    }

    abstract protected boolean exists();
}
