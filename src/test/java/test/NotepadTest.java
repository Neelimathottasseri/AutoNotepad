package test;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.File;
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

		driver = new WindowsDriver<>(new URL("http://127.0.0.1:4723"), caps);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test
	public void testNotepadSaveFile() throws Exception {
	

		// Type text
		WindowsElement editor = driver.findElementByClassName("RichEditD2DPT");
		editor.sendKeys("Hello World");

		// Ctrl+S to save
		editor.sendKeys(Keys.chord(Keys.CONTROL, "s"));
		Thread.sleep(1000);

		// Attach to Save dialog
		DesiredCapabilities saveCaps = new DesiredCapabilities();
		String windowHandleHex = driver.getWindowHandle().replace("0x", "");
		saveCaps.setCapability("appTopLevelWindow", windowHandleHex);
		WindowsDriver<WindowsElement> saveDialog = new WindowsDriver<>(new URL("http://127.0.0.1:4723"), saveCaps);

		//String filePath = System.getProperty("user.home") + "\\Desktop\\TestFile.txt";
		
		String filePath = "D:\\TestFile.txt";
		WindowsElement fileNameBox = saveDialog.findElementByClassName("Edit");
		fileNameBox.clear();
		fileNameBox.sendKeys(filePath);
		

		WindowsElement saveBtn = saveDialog.findElementByName("Save");
		saveBtn.click();
	

		Thread.sleep(1000);

		// Validate file
		File file = new File(filePath);
		Assert.assertTrue(file.exists(), "File not saved!");
	

	}

//	@AfterMethod
//	public void tearDown() {
//		if (driver != null) {
//			driver.quit();
//		}
//	}


}