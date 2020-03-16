package projectSelenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public  class LoginTest  {
	public WebDriver driver ; 
	WebDriverWait wait;
	WebElement eleLogin,eleEmail,elePwd,eleText,eleProfile,eleUser,eleMyProfile,eleLogOut;
	String expectedTitle="CLICKDOC";
	String str="Bitte überprüfen Sie Ihre E-Mail-Adresse, Passwort und probieren Sie es noch einmal.";
	String path="C:\\Users\\Tony\\eclipse-workspace\\CGM";  //Give the path of chromedriver.exe file here

	@Test(priority = 0)

	public void start() throws InterruptedException {
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(400, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.clickdoc.de/cms-de/");
		wait=new WebDriverWait(driver, 30);  
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}
	//Send email address
	@Test(priority = 1)
	public void profileWindow() throws InterruptedException ,ClassNotFoundException  {
		Thread.sleep(1000);
		eleProfile=driver.findElement(By.xpath("//div//nav//li[8]//a[1]"));
		eleProfile.click();
		Thread.sleep(1000);


		// To handle all new Login frame.				
		WebElement element = driver.findElement(By.id("iframeDialog"));
		driver.switchTo().frame(element);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/div/main/app-login/div/div[1]/div/div/div[2]/div[1]/form/mat-form-field[1]/div/div[1]/div")));
		eleEmail=driver.findElement(By.id("mat-input-0"));
		elePwd=driver.findElement(By.id("mat-input-1"));
		eleLogin=driver.findElement(By.xpath("/html/body/app-root/div/div/main/app-login/div/div[1]/div/div/div[2]/div[2]/div[2]/button"));
		//WebElement eleClose=driver.findElement(By.xpath("//span[@class='iframe-dialog-close icon icon-CO_close']"));
		WebElement eleForgotPwd=driver.findElement(By.xpath("/html/body/app-root/div/div/main/app-login/div/div[1]/div/div/div[2]/div[1]/div/span"));
		WebElement eleRegister=driver.findElement(By.xpath("/html/body/app-root/div/div/main/app-login/div/div[1]/div/div/div[2]/div[2]/div[1]/button"));

		Assert.assertEquals(eleEmail.isDisplayed(),true);
		Assert.assertEquals(elePwd.isDisplayed(),true);
		Assert.assertEquals(eleLogin.isDisplayed(),true);
		//Assert.assertEquals(true, eleClose.isDisplayed());
		Assert.assertEquals(eleForgotPwd.isDisplayed(),true);
		Assert.assertEquals(eleRegister.isDisplayed(),true);
	}

	//Login with no inputs
	@Test(priority = 2)
	public void loginTrial() throws InterruptedException   {
		eleLogin.click(); 
		Thread.sleep(1000);//Login with no inputs 
		Assert.assertEquals(eleEmail.getCssValue("caret-color"),"rgb(244, 67, 54)");
		Assert.assertEquals(elePwd.getCssValue("caret-color"),"rgb(244, 67, 54)");

	}
	//Login with valid mail
	@Test(priority = 3)
	public void validMailloginTrial() throws InterruptedException   {
		eleEmail.sendKeys("dirk.nonn@cgm.com#1111");  //valid mail              			
		Thread.sleep(1000);
		Assert.assertNotEquals(eleEmail.getCssValue("caret-color"),"rgb(244, 67, 54)");//not invalid
	}


	@Test(priority = 4)
	public void invalidPwdloginTrial() throws InterruptedException ,ClassNotFoundException  {
		elePwd.sendKeys("abcsdweff");   //invalid password
		Thread.sleep(1000);
		Assert.assertNotEquals(elePwd.getCssValue("caret-color"),"rgb(244, 67, 54)");//not invalid
	}

	@Test(priority = 5)

	public void validMaillogin() throws InterruptedException  {
		eleLogin.click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(1000);
		eleText= driver.findElement(By.tagName("app-error-message"));
		if(driver.findElements(By.xpath("/html/body/app-root/div/div/main/app-login/div/div[1]/div/div/div[2]/div[1]/div[1]/div/app-error-message/div/div/p")).size()>0)
			System.out.println(eleText.getText());
		//if(str.equals(eleText.getText()))
		//	System.out.println("email incorrect");
		Assert.assertEquals(eleText.getText(),str);//information about mismatch in inputs
		Assert.assertEquals(eleLogin.isEnabled(),true);//Login blocked

	}

	@Test(priority = 6)

	public void invalidMaillogin() throws InterruptedException   {
		Thread.sleep(1000);
		eleEmail.clear();
		eleEmail.sendKeys("testmail.com");  //invalid mail  
		Thread.sleep(1000);
		eleLogin.click();
		//	driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);  
		Thread.sleep(2000);
		//Assert.assertEquals(eleText.getText(),str);//information about mismatch in inputs
		Assert.assertEquals(eleLogin.isEnabled(),false);//Login blocked
	}

	@Test(priority = 7)

	public void validlogin() throws InterruptedException ,ClassNotFoundException  {
		eleEmail.clear();
		elePwd.clear();
		eleEmail .sendKeys("dirk.nonn@cgm.com#1111");  //valid mail              			
		elePwd.sendKeys("recruitingTest1!");   //valid password

		eleLogin.click();

		driver.switchTo().parentFrame();//Coming out of frame

		eleUser=driver.findElement(By.xpath("/html/body/app-root/div[2]/app-header/div/div[2]/div/div[2]/ul/li[7]/a/app-avatar/div/img"));
		//eleUser.click();//Click Profile button
		Thread.sleep(2000);

		Assert.assertEquals(driver.getCurrentUrl(),"https://demo.clickdoc.de/cd-de/my-profile/personal-data");//Login Performed


		Assert.assertEquals(eleUser.isEnabled(),true);//Login blocked
		//Assert.assertEquals(eleProfile.isEnabled(),false);//Login blocked


	}
	@Test(priority = 8)

	public void profileButton() throws InterruptedException ,ClassNotFoundException  {
		eleUser.click();
		Thread.sleep(2000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[2]//div[1]")));

		eleMyProfile=driver.findElement(By.xpath("//div//div//div//div//div//div//a[1]//div[1]"));		
		eleLogOut=	driver.findElement(By.xpath("//a[2]//div[1]"));
		Assert.assertEquals(eleMyProfile.isEnabled(),true);//MyProfile enabled
		Assert.assertEquals(eleLogOut.isEnabled(),true);//Logout enabled

	}
	@Test(priority = 9)

	public void logOut() throws InterruptedException ,ClassNotFoundException  {
		eleLogOut.click();
		Thread.sleep(2000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
		Assert.assertEquals(driver.getCurrentUrl(),"https://demo.clickdoc.de/cms-de/");//Login Performed
		Thread.sleep(2000);


	}





	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", path);

	}

	@AfterTest
	public void afterTest() {
		driver.close();
	}

}
