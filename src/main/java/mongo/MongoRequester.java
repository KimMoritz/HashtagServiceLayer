package mongo;


import org.json.JSONException;
import org.json.JSONObject;

public class MongoRequester {

    public JSONObject request(String hashtag) {
        JSONObject jsonObject = null;
        try{
            jsonObject = callMongo(hashtag, "week");
        }catch (JSONException je){
            je.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject callMongo(String hashtag, String period){
        JSONObject jsonObject = new JSONObject("{\"hashtag\": \"" + hashtag +"\",\"xvals\": [0, 1, 2, 3, 4, 5],\"yvals\": [0, 1, 2, 4, 8, 16]}");

        return jsonObject;
    }

}
