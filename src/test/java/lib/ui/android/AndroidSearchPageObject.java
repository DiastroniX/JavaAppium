package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidSearchPageObject extends SearchPageObject
{
    static {
            SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]";
            SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]";
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[contains(@text(), '{SUBSTRING}')]";
            SEARCH_RESULT_BY_TITLE_AND_SUBSTRING_TPL = "xpath://*[contains(@text, '{TITLE}')]/following-sibling::*[contains(@text, '{SUBSTRING}')]/parent::*";
            SEARCH_RESULT_BY_INDEX_CONTAINS_SUBSTRING_TPL = "xpath://*[@index = '{NUMBER}']//*[contains(@text,'{SUBSTRING}')]";
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id = 'org.wikipedia:id/search_results_list']/*[@resource-id = 'org.wikipedia:id/page_list_item_container']";
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text = 'No results found']";
            SEARCH_TITLE = "id:org.wikipedia:id/search_src_text";
    }

    public AndroidSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
