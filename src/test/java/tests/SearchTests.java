package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase
{

    @Test
    public void testSearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");

    }


    @Test
    public void testCancelSearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();

    }


    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        String search_line = "Linkin Park discography";

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }


    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        String search_line = "zxcwartqdfgss";

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testFindArticleTabSearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();

        String search_title = searchPageObject.getSearchTitle();

        assertEquals(
                "We see unexpected default title!",
                "Search…",
                search_title
        );
    }

    @Test
    public void testFindSomeResults()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Sunlight");
        searchPageObject.waitForSearchResult("Sunlight");
        searchPageObject.waitForSearchResult("Sunlight Foundation");
        searchPageObject.waitForSearchResult("Sunlight (cleaning product)");
        searchPageObject.clearSearchLineText();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testFindSomeResultsWithSameWord()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Test");
        searchPageObject.waitSomeSearchResult(0, "Test");
        searchPageObject.waitSomeSearchResult(1, "Test");
        searchPageObject.waitSomeSearchResult(2, "Test");
    }

    @Test
    public void testFindResultByTitleAndDescription()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        String search_line = "Russia";
        String title_1 = "Russia";
        String substring_1 = "Transcontinental country in Eastern Europe and Northern Asia";
        String title_2 = "Russian language";
        String substring_2 = "East Slavic language";
        String title_3 = "Russian interference in the 2016 United States elections";
        String substring_3 = "Foreign meddling campaign";


        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);

        if (Platform.getInstance().isAndroid()) {
            searchPageObject.waitForElementByTitleAndDescription(title_1, substring_1);
            searchPageObject.waitForElementByTitleAndDescription(title_2, substring_2);
            searchPageObject.waitForElementByTitleAndDescription(title_3, substring_3);
        } else {
            searchPageObject.checkAttributeNameByTitleAndDescription(title_1, substring_1);
            searchPageObject.checkAttributeNameByTitleAndDescription(title_2, substring_2);
            searchPageObject.checkAttributeNameByTitleAndDescription(title_3, substring_3);
        }

    }
}
