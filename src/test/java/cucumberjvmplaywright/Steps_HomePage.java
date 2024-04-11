package cucumberjvmplaywright;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Steps_HomePage {

    Driver driverMethods = new Driver();
    Configs configs = new Configs();
    Selectors selectors = new Selectors();

    @And("the number of items in the cart bubble is {string}")
    public void verifyItemNumberInCart(String cartNumber){
        assertEquals(driverMethods.getTextFromElement(selectors.itemNumberInCart), cartNumber);
    }

    @Then("the sort {string} should work correctly")
    public void checkTheSortOrder(String sortType){
        switch (sortType){
            /*
               We get the list of prices from the home page and store it in a list, noSortPrices.
               Then remove the $ sign, convert the prices to floats and sort it Descending
               The result is stored it in an List, sortedPricesHighToLowBYCODE

               We then select the Price (high to low) option from the dropdown, repeat the above step
               We end up with prices sorted by the application in an List, sortedPricesHighToLowBYAPP
               Finally we compare both Lists if they are the same
            */
            case "Price (high to low)" -> {
                List<String> noSortPrices = driverMethods.getAllTextFromAListOfElements(selectors.priceList);
                List<Float> sortedPricesHighToLowBYCODE = noSortPrices.stream().map(s -> Float.parseFloat(s.substring(1))).collect(Collectors.toList()).stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                driverMethods.selectFromDropdownUsingText(selectors.productSort, sortType);

                List<String> priceAfterSortByUI = driverMethods.getAllTextFromAListOfElements(selectors.priceList);
                List<Float> sortedPricesHighToLowBYAPP = priceAfterSortByUI.stream().map(s -> Float.parseFloat(s.substring(1))).collect(Collectors.toList());

                assertEquals(sortedPricesHighToLowBYCODE, sortedPricesHighToLowBYAPP);
            }
            case "Price (low to high)" -> {
                List<String> noSortPrices = driverMethods.getAllTextFromAListOfElements(selectors.priceList);
                List<Float> sortedPricesLowToHighBYCODE = noSortPrices.stream().map(s -> Float.parseFloat(s.substring(1))).collect(Collectors.toList()).stream().sorted().collect(Collectors.toList());
                driverMethods.selectFromDropdownUsingText(selectors.productSort, sortType);

                List<String> priceAfterSortByUI = driverMethods.getAllTextFromAListOfElements(selectors.priceList);
                List<Float> sortedPricesLowToHighBYAPP = priceAfterSortByUI.stream().map(s -> Float.parseFloat(s.substring(1))).collect(Collectors.toList());

                assertEquals(sortedPricesLowToHighBYCODE, sortedPricesLowToHighBYAPP);
            }

            /*
                We get the names of products from the home page and store them in a List, noSortNames
                We then sort it Descending and store it in a List, sortedNamesZtoAByCODE
                We then select the Name (Z to A) option from the dropdown
                We end up with prices sorted by the application in a List, sortedNamesZtoAByAPP
                Finally we compare both Lists if they are the same
            */
            case "Name (Z to A)" -> {
                List<String> noSortNames = driverMethods.getAllTextFromAListOfElements(selectors.productList);
                List<String> sortedNamesZtoAByCODE = noSortNames.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                driverMethods.selectFromDropdownUsingText(selectors.productSort, sortType);

                List<String> sortedNamesZtoAByAPP = driverMethods.getAllTextFromAListOfElements(selectors.productList);
                assertEquals(sortedNamesZtoAByCODE, sortedNamesZtoAByAPP);
            }
            case "Name (A to Z)" -> {
                List<String> noSortNames = driverMethods.getAllTextFromAListOfElements(selectors.productList);
                List<String> sortedNamesZtoAByCODE = noSortNames.stream().sorted().collect(Collectors.toList());
                driverMethods.selectFromDropdownUsingText(selectors.productSort, sortType);

                List<String> sortedNamesZtoAByAPP = driverMethods.getAllTextFromAListOfElements(selectors.productList);
                assertEquals(sortedNamesZtoAByCODE, sortedNamesZtoAByAPP);
            }
            default -> throw new IllegalArgumentException("Incorrect sort option : " + sortType);
        }
    }
}
