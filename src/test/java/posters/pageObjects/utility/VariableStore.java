/**
 * 
 */
package posters.pageObjects.utility;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pfotenhauer
 */
public class VariableStore
{
    private static class LocalMyCache
    {
        final Map<String, Object> map = new HashMap<String, Object>();

        Object get(String key)
        {
            Object val = map.get(key);
            return val;
        }

        void put(String key, Object value)
        {
            map.put(key, value);
        }

        Map<String, Object> getMap()
        {
            return map;
        }

    }

    private final ThreadLocal<LocalMyCache> localCaches = new ThreadLocal<LocalMyCache>()
    {
        protected LocalMyCache initialValue()
        {
            return new LocalMyCache();
        }
    };

    public Object get(String key)
    {
        return localCaches.get().get(key);
    }

    public void put(String key, Object value)
    {
        localCaches.get().put(key, value);
    }

    public Map<String, Object> getMap()
    {
        return localCaches.get().getMap();
    }
}
