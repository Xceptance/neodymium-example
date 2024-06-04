package posters.tests.smoke;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import com.xceptance.neodymium.util.Neodymium;

import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.AverageHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import dev.brachtendorf.jimagehash.matcher.exotic.SingleImageMatcher;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;

public class ImageComparisationTest extends AbstractTest
{
    @Test
    public void testVisitingHomepage() throws IOException
    {
        // open homepage and validate
        var homePage = OpenHomePageFlow.flow();
        homePage.title.validateTitle(Neodymium.localizedText("homePage.title"));
        
        File img0 = new File("src/test/resources/wallpapers/mountain.jpg");
        File img1 = new File("src/test/resources/wallpapers/split-mountains.jpg");

        HashingAlgorithm hasher = new PerceptiveHash(512);

        Hash hash0 = hasher.hash(img0);
        Hash hash1 = hasher.hash(img1);
        System.out.println(hash0);
        System.out.println(hash1);

        double similarityScore = hash0.normalizedHammingDistance(hash1);
        System.out.println(similarityScore);

        if(similarityScore < .2) 
        {
            System.out.println("almost duplicate");
        }

        //Chaining multiple matcher for single image comparison

        SingleImageMatcher matcher = new SingleImageMatcher();
        matcher.addHashingAlgorithm(new AverageHash(64),.3);
        matcher.addHashingAlgorithm(new PerceptiveHash(32),.2);

        if(matcher.checkSimilarity(img0,img1)) 
        {
            System.out.println("check similarity");
        }
    }
}
