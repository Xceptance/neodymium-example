package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import io.qameta.allure.*;


public class PosterSlider extends AbstractComponent {

	// The slides in the carousel are indexed from 1 to NUM_SLIDES
	private static final int NUM_SLIDES = 4;
	private static final String TITLE_TEXT = Neodymium.localizedText("HomePage.posterSlider.title");
	private static final String ACTIVE_CLASS = "active";
	
	private ElementsCollection carouselIndicators = $$("#pShopCarousel > ol.carousel-indicators > li");
	private ElementsCollection carouselSlides = $$("#pShopCarousel > div.carousel-inner > div[id^='pShopCarouselItem']");
	private SelenideElement prevSlideBtn = $("#btnCarouselPrev");
	private SelenideElement nextSlideBtn = $("#btnCarouselNext");
	private SelenideElement title = $("#titleIndex");
	
	@Override
	public void isComponentAvailable() {
		$("#pShopCarousel").should(exist);
	}

	/**
	 * Navigate to a slide using its position
	 * @param position the position of the slide to navigate to
	 */
	@Step("Open a slide using its position in the carousel")
	public void selectSlide(int position) {
		carouselIndicators.get(getSlideIdx(position)).click();
	}

	@Step("Go to the previous slide")
	public void previousSlide() {
		prevSlideBtn.click();
	}

	@Step("Go to the next slide")
	public void nextSlide() {
		nextSlideBtn.click();
	}

	/**
	 * Returns a slide index in the range of 0 and NUM_SLIDES - 1 using modular arithmetic
	 * @param position of the slide
	 * @return
	 */
	private int getSlideIdx(int position) {
		return Math.floorMod(--position, NUM_SLIDES);
	}

	/**
	 * Get the position of the currently visible slide
	 * @return slide's position in range 1 to NUM_SLIDES
	 */
	@Step("Get the position of the currently visible slide")
	public int getCurrentSlidePosition() {
		// Validate that there is exactly 1 visible slide in the carousel
		carouselSlides.filter(visible).shouldHaveSize(1);

		for(int i = 0; i < carouselSlides.size(); i++) {
			if(carouselSlides.get(i).is(visible)) {
				return i + 1;
			}
		}

		throw new AssertionError("Found no visible slide in the carousel");
	}

	/**
	 * Validates the slide with the given position is currently visible
	 * @param position of the slide
	 */
	@Step("Validate the given slide is currently visible")
	public void validateSlideIsVisible(int position) {
		int positionIdx = getSlideIdx(position);
		SelenideElement slide = carouselSlides.get(positionIdx);

		slide.shouldBe(visible);
		slide.shouldHave(cssClass(ACTIVE_CLASS));
		carouselIndicators.get(positionIdx).shouldHave(cssClass(ACTIVE_CLASS));
	}

	@Step("Validate the title of the poster slider")
	public void validateTitle() {
		title.shouldHave(exactText(TITLE_TEXT));
	}

	@Step("Validate the poster slider contains the expected number of slides")
	public void validateNumSlides() {
		carouselSlides.shouldHaveSize(NUM_SLIDES);
		carouselIndicators.shouldHaveSize(NUM_SLIDES);
	}

	public void validate() {
		isComponentAvailable();
		validateTitle();
		validateNumSlides();
	}
}
