package com.samsoft.grid;

import com.vaadin.flow.data.provider.CallbackDataProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class JdbcDataProvider extends CallbackDataProvider<Map<String, Object>, String> {

    public JdbcDataProvider(JdbcDataCallback jdbcDataCallback) {
        super(jdbcDataCallback, jdbcDataCallback);
    }

}
