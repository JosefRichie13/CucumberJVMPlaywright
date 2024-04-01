package cucumberjvmplaywright;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class Steps_Login {

    Driver driverMethods = new Driver();
    Configs configs = new Configs();
    Selectors selectors = new Selectors();

    @Given("I open the web page")
    public void openTheWebPage(){
        driverMethods.loadAUrl(configs.mainURL);
    }

    @When("I login as a {string} user")
    public void loginAsAUser(String userType){
        switch (userType){
            case "standard" -> {
                driverMethods.typeText(configs.validUser, selectors.userName);
                driverMethods.typeText(configs.password, selectors.password);
            }
            case "locked" -> {
                driverMethods.typeText(configs.lockedUser, selectors.userName);
                driverMethods.typeText(configs.password, selectors.password);
            }
            case "no_username" -> driverMethods.typeText(configs.password, selectors.password);
            case "no_password" -> driverMethods.typeText(configs.validUser, selectors.userName);
            case "wrong_username" -> {
                driverMethods.typeText(configs.wrongUser, selectors.userName);
                driverMethods.typeText(configs.password, selectors.password);
            }
            case "wrong_password" -> {
                driverMethods.typeText(configs.validUser, selectors.userName);
                driverMethods.typeText(configs.wrongPassword, selectors.password);
            }
            default -> throw new IllegalArgumentException("Incorrect User Type : " + userType);
        }
        driverMethods.clickButton(selectors.loginButton);
    }

    @Then("I should see {string} in the {string}")
    public void shouldSeeMessageInPage(String message, String page){
        switch (page){
            case "homepage" -> {
                assertEquals(driverMethods.getTextFromElement(selectors.homepageTitle), message);
                assertFalse(driverMethods.elementVisibleOrNot(selectors.loginButton));
            }
            case "loginpage" -> {
                assertEquals(driverMethods.getTextFromElement(selectors.loginpageTitle), message);
                assertTrue(driverMethods.elementVisibleOrNot(selectors.loginButton));
            }
            default -> throw new IllegalArgumentException("Incorrect Page : " + page);
        }
    }

}
