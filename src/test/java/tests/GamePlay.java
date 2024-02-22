package tests;

import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.MainPage;

public class GamePlay extends BaseTest{

    @BeforeMethod
    @Parameters({"browser","env"})
    public void setup(String browser, String env) throws Exception {
        init(browser);
        openApp(env);
    }

    @AfterMethod
    @Parameters({"quit"})
    public void tearDown(String quit){
        if(quit.equalsIgnoreCase("yes")){
            quit();
        }
    }

    @Test(enabled = false)
    public void newGame() throws Exception {
        MainPage mainPage = new MainPage(driver);
        mainPage.startNewGame();
        driver.switchTo().alert().accept();
        mainPage.checkNewTileCount("2");
        mainPage.startNewGame();
        driver.switchTo().alert().accept();
        mainPage.checkNewTileCount("2");
    }

    @Test(enabled = false)
    public void playGame() throws Exception {
        MainPage mainPage = new MainPage(driver);
        mainPage.play(10);
        mainPage.checkScore();
    }

    @Test(enabled = false)
    public void fullGame() throws Exception {
        MainPage mainPage = new MainPage(driver);
        mainPage.playUntilGameOver(50);
    }

    @Test
    public void fullGameWin() throws Exception {
        MainPage mainPage = new MainPage(driver);
        mainPage.playUntilGameOver(50,"128");
    }
}
