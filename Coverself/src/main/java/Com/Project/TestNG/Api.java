package Com.Project.TestNG;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import tech.grasshopper.pdf.extent.ExtentPDFCucumberReporter;

public class Api {

	WebDriver driver;
	Response res;
	int totalCount;
	String cookie, response, api, tabname, tabordercount, tabemail, tabproductid, apiname, apiordercount, apimail,
			apiproductid;
	public static Actions actions;

	@Test
	public void print() throws InterruptedException {

		driver = new ChromeDriver();

		System.setProperty("WebDriver.chrome.driver",
				"C:\\Users\\subash\\git\\coverselfassignment\\Coverself\\Driver\\chromedriver.exe");
		driver.get("https://retool.com/api-generator/");
		driver.manage().window().maximize();
		waittill(10);
		scrolldown();
		addcolumn();

		System.out.println("This is testing mail");
		res = RestAssured.given().log().all().relaxedHTTPSValidation().when().log().all().get(api).then().log().all()
				.extract().response();
		int APIStatuscode = res.getStatusCode();
		response = res.getBody().asString();
		System.out.println("The API response is  " + response);
		System.out.println("The API Status Code is " + APIStatuscode);

		Assert.assertEquals(APIStatuscode, 200, "Incorrect Status Code");

		JsonPath jsres = res.jsonPath();

		String uires = res.getBody().asString();

		JsonPath uijres = new JsonPath(uires);

		// Get the path of the resource directory
		String resourceDirPath = getClass().getResource("/").getPath();

		// Specify the file path using the resource directory path
		String filePath = resourceDirPath + "output.json";

		System.out.println("The Path of the file store " + filePath);

		// save the file in the local directory
		try (FileWriter fileWriter = new FileWriter(filePath)) {
			fileWriter.write(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Object> record = jsres.getList("Name");
		/*	for (Object item : record) {
			// Process each item
			System.out.println(item);
		}*/

		totalCount = record.size();
		validatedata();

	} 

	public void waittill(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void scrolldown() {
		// WebElement
		// element=driver.findElement(By.xpath("//div[@class='h-100']/descendant::h3"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,500)");
	}

	public void addcolumn() {
		// Get the number of frames in the page
		int numberOfFrames = driver.findElements(By.tagName("iframe")).size();

		// Print the number of frames
		System.out.println("Number of frames: " + numberOfFrames);

		String iframeXPath = "//iframe[@title='REST API Generator' and contains(@src, 'https://samples.tryretool.com/embedded/public/4f6e4c49-a4b1-47ed-a8b0-455c11e5be7d')]";

		WebElement iframe = driver.findElement(By.xpath(iframeXPath));
		driver.switchTo().frame(iframe);

		waittill(2);
		for (int i = 1; i < 4; i++) {

			WebElement butt = driver.findElement(By.xpath("//*[@id=\"addColumn--0\"]/div/button"));
			butt.click();
		}

		// providing data to the table
		table();
		generateapi();

	}

	public void act(String string) {
		actions = new Actions(driver);
		WebElement textBox = driver.findElement(By.xpath(string));
		// Select the existing text in the text box
		textBox.click(); // Ensure the text box is focused
		actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
		// Delete the existing text by pressing the Delete key
		actions.sendKeys(Keys.DELETE).perform();
	}

	public void dropdown(int i) {
		driver.findElement(
				By.xpath("(//div[@class='input-control-component_vertical fs-mask'])[" + i + "]/div[2]/span/i"))
				.click();

	}

	public void hover(String string) {
		WebElement mousehover = driver.findElement(By.xpath(string));
		actions.moveToElement(mousehover).perform();
	}

	public void table() {

		act("(//div[@class='_f-VOV'])[1]/div/div/div[3]/div/div[1]/div/div/div[2]/div/input");
		driver.findElement(By.xpath("(//div[@class='_f-VOV'])[1]/div/div/div[3]/div/div[1]/div/div/div[2]/div/input"))
				.sendKeys("Name");
		act("(//div[@class='input-control-component_vertical fs-mask'])[1]/div[2]/span/input");
		// driver.findElement(By.xpath("(//div[@class='input-control-component_vertical
		// fs-mask'])[1]/div[2]/span/input")).sendKeys(" People / Full Name");
		driver.findElement(By.xpath("(//div[@class='input-control-component_vertical fs-mask'])[1]/div[2]/span/input"))
				.click();
		waittill(2);
		hover("//div[@class='ant-cascader-menus ant-cascader-menus-placement-bottomLeft']/div/ul[@class='ant-cascader-menu'][1]/li[1]");
		driver.findElement(By.xpath(
				"//div[@class='ant-cascader-menus ant-cascader-menus-placement-bottomLeft']/div/ul[@class='ant-cascader-menu'][2]/li[1]"))
				.click();
		waittill(2);

		act("(//div[@class='_f-VOV'])[2]//div/div/div[3]/div/div[1]/div/div/div[2]/div/input");
		driver.findElement(By.xpath("(//div[@class='_f-VOV'])[2]//div/div/div[3]/div/div[1]/div/div/div[2]/div/input"))
				.sendKeys("OrderCount");
		act("(//div[@class='input-control-component_vertical fs-mask'])[2]/div[2]/span/input");
		// driver.findElement(By.xpath("(//div[@class='input-control-component_vertical
		// fs-mask'])[2]/div[2]/span/input")).sendKeys(" Number / Random");
		driver.findElement(By.xpath("(//div[@class='input-control-component_vertical fs-mask'])[2]/div[2]/span/input"))
				.click();
		waittill(2);
		
		hover("//div[@class='ant-cascader-menus ant-cascader-menus-placement-bottomLeft']/div/ul[@class='ant-cascader-menu'][1]/li[2]");
		
		waittill(2);
		driver.findElement(By.xpath(
				"//div[@class='ant-cascader-menus ant-cascader-menus-placement-bottomLeft']/div/ul[@class='ant-cascader-menu'][2]/li[1]"))
				.click();
		waittill(2);

		

		act("(//div[@class='_f-VOV'])[3]//div/div/div[3]/div/div[1]/div/div/div[2]/div/input");
		driver.findElement(By.xpath("(//div[@class='_f-VOV'])[3]//div/div/div[3]/div/div[1]/div/div/div[2]/div/input"))
				.sendKeys("Email");
		act("(//div[@class='input-control-component_vertical fs-mask'])[3]/div[2]/span/input");
		driver.findElement(By.xpath("(//div[@class='input-control-component_vertical fs-mask'])[3]/div[2]/span/input"))
				.click();
		waittill(2);

		hover("//div[@class='ant-cascader-menus ant-cascader-menus-placement-bottomLeft']/div/ul[@class='ant-cascader-menu'][1]/li[1]");

		waittill(2);
		driver.findElement(By.xpath(
				"//div[@class='ant-cascader-menus ant-cascader-menus-placement-bottomLeft']/div/ul[@class='ant-cascader-menu'][2]/li[4]"))
				.click();
		waittill(2);

		act("(//div[@class='_f-VOV'])[4]/div/div/div[3]/div/div[1]/div/div/div[2]/div/input");
		driver.findElement(By.xpath("(//div[@class='_f-VOV'])[4]/div/div/div[3]/div/div[1]/div/div/div[2]/div/input"))
				.sendKeys("ProductId");
		act("(//div[@class='input-control-component_vertical fs-mask'])[4]/div[2]/span/input");
		driver.findElement(By.xpath("(//div[@class='input-control-component_vertical fs-mask'])[4]/div[2]/span/input"))
				.click();
		waittill(2);
		
		hover("//div[@class='ant-cascader-menus ant-cascader-menus-placement-bottomLeft']/div/ul[@class='ant-cascader-menu'][1]/li[1]");
		
		waittill(2);
		driver.findElement(By.xpath(	
				"//div[@class='ant-cascader-menus ant-cascader-menus-placement-bottomLeft']/div/ul[@class='ant-cascader-menu'][2]/li[1]"))
				.click();
		waittill(2);

	}

	public void scrollVertical(int scrollValue) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String script = String.format("window.scrollBy(0, %d)", scrollValue);
		jsExecutor.executeScript(script);
	}

	public void generateapi() {
		act("//div[@class='_48zyS']/descendant::input[@id='input_api_name--0']");
		driver.findElement(By.xpath("//div[@class='_48zyS']/descendant::input[@id='input_api_name--0']"))
				.sendKeys("order");
		act("//div[@class='_48zyS']/descendant::input[@id='input_rows--0']");
		driver.findElement(By.xpath("//div[@class='_48zyS']/descendant::input[@id='input_rows--0']")).sendKeys("5");
		driver.findElement(By.xpath("//div[@id='button_generate--0']/div/button[@class='_6Xk9D _BIHyA']")).click();
		// need to implement the wait concept here

		waittill(5);

		api = driver.findElement(By.xpath("(//div[@class='_Jlyco _mMrMY _mnBz1'])[2]/div/div/code/a")).getText();
		System.out.println("The Generated API is " + api);
	}

	public void validatedata() {
		System.out.println("The number of records in the response is " + totalCount);

		for (int i = 0; i < totalCount; i++) {
			String apitabname = res.jsonPath().get("Name[" + i + "]").toString();
			System.out.println(apitabname);
			String apitabordercount = res.jsonPath().get("OrderCount[" + i + "]").toString();
			System.out.println(apitabordercount);
			String apitabemail = res.jsonPath().get("Email[" + i + "]").toString();
			System.out.println(apitabemail);
			String apitabproductid = res.jsonPath().get("ProductId[" + i + "]").toString();
			System.out.println(apitabproductid);
			
				// tabname,tabordercount,tabemail,tabproductid
			int j = ++i;
				tabname = driver
						.findElement(By
								.xpath("(//div[@class='rt-tbody _HHjpm']/div[1]/div/div)["+ j + "]/div[1]/div/div[5]"))
						.getText();
				System.out.println("The name is  " + tabname);
				tabordercount = driver
						.findElement(By.xpath("(//div[@class='rt-tbody _HHjpm']/div[1]/div/div)["+ j  + "]/div[3]/div/div[5]"))
						.getText();
				System.out.println("The order count  is  " + tabordercount);
				tabemail = driver
						.findElement(By.xpath("(//div[@class='rt-tbody _HHjpm']/div[1]/div/div)["+ j + "]/div[4]/div/div[5]"))
						.getText();
				System.out.println("The mail id  is  " + tabemail);
				tabproductid = driver
						.findElement(By.xpath("(//div[@class='rt-tbody _HHjpm']/div[1]/div/div)["+ j + "]/div[5]/div/div[5]"))
						.getText();
				System.out.println("The product id  is  " + tabproductid);
				Assert.assertEquals(apitabname, tabname, "Incorrect Table Name");
				Assert.assertEquals(apitabordercount, tabordercount, "Incorrect Order Count");
				Assert.assertEquals(apitabemail, tabemail, "Incorrect Mail Id");
				Assert.assertEquals(apitabproductid, tabproductid, "Incorrect Product ID Count");
			
		}
	}
}
