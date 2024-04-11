package cucumberjvmplaywright;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Steps_ProductCheckout {

    Driver driverMethods = new Driver();
    Configs configs = new Configs();
    Selectors selectors = new Selectors();

    @And("I add {string} to the cart")
    public void addToCart(String productToAdd){
        addOrRemoveProductFromCart(productToAdd);
    }

    @And("I remove {string} from the cart")
    public void removeFromTheCart(String productToRemove){
        addOrRemoveProductFromCart(productToRemove);
    }

    public void addOrRemoveProductFromCart(String productType){
        switch (productType){
            case "Sauce Labs Backpack" -> driverMethods.clickButton(selectors.productBackpack);
            case "Sauce Labs Bike Light" -> driverMethods.clickButton(selectors.productBikelight);
            case "Sauce Labs Bolt T-Shirt" -> driverMethods.clickButton(selectors.productTshirt);
            case "Sauce Labs Fleece Jacket" -> driverMethods.clickButton(selectors.productJacket);
            case "Sauce Labs Onesie" -> driverMethods.clickButton(selectors.productOnesie);
            case "Test.allTheThings() T-Shirt (Red)" -> driverMethods.clickButton(selectors.productTshirtRed);
            default -> throw new IllegalArgumentException("Unavailable product : " + productType);
        }
    }

    @And("I click on the cart")
    public void clickOnTheCart(){
        driverMethods.clickButton(selectors.cart);
    }

    @And("I checkout")
    public void checkout(){
        driverMethods.clickButton(selectors.checkout);
    }

    @And("I enter my information to continue")
    public void enterMyInfoAndContinue(DataTable table){
        driverMethods.typeText(table.asMaps().get(0).get("FirstName"), selectors.firstName);
        driverMethods.typeText(table.asMaps().get(0).get("LastName"), selectors.lastName);
        driverMethods.typeText(table.asMaps().get(0).get("Zip"), selectors.zipCode);
        driverMethods.clickButton(selectors.continueButton);
    }

    @And("I confirm my order")
    public void confirmMyOrder(){
        driverMethods.clickButton(selectors.finishButton);
    }

    @Then("I should see {string} after the order is placed")
    public void seeMessageAfterOrderPlacement(String message){
        assertEquals(driverMethods.getTextFromElement(selectors.checkoutBanner), message);
    }

    @Then("I should see the tax calculated at 8 percent")
    public void verifyTheTaxCalculation() {
        float taxCalculatedByAPP = Float.parseFloat(driverMethods.getTextFromElement(selectors.taxCalculated).replaceAll("[^\\d.]", ""));
        float taxCalculatedByCODE = Math.round(Float.parseFloat(driverMethods.getTextFromElement(selectors.subtotal).replaceAll("[^\\d.]", "")) * 0.08 * 100f) / 100f;
        assertEquals(taxCalculatedByAPP, taxCalculatedByCODE, 0);

        float totalCalculatedByAPP = Float.parseFloat(driverMethods.getTextFromElement(selectors.fullTotal).replaceAll("[^\\d.]", ""));
        float totalCalculatedByCODE = Float.parseFloat(driverMethods.getTextFromElement(selectors.subtotal).replaceAll("[^\\d.]", "")) + taxCalculatedByAPP;
        assertEquals(totalCalculatedByAPP, totalCalculatedByCODE, 0);
    }

    @Then("I should see the individual items total correctly")
    public void verifyIndividualItemsTotal(){
        List<String> individualPrices = driverMethods.getAllTextFromAListOfElements(selectors.priceList);
        List<String> individualPricesInFloatWithoutSign = individualPrices.stream().map(s -> s.substring(1)).collect(Collectors.toList());
        float sumCalculatedByCODE = individualPricesInFloatWithoutSign.stream().map(Float::valueOf).reduce(0f, Float::sum);

        float sumCalculatedByAPP = Float.parseFloat(driverMethods.getTextFromElement(selectors.subtotal).replaceAll("[^\\d.]", ""));

        assertEquals(sumCalculatedByCODE, sumCalculatedByAPP, 0);
    }
}
