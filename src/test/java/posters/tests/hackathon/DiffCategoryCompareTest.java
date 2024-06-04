package posters.tests.hackathon;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;
import com.xceptance.neodymium.util.Neodymium;

import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.AverageHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.AverageKernelHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.MedianHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;

@Browser("Firefox_1400x1000")
@Browser("Chrome_1400x1000")
@Browser("Chrome_1200x768")
@Browser("Firefox_1200x768")
public class DiffCategoryCompareTest extends AbstractTest
{
    @Test
    public void testBrowsing() throws IOException
    {
        OpenHomePageFlow.openCategory("Dining", "2");
        Selenide.screenshot("category-page-now");
        File target = new File("build/reports/tests/category-page-now.png");
        File referenceChrome = new File("chromeScreenshot/diff-category-page-reference-" + Neodymium.getBrowserProfileName() + ".png");
        File referenceFirefox = new File("firefoxScreenshot/diff-category-page-reference-" + Neodymium.getBrowserProfileName() + ".png");

        List<HashingAlgorithm> algos = List.of(new AverageKernelHash(64), new PerceptiveHash(64), new AverageHash(64), new MedianHash(64));
        for (HashingAlgorithm hasher : algos)
        {
            System.out.println(hasher.getClass());
            Hash targetHash = hasher.hash(target);
            Hash referenceChromeHash = hasher.hash(referenceChrome);
            Hash referenceFirefoxHash = hasher.hash(referenceFirefox);

            double similarityScoreFirefox = targetHash.normalizedHammingDistance(referenceFirefoxHash);
            double similarityScoreChrome = targetHash.normalizedHammingDistance(referenceChromeHash);

            System.out.println("Similarity score for Firefox_1200x768 in compare to " + Neodymium.getBrowserProfileName()
                               + " is "
                               + similarityScoreFirefox);
            System.out.println("Similarity score for Chrome_1200x768 in compare to " + Neodymium.getBrowserProfileName()
                               + " is "
                               + similarityScoreChrome);
        }
    }
}
