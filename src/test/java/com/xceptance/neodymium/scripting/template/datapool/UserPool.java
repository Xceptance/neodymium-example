package com.xceptance.neodymium.scripting.template.datapool;

import java.util.LinkedList;
import java.util.List;

import com.xceptance.neodymium.datapool.core.DataListPool;
import com.xceptance.neodymium.datapool.core.DataPoolCache;
import com.xceptance.neodymium.scripting.template.selenide.objects.User;
import com.xceptance.neodymium.testdata.GenericFileReader;

public class UserPool extends DataListPool<User>
{
    public UserPool()
    {
        dataList = new LinkedList<>();
        DataPoolCache.getInstance().addDataListProvider(this);

        List<Object[]> data = GenericFileReader.readFile();
        for (Object[] entry : data)
        {
            addEntry(new User((String) entry[0], (String) entry[1], (String) entry[2], (String) entry[3]));
        }
    }
}
