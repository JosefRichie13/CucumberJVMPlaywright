package cucumberjvmplaywright;

import io.cucumber.java.en.And;

import static org.junit.Assert.*;

public class Steps_Footer {

    Driver driverMethods = new Driver();
    Configs configs = new Configs();
    Selectors selectors = new Selectors();

    @And("I confirm that the footer is {string}")
    public void verifyFooterStatus(String visibleStatus){
        if(visibleStatus.equals("not visible")){
            assertFalse(driverMethods.elementVisibleOrNot(selectors.footer));
        }
        else {
            assertTrue(driverMethods.elementVisibleOrNot(selectors.footer));
        }
    }

    @And("the {string} icon in the footer should open {string}")
    public void clickOnTheFooterIcons(String footerIcon, String redirectURL) throws InterruptedException {
        switch (footerIcon){
            case "Twitter" -> {
                String newURL = driverMethods.getURLFromANewTab(selectors.footerTwitter);
                assertEquals(newURL, redirectURL);
            }
            case "Facebook" -> {
                String newURL = driverMethods.getURLFromANewTab(selectors.footerFacebook);
                assertEquals(newURL, redirectURL);
            }
            case "LinkedIn" -> {
                String newURL = driverMethods.getURLFromANewTab(selectors.footerLinkedin);
                assertEquals(newURL, redirectURL);
            }
            default -> throw new IllegalArgumentException("Incorrect Footer icon : " + footerIcon);
        }
        Thread.sleep(3000);
    }
}
