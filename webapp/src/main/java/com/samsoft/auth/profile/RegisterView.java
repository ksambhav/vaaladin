package com.samsoft.auth.profile;

import com.samsoft.auth.service.UserProfileManager;
import com.samsoft.auth.view.LoginLayout;
import com.samsoft.views.customers.CustomersView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.time.LocalDate;

@PageTitle("Register")
@Route(value = "register", layout = LoginLayout.class)
@AnonymousAllowed
public class RegisterView extends VerticalLayout {
    private final Binder<UserRegistration> binder = new Binder<>(UserRegistration.class);

    public RegisterView(UserProfileManager userProfileService) {
        H3 title = new H3("New User Register");
        TextField fullName = new TextField("Full Name");
        fullName.setRequired(true);
        fullName.setRequiredIndicatorVisible(true);
        fullName.setAutoselect(true);
        fullName.setMinLength(5);
        binder.forField(fullName)
                .asRequired("Full Name is required")
                .bind(UserRegistration::getFullName, UserRegistration::setFullName);
        EmailField email = new EmailField("Email");
        email.setRequiredIndicatorVisible(true);
        binder.forField(email)
                .asRequired("Email is required")
                .withValidator(new EmailValidator("Enter a valid email address"))
                .bind(UserRegistration::getEmail, UserRegistration::setEmail);
        TextField mobile = new TextField("Mobile (Optional)");
        binder.forField(mobile)
                .withValidator(mobileNumber -> mobileNumber.isEmpty() || mobileNumber.matches("\\d{10}"),
                        "Enter a valid 10-digit mobile number")
                .bind(UserRegistration::getMobile, UserRegistration::setMobile);
        DatePicker dateOfBirth = new DatePicker("Date of Birth (Optional)");
        dateOfBirth.setInitialPosition(LocalDate.now().minusYears(20));
        binder.forField(dateOfBirth)
                .bind(UserRegistration::getDateOfBirth, UserRegistration::setDateOfBirth);
        PasswordField password = new PasswordField("Password");
        password.setRequired(true);
        binder.forField(password)
                .asRequired("Password is required")
                .withValidator(pass -> pass.length() >= 8, "Password must be at least 8 characters long")
                .withValidator(pass -> pass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$"),
                        "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
                .bind(UserRegistration::getPassword, UserRegistration::setPassword);
        PasswordField confirmPassword = new PasswordField("Confirm Password");
        confirmPassword.setRequired(true);
        binder.forField(confirmPassword)
                .asRequired("Confirm Password is required")
                .withValidator(confirmPass -> confirmPass.equals(password.getValue()), "Passwords must match")
                .bind(UserRegistration::getConfirmPassword, UserRegistration::setConfirmPassword);
        Button registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClickListener(event -> {
            try {
                UserRegistration user = new UserRegistration();
                binder.writeBean(user);
                userProfileService.register(user);
                Notification.show("Registration successful for " + user.getFullName());
                UI.getCurrent().navigate(CustomersView.class);
            } catch (ValidationException e) {
                Notification.show("Please fix the errors and try again", 3000, Notification.Position.MIDDLE, false);
            }
        });
        FormLayout formLayout = new FormLayout(fullName, email, mobile, dateOfBirth, password, confirmPassword);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 2) // Two columns layout
        );
        add(title, formLayout, registerButton);
        setAlignItems(Alignment.CENTER);
        setMaxWidth(25, Unit.PERCENTAGE);
    }

}
