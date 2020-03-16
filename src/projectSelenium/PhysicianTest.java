package projectSelenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class PhysicianTest {
	WebDriver driver;
	WebDriverWait wait;
	WebElement eleName,elePractice,eleDistkm,eleMoreResults,eleBest,eleAvailableAppt,eleHeadline,eleAlpha,eleDist,eleSlider,eleSearch,eleSearchSec,eleSort,eleResult,eleLocation,eleOnline,eleVideo,eleBarrier,eleSearchBtn,select,selectPLZ;
	List<WebElement> options,options1;
	ArrayList<String> arlist;
	Actions action;
	Boolean found;
	JavascriptExecutor js;
	int i;
	String actualTitle;
	String expectedTitle="CLICKDOC";

	@Test(priority = 0) 
	public void start()   {
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.clickdoc.de/cms-de/");
		wait=new WebDriverWait(driver, 30);  
		actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test(priority = 1)
	public void suchseite() throws InterruptedException {
		eleSearch=driver.findElement(By.xpath("//*[@id=\"menu-item-10\"]/a"));
		eleSearch.click();
		Thread.sleep(1000);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		eleSearchSec=driver.findElement(By.xpath("//div[@class='container-fluid filter-container']"));
		eleSort=driver.findElement(By.xpath("//div[@class='sort']//div[@class='container']"));
		eleResult=driver.findElement(By.xpath("//div[@class='card d-flex flex-column justify-content-center no-gutters']"));
		Assert.assertEquals(true, eleSearchSec.isDisplayed());
		Assert.assertEquals(true, eleSort.isDisplayed());
		Assert.assertEquals(true, eleResult.isDisplayed());

	}
	@Test(priority = 2)
	public void search() {
		eleName=driver.findElement(By.xpath("//input[@id='search-query-typeahead']"));
		eleLocation=driver.findElement(By.id("search-location-typeahead"));
		eleOnline=driver.findElement(By.xpath("//app-filter//div[3]//div[1]//div[1]"));
		eleVideo=driver.findElement(By.xpath("//app-filter//div[4]//div[1]//div[1]"));
		eleBarrier=driver.findElement(By.xpath("//app-filter//div[5]//div[1]//div[1]"));
		eleSearchBtn=driver.findElement(By.xpath("//button[@class='btn btn-primary btn-block']"));

		Assert.assertEquals(true, eleName.isDisplayed());
		Assert.assertEquals(true, eleLocation.isDisplayed());
		Assert.assertEquals(true, eleOnline.isDisplayed());
		Assert.assertEquals(true, eleVideo.isDisplayed());
		Assert.assertEquals(true, eleBarrier.isDisplayed());
		Assert.assertEquals(true, eleSearchBtn.isDisplayed());
		driver.findElement(By.xpath("/html/body/app-root/div[2]/app-tracking/div/div/div[2]/div[2]")).click();

	}
	@Test(priority = 3)
	public void sortSection() {
		eleBest=driver.findElement(By.xpath("//body/app-root/div/div/app-search/div/div[@id='search']/div/div/div/div/app-sort/div/div/div[2]/div[1]/div[1]"));
		eleAlpha=driver.findElement(By.xpath("//*[@id=\"search\"]/div/div[2]/div[2]/div[2]/app-sort/div/div/div[3]/div/div"));
		eleDist=driver.findElement(By.xpath("//body/app-root/div/div/app-search/div/div[@id='search']/div/div/div/div/app-sort/div/div/div[4]/div[1]/div[1]"));
		eleSlider=driver.findElement(By.xpath("//body//span[5]"));
		Assert.assertEquals(true, eleBest.isDisplayed());
		Assert.assertEquals(true, eleAlpha.isDisplayed());
		Assert.assertEquals(true, eleDist.isDisplayed());
		Assert.assertEquals(true, eleSlider.isDisplayed());

	}
	
	@Test(priority = 4)
	public void resultSection() {
		Assert.assertEquals("AUF DER LINKEN SEITE KANNST DU DIE ARZTSUCHE STARTEN.", eleResult.getText());

	}

	@Test(priority = 5)
	public void nameTrial() throws InterruptedException {
		eleName.sendKeys("Tom");  //Entering  name
		Thread.sleep(2000);
		found = false;
		select = driver.findElement(By.xpath("//*[@id=\"search\"]/div/div[2]/div[2]/div[1]/app-filter/div/div/div[2]/div[1]/div/div/typeahead-container"));
		List<WebElement> options = select.findElements(By.tagName("button"));
		for (WebElement option1 : options) {
			if(option1.getText().contains("Tom"))
			{

				//	System.out.println(option1.getText());
				found=true;
			}
		}  
		Assert.assertEquals(true, found.booleanValue());
		Thread.sleep(1000);
	}

	@Test(priority = 6)
	public void furtherInput() throws InterruptedException {
		eleName.sendKeys(" En");
		Thread.sleep(2000);
		found = false;
		select = driver.findElement(By.xpath("//*[@id=\"search\"]/div/div[2]/div[2]/div[1]/app-filter/div/div/div[2]/div[1]/div/div/typeahead-container"));
		options = select.findElements(By.tagName("button"));

		for (WebElement option1 : options) {
			System.out.println(option1.getText());

			if(option1.getText().contains("Tom En"))
				found=true;


		} 
		Assert.assertEquals(true, found.booleanValue());
		Thread.sleep(1000);
	}
	@Test(priority = 7)
	public void noOption() throws InterruptedException {

		eleName.sendKeys("ter");
		Thread.sleep(2000);
		//found = false;

		//options = select.findElements(By.tagName("button"));
		Assert.assertEquals(driver.findElements(By.tagName("typeahead-container")).size(),0);

	}

	@Test(priority = 8)
	public void validName() throws InterruptedException {
		eleName.clear();
		eleName.sendKeys("Beate");
		found=false;
		eleSearchBtn.click();//Click Suchen
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);  
		select = driver.findElement(By.xpath("//*[@id=\"search\"]/div/div[3]/div/div"));  //result window

		options = select.findElements(By.tagName("app-physician-card")); //find cards

		for (WebElement option1 : options) {
			//System.out.println(option1.getText());
			if(option1.getText().contains("Beate"))
				found=true;
		}    
		Assert.assertEquals(true, found.booleanValue());

	}

	@Test(priority = 9)
	public void searchCard() throws InterruptedException {

		eleHeadline=driver.findElement(By.xpath("//body/app-root/div/div/app-search/div/div[@id='search']/div/div/div/div/app-physician-card[1]/div[1]/div[1]"));
		elePractice=driver.findElement(By.xpath("//body/app-root/div/div/app-search/div/div[@id='search']/div/div/div/div/app-physician-card[1]/div[1]/div[2]/div[1]/div[1]/div[1]"));
		eleAvailableAppt=driver.findElement(By.xpath("//body/app-root/div/div/app-search/div/div[@id='search']/div/div/div/div/app-physician-card[1]/div[1]/div[2]/app-physician-calendar[1]/div[1]"));
		Assert.assertEquals(true, eleHeadline.isDisplayed());
		Assert.assertEquals(true, elePractice.isDisplayed());
		Assert.assertEquals(true, eleAvailableAppt.isDisplayed());

	}

	@Test(priority = 10)
	public void scrollDown() throws InterruptedException {
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(1000);
		eleMoreResults=driver.findElement(By.xpath("//*[@id=\"search\"]/div/div[3]/div/div/div/a"));
		Thread.sleep(1000);
		Assert.assertEquals(true, eleMoreResults.isDisplayed());
	}

	@Test(priority = 11)
	public void moreResults() throws InterruptedException {

		eleMoreResults.click();//Click Mehr Anzeige
		found=false;
		Thread.sleep(1000);
		select = driver.findElement(By.xpath("//*[@id=\"search\"]/div/div[3]/div/div"));  //result window

		options1 = select.findElements(By.tagName("app-physician-card")); //find cards
		System.out.println(options.size());
		System.out.println(options1.size());

		if(options1.size()>options.size())
			found=true;
		Assert.assertEquals( found.booleanValue(),true);

	}

	@Test(priority = 12)
	public void scrollNValidLocation() throws InterruptedException {
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
		Thread.sleep(2000);
		eleLocation.sendKeys("56567");
		Thread.sleep(1000);
		found=false;
		//dropdown suggestions
		selectPLZ = driver.findElement(By.tagName("typeahead-container"));
		options = selectPLZ.findElements(By.tagName("button"));

		for (WebElement option1 : options) {
			if(option1.getText().contains("56567"))
			{

				found=true;

			}
		}  		
		Assert.assertEquals(true, found.booleanValue());

	}

	@Test(priority = 13)
	public void selectEntry() throws InterruptedException {
		Thread.sleep(1000);
		//SELECTING FROM DROPDOWN
		for (WebElement option1 : options) {
			if(option1.getText().contains("56567 Neuwied"))
			{
				option1.click();
				break;

			}
		}   
		//	Thread.sleep(1000);

		eleSearchBtn.click();//Click Suchen
		//refined searches with address
		Thread.sleep(1000);

		select = driver.findElement(By.xpath("//div[@class='panel default-panel']"));  //result window
		i=1;

		options = select.findElements(By.tagName("app-physician-card"));
		Thread.sleep(1000);
		found=false;
		for (WebElement option : options) {
			//System.out.println(option.getText());
			if(option.findElement(By.xpath("//span[contains(text(),'km')]")) != null)
				found=true;
			i++;
		} 
		Assert.assertEquals(true, found.booleanValue());
	}

	@Test(priority = 14)
	public void onlineBookable() throws InterruptedException {	
		Thread.sleep(2000);
		eleOnline.click();
		WebElement dropdown= driver.findElement(By.xpath("//div[@class='day dropdown dropdown-select d-block']//button[@class='btn btn-outline-light btn-block text-left select-label dropdown-toggle']"));
		WebElement time= driver.findElement(By.xpath("//div[@class='time dropdown-select d-block dropdown']//button[@class='btn btn-outline-light btn-block text-left select-label dropdown-toggle']"));
		Assert.assertEquals(true, dropdown.isDisplayed());
		Assert.assertEquals(true, time.isDisplayed());
	}
	@Test(priority = 15)
	public void onlineBookableResults() throws InterruptedException {	
		eleSearchBtn.click();//Click Suchen
		Thread.sleep(1000);
		i=1;
		found=true;
		select = driver.findElement(By.xpath("//div[@class='panel default-panel']"));
		options = select.findElements(By.tagName("app-physician-card"));
		for (WebElement option : options) {
			if(option.findElement(By.xpath("//body/app-root/div/div/app-search/div/div[@id='search']/div/div/div/div/app-physician-card[\"+i+\"]/div/div/div/div/div[2]")).getText().contains("Online-Termine"));
			else found=false;

			i++;
		}

		Assert.assertEquals(true, found.booleanValue());

	}

	@Test(priority = 16)
	public void videoconf() throws InterruptedException {
		eleName.clear();
		//System.out.println("1");

		//Check „Video-Conference“-Checkbox
		//driver.navigate().refresh();
		Thread.sleep(2000);

		eleName.clear();

		eleVideo.click();
		Thread.sleep(2000);

		eleSearchBtn.click();//Click Suchen

		i=1;
		Thread.sleep(2000);
		found=false;
		select = driver.findElement(By.xpath("//div[@class='panel default-panel']"));
		options = select.findElements(By.tagName("app-physician-card"));
		if(options.size()==0)
			found=true;
		else
		{
			for (WebElement option1 : options) {

				if(option1.findElement(By.xpath("//body/app-root/div/div/app-search/div/div[@id='search']/div/div/div/div/app-physician-card[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]")).getText().contains("Videosprechstunde"));
				else  found=true;
				i++;

			}	
		}
		Assert.assertEquals(found.booleanValue(),false);

	}

	@Test(priority = 17)
	public void barrierfrei() throws InterruptedException {
		driver.navigate().to("https://demo.clickdoc.de/cd-de/search?ab=true&ac=false&av=true&d=20&l=56567%20Neuwied&it=egal&sort=bestHit");
		Thread.sleep(1000);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		eleVideo=driver.findElement(By.xpath("//app-filter//div[4]//div[1]//div[1]"));
		eleBarrier=driver.findElement(By.xpath("//app-filter//div[5]//div[1]//div[1]"));
		eleSearchBtn=driver.findElement(By.xpath("//button[contains(text(),'Suchen')]"));
		eleVideo.click();
		Thread.sleep(1000);

		eleBarrier.click();
		Thread.sleep(1000);

		eleSearchBtn.click();//Click Suchen

		Thread.sleep(1000);
		found=true;
		select = driver.findElement(By.xpath("//div[@class='panel default-panel']"));
		options = select.findElements(By.tagName("app-physician-card"));
		if(options.size()==0)
			found=false;
		else
		for (WebElement option1 : options) {
			if(option1.getText().contains("Barrierfrei"));
			else found=false;
		}		Assert.assertEquals( found.booleanValue(),true );

	}
	@Test(priority = 18)
	public void alphaSort() throws InterruptedException {
		//Alphabetical-Sort
		Thread.sleep(2000);
		js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)");
		Thread.sleep(2000);

        eleAlpha=driver.findElement(By.xpath("//body/app-root/div/div/app-search/div/div[@id='search']/div/div/div/div/app-sort/div/div/div[3]/div[1]/div[1]"));
		eleAlpha.click();
		Thread.sleep(2000);

		arlist = new ArrayList<String>( );
		select = driver.findElement(By.xpath("//div[@class='panel default-panel']"));
		options = select.findElements(By.xpath("//*[@class='physician-name align-self-center']"));
		//Splitting lastname
		for(i=0;i<options.size();i++)  
		{
			if(options.get(i).getText().contains(","))
			{

				String[] str1 = options.get(i).getText().split(",");   
				if(str1[0].contains(" "))
				{
					String[] str2 =str1[0].split(" ");
					arlist.add(str2[str2.length-1]); 

				}
				else
					arlist.add(str1[0]);

			}

			else
			{

				String[] str1 = options.get(i).getText().split(" ");
				arlist.add(str1[str1.length-1]);
			}

		}

		//boolean isSorted = Ordering.natural().isOrdered(list); //can also be used for sorting
		//Sorting
		String previous = ""; // empty string: guaranteed to be less than or equal to any other
		found= true;
		System.out.print("List of last names:");
		for ( String current: arlist) {
			System.out.print(current +" ");
			if (current.compareTo(previous) < 0)
				found= false;
			previous = current;
		}

		Assert.assertEquals( found.booleanValue(),true);
	}


	@Test(priority = 19)
	public void distanceSort() throws InterruptedException {
		Thread.sleep(1000);

		eleDist=driver.findElement(By.xpath("//body/app-root/div/div/app-search/div/div[@id='search']/div/div/div/div/app-sort/div/div/div[4]/div[1]/div[1]"));
		eleDist.click();
		Thread.sleep(2000);
		select = driver.findElement(By.xpath("//div[@class='panel default-panel']"));
		options = select.findElements(By.tagName("app-physician-card"));
		int j=1;
		float f;
		char c;
		float	fl[]=new float[10];
		int k=0;
		for (WebElement option1 : options) {
			String dist="";

			String strDist=option1.findElement(By.xpath("//body/app-root/div/div/app-search/div/div[@id='search']/div/div/div/div/app-physician-card["+j+"]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/span[2]/span[1]")).getText();

			for(i=0;i<4;i++)  		
			{
				c=strDist.charAt(i);
				if(c==',')
					dist=dist+".";
				else 
					dist=dist+c;
			}

			f = Float.parseFloat(dist);
			fl[k]=fl[k]+f;
			j++;
			k++;
		}
		System.out.println("List of distances :");

		found=true;//finding whether the float elements are sorted
		for ( i = 0; i < fl.length - 1; i++) {
			if (fl[i] > fl[i + 1]) 
				found=false;	
			System.out.print(" "+fl[i]);
		}
		Assert.assertEquals( found.booleanValue(), true);

	}
	@Test(priority = 20)
	public void dragSlider() throws InterruptedException {
		
		action= new Actions(driver);
		Thread.sleep(2000);

		eleSlider=driver.findElement(By.xpath("//body//app-sort//span[5]"));
		action.clickAndHold(eleSlider).build().perform();
		action.moveByOffset(30, 0);
		Thread.sleep(2000);
		action.build().perform();
		found=false;
		select = driver.findElement(By.xpath("//*[@id=\"search\"]/div/div[3]/div/div"));  //result window

		options1 = select.findElements(By.tagName("app-physician-card")); //find cards
		//System.out.println(options.size());
		//System.out.println(options1.size());

		if(options1.size()==options.size())
			found=true;
		Assert.assertEquals( found.booleanValue(),true);

	}

	@Test(priority = 21)
	public void releaseSlider() throws InterruptedException {
		Thread.sleep(2000);
		action.release().build();
		action.perform();
		found=false;
		select = driver.findElement(By.xpath("//*[@id=\"search\"]/div/div[3]/div/div"));  //result window

		options1 = select.findElements(By.tagName("app-physician-card")); //find cards
		//System.out.println(options.size());
		//System.out.println(options1.size());

		if(options1.size()!=options.size())
			found=true;
		Assert.assertEquals( found.booleanValue(),false);

	}
	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Tony\\eclipse\\chromedriver.exe");

	}

	@AfterTest
	public void afterTest() {
		driver.close();

	}

}
