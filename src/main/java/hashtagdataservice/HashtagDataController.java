package hashtagdataservice;

import mongo.MongoRequester;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

@RestController
public class HashtagDataController {
    MongoRequester mongoRequester;

    public HashtagDataController(){
        mongoRequester = new MongoRequester();
        mongoRequester.request("asdf", "week");
    }

    @RequestMapping("/getHashtag")
    public String getHashtag(@RequestParam("hashTag") String hashTag, @RequestParam("period") String period) {
        JSONObject jsonObject = mongoRequester.request(hashTag, period);
        return jsonObject.toString();
    }

}
