package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSSearchPageObject extends SearchPageObject
{
    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath:///XCUIElementTypeNavigationBar[@name='Wikipedia, scroll to top of Explore']";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{SUBSTRING}')]";
        SEARCH_RESULT_BY_TITLE_AND_SUBSTRING_TPL = "xpath://*[contains(@text, '{TITLE}')]/following-sibling::*[contains(@text, '{SUBSTRING}')]/parent::*";
        SEARCH_RESULT_BY_INDEX_CONTAINS_SUBSTRING_TPL = "xpath://*[@index = '{NUMBER}']//*[contains(@text,'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        SEARCH_TITLE = "id:org.wikipedia:id/search_src_text";
        SEARCH_CLEAN_BUTTON = "id:clear mini";
    }

    public iOSSearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
