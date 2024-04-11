package cucumberjvmplaywright;

import java.util.List;

public class Driver {

    public void loadAUrl(String url){
        Hooks.page.navigate(url);
    }

    public String getTextFromElement(String element){
        return Hooks.page.locator(element).textContent();
    }

    public void clickButton(String element){
        Hooks.page.locator(element).click();
    }

    public void typeText(String textToType, String element){
        Hooks.page.locator(element).fill(textToType);
    }

    public boolean elementVisibleOrNot(String element){
        return Hooks.page.locator(element).isVisible();
    }

    public String getSpecificTextFromAListOfElements(String element, Integer index){
        return Hooks.page.locator(element).nth(index).textContent();
    }

    public List<String> getAllTextFromAListOfElements(String element) {
        return Hooks.page.locator(element).allTextContents();
    }

    public void selectFromDropdownUsingText(String element, String selectOptionInText) {
        Hooks.page.locator(element).selectOption(selectOptionInText);
    }

    public String getTheCurrentURL(){
        return Hooks.page.url();
    }

    public String getURLFromANewTab(String buttonToBeClicked){
        return Hooks.page.waitForPopup(() -> {
            clickButton(buttonToBeClicked);
        }).url();
    }

}
