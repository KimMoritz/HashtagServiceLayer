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
        mongoRequester.request("asdf");
    }

    @RequestMapping("/getHashtag")
    public String getHashtag(@RequestParam("hashTag") String hashTag) {
        JSONObject jsonObject = mongoRequester.request(hashTag);
        return jsonObject.toString();
    }

}
