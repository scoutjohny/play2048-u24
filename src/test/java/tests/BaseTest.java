package tests;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import selenium_core.DriverManager;
import selenium_core.DriverManagerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {
    DriverManager driverManager;
    WebDriver driver;
    String path = "src/results/screenshots/";

    public void init (String browser) throws Exception {
        driverManager = DriverManagerFactory.getDriverManager(browser);
        driver = driverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public void quit(){
        driverManager.quitDriver();
    }

    public void openApp (String env) throws Exception {
        env = env.toUpperCase();
        switch (env){
            case "PROD":{
                driver.get("https://play2048.co/");
            }
            break;
            default: throw new Exception("Environment: "+env+" is not supported!");
        }
    }

    public void takeScreenshot(String fileName) throws IOException {
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("results/screenshots/"+fileName+".png"));
    }

    public void reportScreenshot(String fileName, String description) throws IOException {
        takeScreenshot(fileName);
        Path content = Paths.get("results/screenshots/"+fileName+".png");
        try(InputStream is = Files.newInputStream(content)){
            Allure.addAttachment(description,is);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
