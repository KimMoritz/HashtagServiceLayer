package mongo;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.json.JSONException;
import org.json.JSONObject;

public class MongoRequester {

    public JSONObject request(String hashtag, String period) {
        JSONObject jsonObject = null;
        try{
            jsonObject = callMongo(hashtag, period);
        }catch (JSONException je){
            je.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject callMongo(String hashtag, String period){
        JSONObject jsonObject = new JSONObject("{\"hashtag\": \"" + hashtag +"\",\"period\":"+period+"}");
        jsonObject = sendCallToMongo(jsonObject);
        return jsonObject;
    }

    private JSONObject sendCallToMongo(JSONObject jsonObject) {
        JSONObject jsonObject1=null;
        try {
            CamelContext context = new DefaultCamelContext();
            context.start();
            ProducerTemplate producerTemplate = null;
            context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
            producerTemplate = context.createProducerTemplate();
            Object o = producerTemplate.sendBody("activemq:hashtagServiceQueue", ExchangePattern.InOut,
                    jsonObject.toString());
            jsonObject1 = new JSONObject(o.toString());
            System.out.println("response: " + o.toString()); //later: change from o to jsonobject1
            producerTemplate.stop();
            context.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject1;
    }

}
