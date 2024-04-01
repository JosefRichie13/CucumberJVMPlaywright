package cucumberjvmplaywright;

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

}
