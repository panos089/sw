package com.example.fb_demo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

public class MainActivity extends FragmentActivity {

	private LoginButton loginBtn;
	private TextView username;
	private UiLifecycleHelper uiHelper;
	  String bad_loc_pattern = "(hell|heaven|paradise|home|road|mother|fairytail|universe|mars|jupiter|moon|planet|galaxy|house|mom|mercury|venus|saturn|uranus|neptune|pluto|sun|solar|asteroid|comet)";
	  String flu_pattern="()";
	  String aggrav_pattern = "(getting worse|get worse|weaker|deterioration|deteriorate|worsening|degenerate|regress|exacerbate|relapse|intensify|compound|Become aggravated|get into a decline|go to pot)";
		private String TAG_SUCCESS = "success";
		String url;
		
	  @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		username = (TextView) findViewById(R.id.username);
		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
		loginBtn.setReadPermissions(Arrays.asList("user_posts"));
		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				if (user != null) {
					username.setText("You are currently logged in as " + user.getName());
				} else {
					username.setText("You are not logged in.");
				}
			}
		});
		
		
	}
	private Session.StatusCallback statusCallback = new Session.StatusCallback() {

		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {
				Log.d("MainActivity", "Facebook session opened.");
				new Request(session, "/me/posts",
			            null,
			            HttpMethod.GET,
			            new Request.Callback() {
					public void onCompleted(Response response) {

			                    try
			                    {
			                        Pattern flu_symptoms = Pattern.compile("flu|chills|sore|throat|headache|runny|nose|vomiting|sneazing|fever|diarrhea|dry|cough");
			                        Pattern bad_locations = Pattern.compile("hell|heaven|paradise|home|road|mother|fairytail|universe|mars|jupiter|moon|planet|galaxy|house|mom|mercury|venus|saturn|uranus|neptune|pluto|sun|solar|asteroid|comet");

			                        GraphObject user_feed  = response.getGraphObject();
			                        JSONObject jsonObj1=user_feed.getInnerJSONObject();
			                        JSONArray jsonArr = jsonObj1.getJSONArray("data");
			                        JSONArray data = new JSONArray();

			                      //reset jsonarray
			                        //data={};
			                        for(int i=0;i<jsonArr.length();i++)
			                        {
			                        	JSONObject jsonObj2=jsonArr.getJSONObject(i);
			                            JSONObject args = new JSONObject();
			                            try {
			                            	JSONObject place = jsonObj2.getJSONObject("place");
			                            	JSONObject coordinates=place.getJSONObject("location");
			                            	JSONObject user_info=jsonObj2.getJSONObject("from");
			                            	Matcher m1 = flu_symptoms.matcher(jsonObj2.getString("message"));
			                                Matcher m2 = bad_locations.matcher(place.getString("name"));

			                                if (m1.find() && !m2.find()) {
					                            //	Log.v("user_name",user_info.getString("name"));
					                            //	Log.v("user_id",user_info.getString("id"));
					                            //	Log.v("msg",jsonObj2.getString("message"));
					                            //	Log.v("post_id",jsonObj2.getString("id"));
					                            //	Log.v("date",jsonObj2.getString("updated_time"));
				                            	//	Log.v("latitude",coordinates.getString("latitude"));
				                            	//	Log.v("longitude",coordinates.getString("longitude"));

			                                	args.put("user_name",user_info.getString("name"));
			                                	args.put("user_id",user_info.getString("id"));
			                                	args.put("msg", jsonObj2.getString("message"));
			                                	args.put("post_id",jsonObj2.getString("id"));
			                                	args.put("date",jsonObj2.getString("updated_time"));
			                                	args.put("latitude",coordinates.getString("latitude"));
			                                	args.put("longitude",coordinates.getString("longitude"));
				                            		data.put(args);
			                            	}
			                            }
			                            catch (JSONException e)
					                    {
					                 //     e.printStackTrace();
					                    }   
			                         
			                        }
			      				  new HttpAsyncTask().execute(data);

			                    } catch (JSONException e)
			                    {
			                    //   e.printStackTrace();
			                    }
			}
			            }
			        ).executeAsync();


				
			} else if (state.isClosed()) {
				Log.d("MainActivity", "Facebook session closed.");
			}
		}

	};
	
    private class HttpAsyncTask extends AsyncTask<JSONArray, Void, Void> {
    	String url="http://cedar-defender-93011.appspot.com/submit.php";
        @Override
        protected Void doInBackground(JSONArray... params) {
			List<NameValuePair> args = new ArrayList<NameValuePair>();
			Log.v("json array is",params[0].toString());
			args.add(new BasicNameValuePair("json", params[0].toString()));
			JSONObject json = POST(url, args);
		//check response
				return null;

        }
      
    }
	
    public static JSONObject POST(String url, List<NameValuePair> params) {
		InputStream is = null;
		JSONObject jObj = null;
		String json = "";
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 15000);
		HttpConnectionParams.setSoTimeout(client.getParams(), 15000);
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPost);
			if (response != null) {
				is = response.getEntity().getContent();
				json = convertInputStreamToString(is);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		return jObj;

	}


	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null) {
			result += line;
		}
		inputStream.close();
		return result;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}
}