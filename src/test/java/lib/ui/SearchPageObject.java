package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.junit.Assert;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_INPUT_BY_TITLE_TPL,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_BY_TITLE_AND_SUBSTRING_TPL,
            SEARCH_RESULT_BY_INDEX_CONTAINS_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_TITLE,
            SEARCH_CLEAN_BUTTON;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElementByText(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByID(int index, String substring) {
        return SEARCH_RESULT_BY_INDEX_CONTAINS_SUBSTRING_TPL.replace("{NUMBER}", String.valueOf(index)).replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndDescription(String title, String description) {
        return SEARCH_RESULT_BY_TITLE_AND_SUBSTRING_TPL.replace("{TITLE}", title).replace("{SUBSTRING}", description);
    }


    /* TEMPLATES METHODS */

    public void initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElementByText(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button!", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultElementSearchByTitle(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    private static String getResultElementSearchByTitle(String title)
    {
        return SEARCH_INPUT_BY_TITLE_TPL.replace("{TITLE}", title);
    }

    public void clickByItemWithTitle(String title)
    {
        String search_result_xpath= getResultElementSearchByTitle(title);
        this.waitForElementAndClick(search_result_xpath,"Cannot find and click search result with title" + title, 10);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    public WebElement waitForSearchTitleElement() {
        return this.waitForElementPresent(SEARCH_TITLE, "Cannot find search title on page!", 15);
    }

    public String getSearchTitle() {
        WebElement title_element = waitForSearchTitleElement();
        return title_element.getAttribute("text");
    }

    public void clearSearchLineText() {
        if (Platform.getInstance().isAndroid()) {
            this.waitForElementAndClear(SEARCH_TITLE, "Cannot find search field", 5);
        } else {
            this.waitForElementAndClick(SEARCH_CLEAN_BUTTON, "Cannot find and click clean button", 5);
        }

    }

    public void waitSomeSearchResult(int index, String substring) {
        String search_result_xpath = getResultSearchElementByID(index, substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String search_result_xpath_by_title_and_dsc = getResultSearchElementByTitleAndDescription(title, description);
        this.waitForElementPresent(search_result_xpath_by_title_and_dsc,
                "Cannot find search result with title " + title + " and description " + description,
                15);
    }

    public String getAttributeNameFromArticleCell(String substring) {
        String name_xpath = getResultSearchElementByText(substring);
        WebElement name_of_saved_element = this.waitForElementPresent(name_xpath, "Cannot find article with xpath");

        return name_of_saved_element.getAttribute("name");
    }

    /*IOS method*/
    public void checkAttributeNameByTitleAndDescription(String title, String description)
    {
        String name_xpath = getResultSearchElementByText(title);
        WebElement element = this.waitForElementPresent(name_xpath,"Cannot find article with xpath");

        String name_of_search_result = element.getAttribute("name");
        Assert.assertTrue(
                "Cannot find search result with title " + title + "and description " + description,
                name_of_search_result.contains(title)&&name_of_search_result.contains(description));
    }
}

