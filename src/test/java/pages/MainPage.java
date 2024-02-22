package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class MainPage extends BasePage{
    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = ".restart-button")
    WebElement newGame;

    @FindBy(css = ".tile-new")
    List<WebElement> newTiles;

    @FindBy(css = "body")
    WebElement board;

    @FindBy(css = ".score-container")
    WebElement score;

    @FindBy(xpath = "//p[text()='Game over!']")
    List<WebElement> gameOver;

    @FindBy(css = ".tile-container>div>div")
    List<WebElement> tilesWithNumbers;

    public void startNewGame() throws Exception {
        click(newGame, "New Game button");
    }

    public void checkNewTileCount(String tileNum){
        assertEQ(String.valueOf(newTiles.size()),tileNum,"Tile Count" );
    }

    public void play (int movesNum) throws Exception {
        Keys [] moves = {Keys.ARROW_UP,Keys.ARROW_DOWN,Keys.ARROW_LEFT,Keys.ARROW_RIGHT};
        Random random = new Random();
        for(int i=1; i<=movesNum;i++){
            typeText(board,moves[random.nextInt(moves.length)],"Move "+moves[random.nextInt(moves.length)]+ " - "+i);
        }
    }

    public void checkScore(){
        Assert.assertTrue(!score.getText().equals("0")&&!score.getText().equals(""));
    }

    public void playUntilGameOver (int movesNum) throws Exception {
        Keys [] moves = {Keys.ARROW_UP,Keys.ARROW_DOWN,Keys.ARROW_LEFT,Keys.ARROW_RIGHT};
        Random random = new Random();
        while(true){
            for(int i=1; i<=movesNum;i++){
                typeText(board,moves[random.nextInt(moves.length)],"Move "+moves[random.nextInt(moves.length)]+ " - "+i);
            }
            if(gameOver.size()>0&&gameOver.get(0).isDisplayed()){
                System.out.println(getCurrentTimeAndDate() + " Game Over!");
                break;
            }
        }

    }

    public void playUntilGameOver (int movesNum,String goal) throws Exception {
        Keys [] moves = {Keys.ARROW_UP,Keys.ARROW_DOWN,Keys.ARROW_LEFT,Keys.ARROW_RIGHT};
        Random random = new Random();
        System.out.println(getCurrentTimeAndDate()+" Game Started!");

        main:
        while(true){
            for(int i=1; i<=movesNum;i++){
                typeText(board,moves[random.nextInt(moves.length)],"Move "+moves[random.nextInt(moves.length)]+ " - "+i);

            }
            for(int i=0;i<tilesWithNumbers.size();i++){
                if(tilesWithNumbers.get(i).getText().equals(goal)){
                    System.out.println(getCurrentTimeAndDate() + " Goal of "+goal+" has been reached!");
                    break main;
                }
            }

        }

    }
}
