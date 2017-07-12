package mongo;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;

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
        sendCallToMongo(jsonObject);
        return jsonObject;
    }

    private void sendCallToMongo(JSONObject jsonObject) {
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory
                    ("tcp://localhost:61616");
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("hashtagServiceQueue");
            MessageProducer prod = session.createProducer(queue);
            TextMessage message = session.createTextMessage("{\"key\":\"test\", \"value\":1}");
            prod.send(message);
            prod.close();
            session.close();
            connection.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
