package ElementImplementation;

import java.time.Duration;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.utils.LoggerUtility;

/**
 * Utility class for common web element interactions. Handles element
 * interactions with logging and exception handling. Author: ShivaShankaran K
 */
public class ElementBase {

	private WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	private static final int DEFAULT_TIMEOUT = 10;
	private final Logger log = LoggerUtility.getLogger(this.getClass());
	
	/**
	 * Constructor to initialize WebDriver and WebDriverWait
	 * 
	 * @param driver WebDriver instance
	 */
	public ElementBase(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT)); // Default wait time
	}

	/**
	 * Waits for the given element to be visible within the specified timeout. If
	 * timeoutInSeconds is 0, it falls back to a default wait time.
	 *
	 * @param element          The WebElement to wait for.
	 * @param timeoutInSeconds The timeout duration in seconds (0 for default).
	 * @return WebElement if found, otherwise null.
	 * @author ShivaShankaran K
	 */
	public WebElement waitForElement(By element, int timeoutInSeconds) {
		int waitTime = (timeoutInSeconds > 0) ? timeoutInSeconds : DEFAULT_TIMEOUT; // Use custom wait if provided
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		} catch (Exception e) {
			log.error("Element not visible after waiting for " + waitTime + " seconds", e);
			return null;
		}
	}

	/**
	 * Clicks on an element after waiting for visibility
	 * 
	 * @param element WebElement to be clicked
	 * @author ShivaShankaran K
	 */
	public void clickElement(By element) {
		try {
			clickElement(element, 0);
			log.info("Clicked on element: " + element);
		} catch (Exception e) {
			log.error("Failed to click element: " + element, e);
			throw e;
		}
	}

	/**
	 * Clicks on the given element with a custom wait time.
	 *
	 * @param element          The WebElement to be clicked.
	 * @param timeoutInSeconds The custom timeout in seconds.
	 * @author ShivaShankaran K
	 */
	public void clickElement(By element, int timeoutInSeconds) {
		try {
			WebElement visibleElement = waitForElement(element, timeoutInSeconds);
			if (visibleElement != null) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",visibleElement);
				visibleElement.click();
				log.info("Clicked on the element successfully after waiting for " + timeoutInSeconds + " seconds");
			} else {
				log.error("Element not found within specified timeout");
			}
		} catch (Exception e) {
			log.error("Error clicking element with custom wait", e);
			throw e;
		}

	}

	/**
	 * Sends text to the given element using the default wait time.
	 *
	 * @param element The WebElement to send text to.
	 * @param text    The text to be entered.
	 * @author ShivaShankaran K
	 */
	public void enterText(By element, String text) {
		enterText(element, text, 0); // 0 means use default wait
	}

	/**
	 * Sends text to the given element with a custom wait time.
	 *
	 * @param element          The WebElement to send text to.
	 * @param text             The text to be entered.
	 * @param timeoutInSeconds The custom timeout in seconds.
	 * @author ShivaShankaran K
	 */
	public void enterText(By element, String text, int timeoutInSeconds) {
		try {
			WebElement visibleElement = waitForElement(element, timeoutInSeconds);
			if (visibleElement != null) {
				visibleElement.clear();
				visibleElement.sendKeys(text);
				log.info("Entered text successfully in the element");
			} else {
				log.error("Element not found within specified timeout");
			}
		} catch (Exception e) {
			log.error("Error entering text with custom wait", e);
			throw e;
		}
	}

	/**
	 * Retrieves the text from a given WebElement
	 * 
	 * @param element WebElement to retrieve text from
	 * @return Text content of the element
	 * @author ShivaShankaran K
	 */
	public String getElementText(By element) {
		try {
			//d
			String text = waitForElement(element, DEFAULT_TIMEOUT).getText();
			log.info("Retrieved text: " + text);
			return text;
		} catch (Exception e) {
			log.error("Failed to retrieve text from element: " + element, e);
			throw e;
		}
	}

	/**
	 * Retrieves an attribute value from a given WebElement
	 * 
	 * @param element       WebElement to get attribute from
	 * @param attributeName Name of the attribute
	 * @return Value of the attribute
	 * @author ShivaShankaran K
	 */
	public String getAttributeValue(By element, String attributeName) {
		try {
			String value = waitForElement(element, DEFAULT_TIMEOUT).getAttribute(attributeName);
			log.info("Retrieved attribute value: " + value);
			return value;
		} catch (Exception e) {
			log.error("Failed to retrieve attribute value from element: " + element, e);
			throw e;
		}
	}


	/**
	 * Get the current page title after waiting for it to be non-empty.
	 * @return The current page title, or null if not retrieved.
	 * @author ShivaShankaran K
	 */
	public String getPageTitle(String expectedTitle) {
		try {
			// Wait until the title is not empty
			boolean isTitleAvailable = wait.until(ExpectedConditions.titleIs(expectedTitle));

			if (isTitleAvailable) {
				String title = driver.getTitle();
				log.info("Retrieved page title: " + title);
				return title;
			} else {
				log.warn("Page title is empty after wait.");
			}
		} catch (Exception e) {
			log.error("Error retrieving page title: " + e.getMessage());
		}
		return null;
	}


	/**
	 * Checks if an element is displayed
	 * 
	 * @param element WebElement to check
	 * @return true if displayed, false otherwise
	 * @author ShivaShankaran K
	 * 
	 */
	public boolean isElementDisplayed(By element) {
		try {
			boolean displayed = waitForElement(element, DEFAULT_TIMEOUT).isDisplayed();
			log.info("Element is displayed: " + displayed);
			return displayed;
		} catch (Exception e) {
			log.error("Failed to check if element is displayed: " + element, e);
			return false;
		}
	}

	/**
	 * Retrieves a list of WebElements by locator
	 * 
	 * @param locator By locator to find elements
	 * @return List of WebElements
	 * @author ShivaShankaran K
	 */
	public List<WebElement> getElements(By locator) {
		try {
			List<WebElement> elements = driver.findElements(locator);
			log.info("Retrieved elements: " + elements.size());
			return elements;
		} catch (Exception e) {
			log.error("Failed to retrieve elements for locator: " + locator, e);
			throw e;
		}
	}

	/**
	 * Waits for a frame to be available and switches to it.
	 *
	 * @param frameLocator     The By locator of the frame.
	 * @param timeoutInSeconds The maximum wait time.
	 * @author ShivaShankaran K
	 */
	public void waitForFrameAndSwitch(By frameLocator, int timeoutInSeconds) {
		try {
			int waitTime = (timeoutInSeconds > 0) ? timeoutInSeconds : DEFAULT_TIMEOUT;
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
			log.info("Switched to frame: " + frameLocator);
		} catch (TimeoutException e) {
			log.error("Timeout waiting for frame: " + frameLocator, e);
		} catch (Exception e) {
			log.error("Error switching to frame: " + frameLocator, e);
			throw e;
		}
	}

	/**
	 * Waits for a frame to be available by index and switches to it.
	 *
	 * @param frameIndex       The index of the frame (starting from 0).
	 * @param timeoutInSeconds The maximum wait time.
	 * @author ShivaShankaran K
	 */
	public void waitForFrameAndSwitch(int frameIndex, int timeoutInSeconds) {
		try {
			int waitTime = (timeoutInSeconds > 0) ? timeoutInSeconds : DEFAULT_TIMEOUT;
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
			log.info("Switched to frame at index: " + frameIndex);
		} catch (TimeoutException e) {
			log.error("Timeout waiting for frame at index: " + frameIndex, e);
		} catch (Exception e) {
			log.error("Error switching to frame at index: " + frameIndex, e);
			throw e;
		}
	}

	/**
	 * Switches back to the default content from a frame.
	 *
	 * @author ShivaShankaran K
	 */
	public void switchToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
			log.info("Switched back to default content");
		} catch (Exception e) {
			log.error("Error switching back to default content", e);
			throw e;
		}
	}


	//drop down functions

	/**
	 * Selects an option from a dropdown by visible text.
	 * @param locator The By locator of the dropdown.
	 * @param visibleText The visible text of the option to select.
	 * @author ShivaShankaran K
	 */
	public void selectDropdownByText(By locator, String visibleText) {
		try {
			WebElement dropdown = waitForElement(locator, DEFAULT_TIMEOUT);
			if (dropdown != null) {
				Select select = new Select(dropdown);
				select.selectByVisibleText(visibleText);
				log.info("Selected dropdown option by text: " + visibleText);
			} else {
				log.warn("Dropdown not found: " + locator);
			}
		} catch (Exception e) {
			log.error("Error selecting dropdown by text: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Selects an option from a dropdown by value.
	 * @param locator The By locator of the dropdown.
	 * @param value The value attribute of the option to select.
	 * @author ShivaShankaran K
	 */
	public void selectDropdownByValue(By locator, String value) {
		try {
			WebElement dropdown = waitForElement(locator, DEFAULT_TIMEOUT);
			if (dropdown != null) {
				Select select = new Select(dropdown);
				select.selectByValue(value);
				log.info("Selected dropdown option by value: " + value);
			} else {
				log.warn("Dropdown not found: " + locator);
			}
		} catch (Exception e) {
			log.error("Error selecting dropdown by value: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Selects an option from a dropdown by index.
	 * @param locator The By locator of the dropdown.
	 * @param index The index of the option to select.
	 * @author ShivaShankaran K
	 */
	public void selectDropdownByIndex(By locator, int index) {
		try {
			WebElement dropdown = waitForElement(locator, DEFAULT_TIMEOUT);
			if (dropdown != null) {
				Select select = new Select(dropdown);
				select.selectByIndex(index);
				log.info("Selected dropdown option by index: " + index);
			} else {
				log.warn("Dropdown not found: " + locator);
			}
		} catch (Exception e) {
			log.error("Error selecting dropdown by index: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Retrieves the current page URL.
	 * @return The current URL as a String.
	 * @author ShivaShankaranK
	 */
	public String getCurrentUrl() {
		try {
			String url = driver.getCurrentUrl();
			log.info("Retrieved current URL: " + url);
			return url;
		} catch (Exception e) {
			log.error("Error retrieving current URL", e);
			throw e;
		}
	}

	/**
	 * Performs a double-click action on the specified element.
	 * @param locator The By locator of the element to double-click.
	 * @author ShivaShankaranK
	 */
	public void doubleClickElement(By locator) {
		try {
			WebElement element = waitForElement(locator, DEFAULT_TIMEOUT);
			actions.doubleClick(element).perform();
			log.info("Double clicked on element: " + locator);
		} catch (Exception e) {
			log.error("Error double-clicking element: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Performs a right-click (context click) on the specified element.
	 * @param locator The By locator of the element to right-click.
	 * @author ShivaShankaranK
	 */
	public void rightClickElement(By locator) {
		try {
			WebElement element = waitForElement(locator, DEFAULT_TIMEOUT);
			actions.contextClick(element).perform();
			log.info("Right-clicked on element: " + locator);
		} catch (Exception e) {
			log.error("Error right-clicking element: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Performs a drag-and-drop action from a source element to a target element.
	 * @param sourceLocator The By locator of the source element.
	 * @param targetLocator The By locator of the target element.
	 * @author ShivaShankaranK
	 */
	public void dragAndDrop(By sourceLocator, By targetLocator) {
		try {
			WebElement source = waitForElement(sourceLocator, DEFAULT_TIMEOUT);
			WebElement target = waitForElement(targetLocator, DEFAULT_TIMEOUT);
			actions.dragAndDrop(source, target).perform();
			log.info("Dragged element from " + sourceLocator + " to " + targetLocator);
		} catch (Exception e) {
			log.error("Error performing drag and drop: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Moves to the specified element and hovers over it.
	 * @param locator The By locator of the element to hover.
	 * @author ShivaShankaranK
	 */
	public void hoverOverElement(By locator) {
		try {
			WebElement element = waitForElement(locator, DEFAULT_TIMEOUT);
			actions.moveToElement(element).perform();
			log.info("Hovered over element: " + locator);
		} catch (Exception e) {
			log.error("Error hovering over element: " + e.getMessage());
			throw e;
		}
	}
}
