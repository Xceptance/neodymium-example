package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;


public class AnimatedPoster extends AbstractComponent {

	// The items in the carousel are indexed from 0 to NUM_ITEMS - 1
	private static final int NUM_ITEMS = 4;
	private static final String TITLE_TEXT = "Check out our new panorama posters";
	private static final String ACTIVE_CLASS = "active";
	
	private ElementsCollection carouselIndicators = $$("#pShopCarousel > ol.carousel-indicators > li");
	private ElementsCollection carouselItems = $$("#pShopCarousel > div.carousel-inner > div[id^='pShopCarouselItem']");
	private SelenideElement prevCarousel = $("#btnCarouselPrev");
	private SelenideElement nextCarousel = $("#btnCarouselNext");
	private SelenideElement title = $("#titleIndex");
	
	@Override
	public void isComponentAvailable() {
		$("#pShopCarousel").should(exist);
	}

	/**
	 * Navigate to a carousel item using its position
	 * @param position the index of the item to navigate to
	 */
	public void navigateToPosition(int position) {
		int positionIdx = getPositionIdx(position);
		carouselIndicators.get(positionIdx).click();
	}

	public void navigateToPrev() {
		prevCarousel.click();
	}

	public void navigateToNext() {
		nextCarousel.click();
	}

	/**
	 * Returns a valid carousel item index in the range of 0 and (NUM_ITEMS - 1) using modular arithmetic
	 * @param position
	 * @return
	 */
	private int getPositionIdx(int position) {
		return Math.floorMod(position, NUM_ITEMS);
	}

	/**
	 * Get the index of the currently visible carousel item
	 * @return carousel item index in range 0 to (NUM_ITEMS - 1)
	 */
	public int getActiveCarouselIdx() {
		for(int i = 0; i < carouselItems.size(); i++) {
			if(carouselItems.get(i).is(visible)) {
				return i;
			}
		}

		throw new AssertionError("No active item in the carousel");
	}

	/**
	 * Validates the carousel item with the given position is currently active
	 * @param position
	 */
	public void validateItemIsActive(int position) {
		int positionIdx = getPositionIdx(position);
		SelenideElement activeItem = carouselItems.get(positionIdx);

		activeItem.shouldBe(visible);
		activeItem.shouldHave(cssClass(ACTIVE_CLASS));
		carouselIndicators.get(positionIdx).shouldHave(cssClass(ACTIVE_CLASS));
	}

	public void validateTitle() {
		assertEquals(title.text(), TITLE_TEXT);
	}
	
	public void validateCarouselSize() {
		assertEquals(carouselItems.size(), NUM_ITEMS);
		assertEquals(carouselIndicators.size(), NUM_ITEMS);
	}

	public void validate() {
		isComponentAvailable();
		validateTitle();
		validateCarouselSize();
	}

	public void validateNavToPos(int position) {
		navigateToPosition(position);
		validateItemIsActive(position);
	}

	public void validateNavToRandomPos() {
		int position = (int) (Math.random() * 50);    // Generate a random integer in the range 0 to 49
		validateNavToPos(position);
	}

	public void validateNavToPrev() {
		int currentIdx = getActiveCarouselIdx();
		navigateToPrev();
		validateItemIsActive(currentIdx - 1);
	}

	public void validateNavToNext() {
		int currentIdx = getActiveCarouselIdx();
		navigateToNext();
		validateItemIsActive(currentIdx + 1);
	}
}
