package com.samsoft.grid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Set;

@Slf4j
public class JdbcGrid extends VerticalLayout {

    final Grid<Map<String, Object>> grid = new Grid<>();
    @Setter
    private String fqn;

    public JdbcGrid(DataSource dataSource, String fqn) {
        setSizeFull();
        setPadding(false);
        setMargin(false);
        this.fqn = fqn;
        JdbcDataCallback jdbcDataCallback = new JdbcDataCallback(grid, dataSource, fqn);
        jdbcDataCallback.setFqn(fqn);
        JdbcDataProvider dataProvider = new JdbcDataProvider(jdbcDataCallback);
        grid.setDataProvider(dataProvider);
        grid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);
        Button refresh = new Button("Refresh", e -> grid.getDataProvider().refreshAll());
        refresh.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        horizontalLayout.addClassNames(LumoUtility.Padding.Left.SMALL);
        horizontalLayout.setWidthFull();
        MultiSelectComboBox<String> columnFilter = columnSelector();
        jdbcDataCallback.setOnColumnChangeHandler(items -> {
            columnFilter.deselectAll();
            columnFilter.setItems(items);
        });
        horizontalLayout.addToEnd(refresh, columnFilter);
        TextField search = new TextField("Search");
        search.setPlaceholder("e.g fund_id ilike 'abcd%' and tran_date > '2023-01-01'");
        search.setSizeFull();
        horizontalLayout.addToStart(search);
        add(horizontalLayout, grid);
    }

    private MultiSelectComboBox<String> columnSelector() {
        MultiSelectComboBox<String> columnFilter = new MultiSelectComboBox<>("Columns");
        columnFilter.setClearButtonVisible(true);
        columnFilter.setAllowCustomValue(false);
        columnFilter.setSelectedItemsOnTop(true);
        columnFilter.addValueChangeListener(e -> {
            Set<String> selectedCols = e.getValue();
            if (selectedCols == null || selectedCols.isEmpty()) {
                log.debug("Show all cols");
                grid.getColumns().forEach(c -> c.setVisible(true));
            } else {
                log.debug("Will show {} cols", selectedCols);
                grid.getColumns().forEach(c -> c.setVisible(selectedCols.contains(c.getKey())));
            }
        });
        return columnFilter;
    }

    public void refreshAll() {
        this.grid.getDataProvider().refreshAll();
    }
}
