package com.companies.house.context;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ScenarioScope
public class ScenarioContext {

    private final Map<String, Object> context = new HashMap<>();

    public void put(String key, Object value) {
        context.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(context.get(key));
    }

    public boolean contains(String key) {
        return context.containsKey(key);
    }

    public Object remove(String key) {
        return context.remove(key);
    }

    public void clear() {
        context.clear();
    }

    /**
     * Retrieve and removes value from context.
     * @param key required key
     * @return value
     */
    public <T> T pop(String key, Class<T> type){
        T value = get(key, type);
        context.remove(key);
        return value;
    }
}
