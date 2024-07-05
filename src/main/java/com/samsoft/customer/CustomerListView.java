package com.samsoft.customer;

import com.samsoft.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Slf4j
@PageTitle("CustomerList")
@Menu(icon = "line-awesome/svg/columns-solid.svg", order = 0)
//@Route(value = "/customers/:customerProfileID?/:action?(edit)", layout = MainView.class)
//@Route(value = "/customers", layout = MainView.class)
@Route(value = "/:customerProfileID?/:action?(edit)", layout = MainView.class)
//@RouteAlias(value = "/customers")
@PermitAll
public class CustomerListView extends Div implements BeforeEnterObserver {

    private final String CUSTOMERPROFILE_ID = "customerProfileID";
    private final String CUSTOMERPROFILE_EDIT_ROUTE_TEMPLATE = "/%s/edit";

    private final Grid<CustomerProfile> grid = new Grid<>(CustomerProfile.class, false);
    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final BeanValidationBinder<CustomerProfile> binder;
    private final CustomerProfileService customerProfileService;
    private TextField fullName;
    private TextField email;
    private TextField mobile;
    private DatePicker dateOfBirth;
    private TextField gender;
    private CustomerProfile customerProfile;

    public CustomerListView(CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
        addClassNames("customer-list-view");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);
        // Configure Grid
        grid.addColumn("fullName").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("mobile").setAutoWidth(true);
        grid.addColumn("dateOfBirth").setAutoWidth(true);
        grid.addColumn("gender").setAutoWidth(true);
        grid.setItems(query -> customerProfileService.list(
                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(CUSTOMERPROFILE_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(CustomerListView.class);
            }
        });
        // Configure Form
        binder = new BeanValidationBinder<>(CustomerProfile.class);
        // Bind fields. This is where you'd define e.g. validation rules
        binder.bindInstanceFields(this);
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
        save.addClickListener(e -> {
            try {
                if (this.customerProfile == null) {
                    this.customerProfile = new CustomerProfile();
                }
                binder.writeBean(this.customerProfile);
                customerProfileService.update(this.customerProfile);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(CustomerListView.class);
            } catch (NullPointerException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Notification.Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> customerProfileId = event.getRouteParameters().get(CUSTOMERPROFILE_ID).map(Long::parseLong);
        if (customerProfileId.isPresent()) {
            Optional<CustomerProfile> customerProfileFromBackend = customerProfileService.get(customerProfileId.get());
            if (customerProfileFromBackend.isPresent()) {
                populateForm(customerProfileFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested customerProfile was not found, ID = %s", customerProfileId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(CustomerListView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");
        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);
        FormLayout formLayout = new FormLayout();
        fullName = new TextField("Full Name");
        email = new TextField("Email");
        mobile = new TextField("Mobile");
        dateOfBirth = new DatePicker("Date Of Birth");
        gender = new TextField("Gender");
        formLayout.add(fullName, email, mobile, dateOfBirth, gender);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);
        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(CustomerProfile value) {
        this.customerProfile = value;
        binder.readBean(this.customerProfile);

    }
}
