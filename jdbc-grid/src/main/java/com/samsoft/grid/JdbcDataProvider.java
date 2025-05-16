package com.samsoft.grid;

import com.vaadin.flow.data.provider.CallbackDataProvider;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Map;

@Slf4j
@NotThreadSafe
public class JdbcDataProvider extends CallbackDataProvider<Map<String, Object>, String> {

    public JdbcDataProvider(JdbcDataCallback jdbcDataCallback) {
        super(jdbcDataCallback, jdbcDataCallback);
    }

}
