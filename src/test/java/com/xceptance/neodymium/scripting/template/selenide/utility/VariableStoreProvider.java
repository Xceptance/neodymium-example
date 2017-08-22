package com.xceptance.neodymium.scripting.template.selenide.utility;

import org.openqa.selenium.NotFoundException;

public class VariableStoreProvider
{

    private VariableStoreProvider()
    {
    }

    public static VariableStore getInstance()
    {
        return Singleton_Holder.INSTANCE;
    }

    private static class Singleton_Holder
    {
        private static final VariableStore INSTANCE = new VariableStore();
    }

    public static Object get(String key)
    {
        Object object = getInstance().get(key);
        if (object == null)
        {
            throw new NotFoundException("Entry with key: '" + key + "' not included in the variable store.");
        }
        return object;
    }

    public static void put(String key, Object value)
    {
        getInstance().put(key, value);
    }
}
