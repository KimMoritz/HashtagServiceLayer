package mongo;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.json.JSONException;
import org.json.JSONObject;

public class MongoRequester {

    public JSONObject request(String hashtag, String period) throws Exception {
        JSONObject jsonObject = null;
            jsonObject = callMongo(hashtag, period);
        return jsonObject;
    }

    public JSONObject callMongo(String hashtag, String period) throws Exception {
        JSONObject jsonObject = new JSONObject("{\"hashtag\": \"" + hashtag +"\",\"period\":"+period+"}");
        jsonObject = sendCallToMongo(jsonObject);
        return jsonObject;
    }

    private JSONObject sendCallToMongo(JSONObject jsonObject) throws Exception {
        JSONObject jsonObject1=null;
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
        return jsonObject1;
    }

}
