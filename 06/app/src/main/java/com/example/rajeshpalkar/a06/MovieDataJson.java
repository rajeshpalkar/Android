package com.example.rajeshpalkar.a06;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MovieDataJson {

    List<Map<String, ?>> moviesList;
    HashMap map2 = null;
    public List<Map<String, ?>> getMoviesList() {
        return moviesList;
    }

    public int getSize()
    {
        return moviesList.size();
    }

    public HashMap getItem(int i)
    {
        if (i >= 0 && i < moviesList.size()) {
            return (HashMap) moviesList.get(i);
        } else return null;
    }


    public MovieDataJson()
    {
        moviesList = new ArrayList<Map<String, ?>>();
    }


    private HashMap createMovie(String id, String url , String name ,String description)
    {
        HashMap movie = new HashMap();
        movie.put("id", id);
        movie.put("name", name);
        movie.put("description", description);
        movie.put("url", url);
        return movie;
    }


    public void downloadSingleJson(String url) throws JSONException {
        String json = MyUtility.downloadJSONusingHTTPGetRequest(url);

        if(json!=null)
        {
            JSONObject jsonArray = new JSONObject(json);
            HashMap<String, Object> map = new HashMap<String, Object>();

            Iterator<String> keysItr = jsonArray.keys();
            while(keysItr.hasNext())
            {
                String key = keysItr.next();
                Object value = jsonArray.get(key);


                map.put(key, value);

            }
            map2 = map;
        }
        else
        {
            Log.i("in null","json");
        }
    }



 public void downloadMovieDataJson(String url)
 {
    moviesList.clear();
    String jsonString = MyUtility.downloadJSONusingHTTPGetRequest(url);
    if (jsonString != null)
    {
      int length = jsonString.length();
      if (jsonString.charAt(length - 1) != ']')
      {
          jsonString = jsonString + "}]";
      }

  try
     {
       JSONObject object = new JSONObject(jsonString);
       JSONArray movieJson = (JSONArray) object.get("results");
       int jsonLength = movieJson.length();
       for (int i = 0; i < jsonLength; i++)
       {
       JSONObject movieJsonObj = movieJson.getJSONObject(i);
       moviesList.add(createMovie(movieJsonObj.getString("id"), "http://image.tmdb.org/t/p/w154/"+ movieJsonObj.getString("poster_path"),movieJsonObj.getString("title"),movieJsonObj.getString("overview")));
       }
      }
      catch (Exception ex)
      {
                Log.d("JSON Download error", ex + "");
      }
    }
}






}
