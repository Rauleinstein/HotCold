package hotcold.utilitys;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by bott on 29/11/2014.
 */
public abstract class Utils {

    public static JSONArray  jsonArrayRemove(JSONArray jsonA, int pos){
        JSONArray jArrayTemp = new JSONArray();

        for (int i =0; i<jsonA.length(); i++){
            if(i!=pos){
                try {
                    jArrayTemp.put(jsonA.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return jArrayTemp;
    }
}
