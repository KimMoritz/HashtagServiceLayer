import hashtagdataservice.HashtagDataController;
import mongo.MongoRequester;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by kim on 2017-07-11.
 */
public class AssertTests {
    HashtagDataController hashtagDataController;
    MongoRequester mongoRequester;
    String hashtag;
    String period;

    @Before
    public void initialize(){
        hashtagDataController = new HashtagDataController();
        mongoRequester = new MongoRequester();
        hashtag = "brexit";
        period = "week";
    }

    //HashtagDataController

    @Test
    public void getHashTagShouldReturnJsonAsString(){
        String response = hashtagDataController.getHashtag(hashtag);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getClass(), String.class);
        new JSONObject(response);
    }

    //MongoRequester

    @Test
    public void requestShouldReturnJSONObject(){
        JSONObject response = mongoRequester.request(hashtag);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getClass(), JSONObject.class);
    }

    @Test
    public void callMongoShouldReturnJSONObjectWithValues(){
        JSONObject response = mongoRequester.callMongo(hashtag, period);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getClass(), JSONObject.class);
        Assert.assertNotNull(response.get("hashtag"));
        Assert.assertNotNull(response.get("xvals"));
        Assert.assertNotNull(response.get("yvals"));
    }
}
