package mongo;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
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
        sendCallToMongo(jsonObject);
        return jsonObject;
    }

    private void sendCallToMongo(JSONObject jsonObject) {
        try {
            CamelContext context = new DefaultCamelContext();
            context.start();
            ProducerTemplate producerTemplate = null;
            context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
            producerTemplate = context.createProducerTemplate();
            Object o = producerTemplate.sendBody("activemq:myQueue.queue", ExchangePattern.InOut, "Hello");
            System.out.println("response: " + o.toString());
            producerTemplate.stop();
            context.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
