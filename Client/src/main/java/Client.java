import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Client {

    public HttpURLConnection setGetConnection() throws IOException {
        URL url = new URL("http://localhost:8082/employee");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept","application/json");
        con.setDoOutput(true);
        return con;
    }

    public HttpURLConnection setPostConnection() throws IOException {
        URL url = new URL("http://localhost:8082/timetracking");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        return con;
    }

    public StringBuffer getGetResponse() throws IOException {
        HttpURLConnection connection = setGetConnection();
        int status = connection.getResponseCode();
        if (status == 200) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return content;
        }
        connection.disconnect();
        return null;
    }

    public ArrayList<String> getAllEmployeesNames() throws IOException, JSONException {
        StringBuffer content = getGetResponse();
        JSONArray tsmresponse = new JSONArray(content.toString());
        ArrayList<String> nameList = new ArrayList<String>();

        for (int i = 0; i < tsmresponse.length(); i++) {
            nameList.add(tsmresponse.getJSONObject(i).getString("name"));
        }
        return nameList;
    }

    public JSONObject JSONClocking(String name, Time checkIn, Time checkOut,Date checkDate) throws IOException, JSONException {
        JSONObject clocking=new JSONObject();
        JSONObject employee=new JSONObject();
        JSONArray response=new JSONArray(getGetResponse().toString());
        for(int i =0 ;i< response.length();i++){
            if(name.equals(response.getJSONObject(i).getString("name"))){
                employee=response.getJSONObject(i);
                break;
            }
        }
        //System.out.println(response);
        clocking.put("check_in",checkIn);
        clocking.put("check_out",checkOut);
        clocking.put("employee",new JSONObject().put("id",employee.getInt("id")));
        clocking.put("check_date",checkDate);
        //System.out.println(clocking);
        return clocking;
    }

    public void insertNewClocking(JSONObject clocking) throws IOException {
        HttpURLConnection connection=setPostConnection();
        OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
        wr.write(clocking.toString());
        wr.flush();
        wr.close();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            //System.out.println(response.toString());
        }
        connection.disconnect();
    }




    /*public static void main(String[] args) throws IOException, JSONException {
        System.out.println(getGetResponse());
    }*/

}

