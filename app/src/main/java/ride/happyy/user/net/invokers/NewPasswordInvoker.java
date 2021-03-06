package ride.happyy.user.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.BasicBean;
import ride.happyy.user.net.ServiceNames;
import ride.happyy.user.net.WebConnector;
import ride.happyy.user.net.parsers.BasicParser;
import ride.happyy.user.net.utils.WSConstants;

public class NewPasswordInvoker extends BaseInvoker {

    public NewPasswordInvoker() {
        super();
    }

    public NewPasswordInvoker(HashMap<String, String> urlParams,
                              JSONObject postData) {
        super(urlParams, postData);
    }

    public BasicBean invokeNewPasswordWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.NEW_PASSWORD), WSConstants.PROTOCOL_HTTP, null, postData);

        //		webConnector= new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, postData,null);
        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
        String wsResponseString = webConnector.connectToPOST_service();
        //	String wsResponseString=webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        BasicBean basicBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return basicBean = null;
        } else {
            basicBean = new BasicBean();
            BasicParser newPasswordParser = new BasicParser();
            basicBean = newPasswordParser.parseBasicResponse(wsResponseString);
            return basicBean;

        }
    }
}
