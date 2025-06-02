package test;
//From Appium's Windows driver, used for WinAppDriver-based automation.
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

import org.openqa.selenium.Keys;//sending keyboard short cuts ctrl+s
import org.openqa.selenium.remote.DesiredCapabilities;//setting app capabilities
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.File;//file handling
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class NotepadTest {

	private WindowsDriver<WindowsElement> driver;
	
	@BeforeMethod
	public void setup() throws Exception {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("app", "C:\\Windows\\System32\\notepad.exe");
		caps.setCapability("platformName", "Windows");
		caps.setCapability("deviceName", "WindowsPC");

		driver = new WindowsDriver<>(new URL("http://127.0.0.1:4723"), caps);//connect to winappdriver
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test
	public void testNotepadSaveFile() throws Exception {

		
		// Type text
		WindowsElement editor = driver.findElementByClassName("RichEditD2DPT");
		editor.sendKeys("Hello World");

		// Ctrl+S to save,triggers save dialog
		editor.sendKeys(Keys.chord(Keys.CONTROL, "s"));
		Thread.sleep(1000);
		//after this save dialog opens

		// switch to Save dialog
		DesiredCapabilities saveCaps = new DesiredCapabilities();
		String windowHandleHex = driver.getWindowHandle().replace("0x", "");
		saveCaps.setCapability("appTopLevelWindow", windowHandleHex);
		//starts a new session for save dialog
		WindowsDriver<WindowsElement> saveDialog = new WindowsDriver<>(new URL("http://127.0.0.1:4723"), saveCaps);

		//String filePath = System.getProperty("user.home") + "\\Desktop\\TestFile.txt";
		
		
		String filePath = "D:\\TestFile.txt";
		File file = new File(filePath);
	//	Assert.assertTrue(file.exists(), "File not saved!");
		if(file.exists()) {
			file.delete();
		}
		WindowsElement fileNameBox = saveDialog.findElementByClassName("Edit");
		fileNameBox.clear();
		fileNameBox.sendKeys(filePath);
		
		
		WindowsElement saveBtn = saveDialog.findElementByName("Save");
		saveBtn.click();
	

		Thread.sleep(1000);

		// Validate file
	
		Assert.assertTrue(file.exists(), "File not saved!");
		Thread.sleep(2000);
		//WindowsElement fil = driver.findElementByName("File");
		//WindowsElement exit = driver.findElementByName("Exit");
		editor.sendKeys(Keys.chord(Keys.chord(Keys.ALT, Keys.F4)));
		
		//editor.sendKeys(Keys.chord(Keys.ALT,"X"));
		
	}

//	@AfterMethod
//	public void tearDown() {
//		if (driver != null) {
//			driver.quit();
//		}
//	}


}
