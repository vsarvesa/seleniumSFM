package pages;

import actions.ElementActions;
import org.openqa.selenium.By;
import utils.WaitStrategy;

import java.text.MessageFormat;

public class RegisterPage extends BasePage{

    private static final String FIRST_NAME ="firstName";
    private static final String LAST_NAME="lastName";
    private static final String EMAIL="userEmail";
    private static final String PHONE_NUMBER ="userMobile";
    private static final String OCCUPATION ="custom-select";
    private static final String PASSWORD="userPassword";
    private static final String CONFIRM_PASSWORD="#confirmPassword";
    private static final String GENDER="//input[@value=''{0}'']";
    private static final String CONSCENT="//input[@formcontrolname='required' and @type='checkbox']";
    private static final String REGISTER = "login";
    private static final String SUCCESSMSG = "//*[text()='Account Created Successfully']";
    private final By firstName;
    private final By lastName;
    private final By email;
    private final By phonenumber;
    private final By occupation;
    private final By passowrd;
    private final By confirmPassword;
    private final By conscent;
    private final By registerbtn;
    private final By successMsg;

    public RegisterPage(){
        firstName = By.id(FIRST_NAME);
        lastName = By.id(LAST_NAME);
        email = By.id(EMAIL);
        phonenumber = By.id(PHONE_NUMBER);
        occupation = By.className(OCCUPATION);
        passowrd = By.id(PASSWORD);
        confirmPassword = By.cssSelector(CONFIRM_PASSWORD);
        conscent = By.xpath(CONSCENT);
        registerbtn = By.name(REGISTER);
        successMsg = By.xpath(SUCCESSMSG);
    }

    public RegisterPage enterFirstName(String fName) {
        ElementActions.type(firstName, fName, "First Name Field");
        return this;
    }

    public RegisterPage enterLastName(String lName) {
        ElementActions.type(lastName, lName, "Last Name Field");
        return this;
    }

    public RegisterPage enterEmail(String emailAddress) {
        ElementActions.type(email, emailAddress, "Email Field");
        return this;
    }

    public RegisterPage enterPhoneNumber(String phone) {
        ElementActions.type(phonenumber, phone, "Phone Number Field");
        return this;
    }

    public RegisterPage selectOccupation(String visibleText) {
        ElementActions.selectByVisibleText(occupation, visibleText, "Occupation Dropdown");
        return this;
    }

    public RegisterPage enterPassword(String pwd) {
        ElementActions.type(passowrd, pwd, "Password Field");
        return this;
    }

    public RegisterPage enterConfirmPassword(String pwd) {
        ElementActions.type(confirmPassword, pwd, "Confirm Password Field");
        return this;
    }

    public RegisterPage selectGender(String genderName) {
        // Dynamically create the locator at runtime using MessageFormat
        String formattedXpath = MessageFormat.format(GENDER, genderName);
        By dynamicGenderBtn = By.xpath(formattedXpath);
        
        ElementActions.selectRadioButton(dynamicGenderBtn, genderName + " Gender Radio Button");
        return this;
    }

    public RegisterPage acceptConsent() {
        ElementActions.checkCheckbox(conscent, "Consent Checkbox");
        return this;
    }

    public RegisterPage clickRegister() {
        ElementActions.click(registerbtn, "Register Button");
        return this;
    }
    public boolean successMsgIsDisplayed(){
        try {
            // Explicitly wait up to 10 seconds (your configured timeout) for the success message to render
            return WaitStrategy.waitForVisibility(successMsg).isDisplayed();
        } catch (Exception e) {
            // If it never appears after the timeout, return false
            return false;
        }
    }
}
