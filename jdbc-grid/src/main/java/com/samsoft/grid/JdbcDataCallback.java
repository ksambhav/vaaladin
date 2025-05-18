package com.samsoft.grid;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.function.ValueProvider;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;

import javax.annotation.concurrent.NotThreadSafe;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@NotThreadSafe
public class JdbcDataCallback implements
        CallbackDataProvider.FetchCallback<Map<String, Object>, String>,
        CallbackDataProvider.CountCallback<Map<String, Object>, String> {

    private final DataSource dataSource;
    private final Grid<Map<String, Object>> grid;
    private final LinkedHashSet<String> columns = new LinkedHashSet<>();
    private final PaginationControls paginationControls;
    @Setter
    private String fqn;
    @Setter
    private Consumer<LinkedHashSet<String>> onColumnChangeHandler;

    public JdbcDataCallback(Grid<Map<String, Object>> grid, DataSource dataSource, String fqn, PaginationControls paginationControls) {
        this.dataSource = dataSource;
        this.fqn = fqn;
        this.grid = grid;
        this.paginationControls = paginationControls;
    }

    @SneakyThrows
    private static LinkedHashSet<String> createColSet(ResultSetMetaData metadata) {
        LinkedHashSet<String> cols = new LinkedHashSet<>(metadata.getColumnCount());
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            cols.add(metadata.getColumnName(i));
        }
        return cols;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    @SneakyThrows
    public Stream<Map<String, Object>> fetch(Query<Map<String, Object>, String> query) {
        if (isBlank(fqn)) {
            return Stream.empty();
        }
        query.getLimit();
        query.getOffset();
        int limit = paginationControls.getPageSize();
        int offset = paginationControls.calculateOffset();
        List<QuerySortOrder> sortOrders = query.getSortOrders();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM ");
        sqlBuilder.append(fqn);
        query.getFilter().ifPresent(f -> {
            if (isNotBlank(f)) {
                sqlBuilder.append(" where ").append(f).append(' ');
            }
        });
        if (sortOrders != null && !sortOrders.isEmpty()) {
            if (sortOrders.size() == 1) {
                sqlBuilder.append(" ORDER BY ").append(sortOrders.get(0).getSorted()).append(" ").append(sortOrders.get(0).getDirection() == SortDirection.ASCENDING ? "asc" : "desc");
            } else {
                sqlBuilder.append(" ORDER BY ");
                for (int i = 0; i < sortOrders.size(); i++) {
                    QuerySortOrder order = sortOrders.get(i);
                    sqlBuilder.append(order.getSorted()).append(" ").append(order.getDirection().name());
                    if (i < sortOrders.size() - 1) {
                        sqlBuilder.append(", ");
                    }
                }
            }
        }
        sqlBuilder.append(" limit ").append(limit).append(" offset ").append(offset);
        var sql = sqlBuilder.toString();
        Stream.Builder<Map<String, Object>> builder = Stream.builder();
        log.debug("Running sql={}, filters = {}", sql, query.getFilter());
        ResultSetMetaData metadata;
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            metadata = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> row = new java.util.HashMap<>(metadata.getColumnCount());
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    String columnName = metadata.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                builder.add(row);
            }
        }
        configureGrid(metadata);
        return builder.build();
    }

    @SneakyThrows
    private void configureGrid(ResultSetMetaData metadata) {
        if (columns.size() != metadata.getColumnCount() || !columns.equals(createColSet(metadata))) {
            columns.clear();
            grid.removeAllColumns();
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                String columnName = metadata.getColumnName(i);
                String columnType = metadata.getColumnTypeName(i);
                columns.add(columnName);
                grid.addColumn((ValueProvider<Map<String, Object>, Object>) stringObjectMap -> stringObjectMap.get(columnName))
                        .setSortProperty(columnName)
                        .setKey(columnName)
                        .setAutoWidth(true)
                        .setTextAlign(columnType.toLowerCase().contains("int") || columnType.toLowerCase().contains("float") ? ColumnTextAlign.END : ColumnTextAlign.START)
                        .setResizable(true)
                        .setHeader(WordUtils.capitalizeFully(columnName.replace('_', ' ')));
            }
            log.info("Grid columns configured with : {} columns", columns.size());
            if (onColumnChangeHandler != null) {
                onColumnChangeHandler.accept(columns);
            }
        }
    }

    @SneakyThrows
    @Override
    public int count(Query<Map<String, Object>, String> query) {
        StringBuilder sql = new StringBuilder("select count(*) as the_count from %s".formatted(fqn));
        query.getFilter().ifPresent(f -> {
            if (isNotBlank(f)) {
                sql.append(" where ").append(f);
            }
        });
        var countQuery = sql.toString();
        log.debug("Count Query = {}", countQuery);
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(countQuery)) {
            if (rs.next()) {
                int itemCount = rs.getInt("the_count");
                paginationControls.recalculatePageCount(itemCount);
                var offset = paginationControls.calculateOffset();
                var limit = paginationControls.getPageSize();
                var remainingItemsCount = itemCount - offset;
                return Math.min(remainingItemsCount, limit);
            } else {
                log.warn("Zero rows for given query");
                return 0;
            }
        }
    }
}
