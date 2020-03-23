package tests;

import lib.CoreTestCase;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUiFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;


public class MyListsTests extends CoreTestCase {
    private static final String name_of_folder = "Learning Programming";
    private static final String
        login = "Vovasiktest",
        password = "wikivovasik123";


    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();

        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyNewList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePageObject.waitForTitleElement();

            assertEquals("We are not on the same page after login.",
                    article_title,
                    articlePageObject.getArticleTitle()
            );

            articlePageObject.addArticlesToMySaved();

        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUiFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyList();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
            myListsPageObject.swipeByArticleToDelete(article_title);
        } else {
            myListsPageObject.swipeByArticleToDelete(article_title);
        }
    }

    @Test
    public void testSaveBothArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();

        String article_title = articlePageObject.getArticleTitle();
        String name_of_folder = "EX-5";

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyNewList(name_of_folder);
            articlePageObject.closeArticle();
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeSaveOverlay();
            articlePageObject.closeArticle();
        } else {
            articlePageObject.addArticlesToMySaved();
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            articlePageObject.waitForTitleElement();
            assertEquals("We are not on the same page after login", article_title, articlePageObject.getArticleTitle());
            articlePageObject.addArticlesToMySaved();
        }

        searchPageObject.initSearchInput();

        if (Platform.getInstance().isIOS()) {
            searchPageObject.clearSearchLineText();
        }

        searchPageObject.typeSearchLine("Moon");
        searchPageObject.clickByArticleWithSubstring("rrival of a spacecraft on the surface of the Moon");

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyNewList(name_of_folder);
            articlePageObject.closeArticle();
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeArticle();
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        NavigationUI navigationUI = NavigationUiFactory.get(driver);
        navigationUI.clickMyList();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
            myListsPageObject.swipeByArticleToDelete(article_title);
        } else {
            myListsPageObject.swipeByArticleToDelete(article_title);
        }

        searchPageObject.waitForSearchResult("Moon landing");


        if (Platform.getInstance().isAndroid()) {
            searchPageObject.clickByArticleWithSubstring("Moon landing");
            articlePageObject.waitForTitleElement();
            String saved_title_element = articlePageObject.getArticleTitle();

            assertEquals(
                    "Title does not match: 'Moon landing'",
                    "Moon landing",
                    saved_title_element);
        } else if (Platform.getInstance().isIOS()) {
            String name_saved_title_element = searchPageObject.getAttributeNameFromArticleCell("Moon Landing");

            assertEquals(
                    "Name does not match: 'Moon Landing - Arrival of a spacecraft on the surface of the Moon'",
                    "Moon Landing" + "\n" + "Arrival of a spacecraft on the surface of the Moon",
                    name_saved_title_element);
        } else {
            searchPageObject.clickByItemWithTitle("oon Landing");
            articlePageObject.waitForTitleElement();

            String remained_title_element = articlePageObject.getArticleTitle();
            assertTrue(
                    "Title does not contains 'Moon Landing' string",
                    remained_title_element.equals("Moon Landing")
            );
        }
    }
}