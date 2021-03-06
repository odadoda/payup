package no.iskald.payup;

import com.enonic.xp.script.bean.BeanContext;
import com.enonic.xp.script.bean.ScriptBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class StripeCharger implements ScriptBean {
    Logger LOG = LoggerFactory.getLogger(this.getClass());

    public Charge chargeCard(String apiKey, String token, double amount, String description, String currency) throws APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
      Stripe.apiKey = apiKey;
      try {
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", AmountParser.fromDouble(amount));
        chargeParams.put("currency", currency);
        chargeParams.put("source", token);
        chargeParams.put("description", description);

        Charge charge = Charge.create(chargeParams);
        return charge;
      } catch (CardException e) {
          return null;
      }

    }

    @Override
    public void initialize(BeanContext context) {
    }
  }
