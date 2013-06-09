package edu.cui.wineapp.models.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import edu.cui.wineapp.models.Comment;
import edu.cui.wineapp.models.User;
import edu.cui.wineapp.models.managers.UserManager;

public class UserDAO {
	private static Context context = null;
	private UserDAO(Context context) {
		this.context = context;
	}
	public static UserDAO getUserDAO(Context context) {
		return new UserDAO(context);
	}

	public ArrayList<User> parseUserXML(String preParsed){    	
		JSONArray winesJSON = null;
		ArrayList<User> thisComments=new ArrayList<User>();
		// Log.i("TRY",preParsed);
		try {
			JSONObject myJSON = new JSONObject(preParsed);
			winesJSON = myJSON.getJSONArray("users");
			//Log.i("TRY","made it past myJSON");
			//Log.i("DEBUG",winesJSON.toString());
			for(int i = 0; i < winesJSON.length(); i++){
				JSONObject currentWine = winesJSON.getJSONObject(i);

				User newUser;
				//Log.e("DEBUG",currentWine.getString("comment"));
				newUser = new User(
						//currentWine.getString(TAG_AVIN),
						currentWine.getString("name"),
						Integer.parseInt(currentWine.getString("age")),
						Float.parseFloat(currentWine.getString("weight")),
						currentWine.getString("email"),
						currentWine.getString("sex"),
						currentWine.getString("country"),
						currentWine.getString("photourl"),
						currentWine.getString("userid")
						);

				// Log.i("DEBUG",newWine.toString());
				thisComments.add(newUser);
			}
		} catch (JSONException e) {e.printStackTrace();}
		return thisComments;
	}

	public User createUserOnServer(String name, int age, float weight, String email, String sex, String country, String photourl, String userid,String pass) throws UnsupportedEncodingException{
		String url="http://hello-zhaoyang-udacity.appspot.com/users";
		url+="?command=signup";
		url+="&userid="+URLEncoder.encode(userid,"UTF-8");
		url+="&age="+URLEncoder.encode(Integer.toString(age),"UTF-8");
		url+="&weight="+URLEncoder.encode(Integer.toString((int)weight),"UTF-8");
		url+="&email="+URLEncoder.encode(email,"UTF-8");
		url+="&sex="+URLEncoder.encode(sex,"UTF-8");
		url+="&country="+URLEncoder.encode(country,"UTF-8");
		url+="&photourl="+URLEncoder.encode(photourl,"UTF-8");
		url+="&name="+URLEncoder.encode(name,"UTF-8");
		url+="&password="+URLEncoder.encode(pass,"UTF-8");

		String response = "ERROR";
		try {
			response = uploadUrl(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return parseUserXML(response).get(0);
	}


	public boolean changeAge(String newAge) throws UnsupportedEncodingException{
		String url="http://hello-zhaoyang-udacity.appspot.com/users";
		url+="?command=setting";
		url+="&userid="+URLEncoder.encode(UserManager.getUserManager(context).getLocalUser().getId(),"UTF-8");
		url+="&colum=age";
		url+="&newvalue="+URLEncoder.encode(newAge,"UTF-8");

		String response = "ERROR";
		boolean isValid = true;
		try {
			response = uploadUrl(url);
			isValid = true;

		} catch (IOException e) {
			isValid = false;
		}

		return isValid;
	}

	public boolean changeWeight(String newWeight) throws UnsupportedEncodingException{
		String url="http://hello-zhaoyang-udacity.appspot.com/users";
		url+="?command=setting";
		url+="&userid="+URLEncoder.encode(UserManager.getUserManager(context).getLocalUser().getId(),"UTF-8");
		url+="&colum=weight";
		url+="&newvalue="+URLEncoder.encode(newWeight,"UTF-8");

		String response = "ERROR";
		boolean isValid = true;
		try {
			response = uploadUrl(url);
			isValid = true;

		} catch (IOException e) {
			isValid = false;
		}

		return isValid;
	}

	public boolean changeSex(String newSex) throws UnsupportedEncodingException{
		String url="http://hello-zhaoyang-udacity.appspot.com/users";
		url+="?command=setting";
		url+="&userid="+URLEncoder.encode(UserManager.getUserManager(context).getLocalUser().getId(),"UTF-8");
		url+="&colum=sex";
		url+="&newvalue="+URLEncoder.encode(newSex,"UTF-8");

		String response = "ERROR";
		boolean isValid = true;
		try {
			response = uploadUrl(url);
			isValid = true;

		} catch (IOException e) {
			isValid = false;
		}

		return isValid;
	}

	public boolean changeCountry(String newCountry) throws UnsupportedEncodingException{
		String url="http://hello-zhaoyang-udacity.appspot.com/users";
		url+="?command=setting";
		url+="&userid="+URLEncoder.encode(UserManager.getUserManager(context).getLocalUser().getId(),"UTF-8");
		url+="&colum=country";
		url+="&newvalue="+URLEncoder.encode(newCountry,"UTF-8");

		String response = "ERROR";
		boolean isValid = true;
		try {
			response = uploadUrl(url);
			isValid = true;

		} catch (IOException e) {
			isValid = false;
		}

		return isValid;
	}

	public boolean changePhotourl(String newPhotourl) throws UnsupportedEncodingException{
		String url="http://hello-zhaoyang-udacity.appspot.com/users";
		url+="?command=setting";
		url+="&userid="+URLEncoder.encode(UserManager.getUserManager(context).getLocalUser().getId(),"UTF-8");
		url+="&colum=photourl";
		url+="&newvalue="+URLEncoder.encode(newPhotourl,"UTF-8");

		String response = "ERROR";
		boolean isValid = true;
		try {
			response = uploadUrl(url);


			isValid = true;

		} catch (IOException e) {
			isValid = false;
		}

		return isValid;
	}

	public ArrayList<User> loginServer(String userid,String pass){
		String url = "http://hello-zhaoyang-udacity.appspot.com/users";
		url+="?command=login";


		try {
			url+="&userid="+URLEncoder.encode(userid,"UTF-8");
			//				url+="&passwrod="+URLEncoder.encode(pass,"UTF-8");
			url+="&password="+URLEncoder.encode(pass,"UTF-8");

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String response = "ERROR";

		try {
			response = uploadUrl(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return parseUserXML(response);

	}
	private String uploadUrl(String myurl) throws IOException {
		InputStream is = null;
		try {
			URL url = new URL(myurl);
			URI uri = null;



			try {
				uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}


			url = uri.toURL();


			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.connect();
			// Log.e("DEBUG", "The response is: " + response);



			is = conn.getInputStream();

			Log.e("INSIDE","HERE");	


			String contentAsString =readIt(is);
			return contentAsString;

		} finally {if (is != null) {is.close();} }
	}
	private String downloadUrl(String myurl) throws IOException {
		InputStream is = null;
		Log.e("DAO.java/downloadUrl","MADE IT");
		try {
			URL url = new URL(myurl);
			URI uri = null;
			try {
				uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			url = uri.toURL();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			// Log.e("DEBUG", "The response is: " + response);
			is = conn.getInputStream();

			String contentAsString = readIt(is);
			return contentAsString;

		} finally {if (is != null) {is.close();} }
	}
	public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			Log.e("DEBUG_DAO",line);
			sb.append(line + "\n");
		}
		reader.close();
		String result = sb.toString();
		return result;
	}

	//////////

	public void setComment(String winecode, String user_id,String comment) throws UnsupportedEncodingException{
		String url="http://hello-zhaoyang-udacity.appspot.com/comments";
		url+="?wine="+URLEncoder.encode(winecode,"UTF-8");
		url+="&user="+URLEncoder.encode(user_id,"UTF-8");
		url+="&comment="+URLEncoder.encode(comment,"UTF-8");
		String response = "ERROR";
		Log.e("COMMENT","ENTER");
		try {
			response = uploadUrl(url);
			Log.e("COMMENT",response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Comment> getCommentsByQuery(String q){
		String url="http://hello-zhaoyang-udacity.appspot.com/comments";
		url=url+"?q="+q;
		String response="";
		//response=new DownloadWebpageText().execute(url);		//May cause issues
		try {
			response= downloadUrl(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parseComments(response);
	}

	private ArrayList<Comment> parseComments(String response) {
		JSONArray winesJSON = null;
		ArrayList<Comment> thisComments=new ArrayList<Comment>();
		// Log.i("TRY",preParsed);
		try {
			JSONObject myJSON = new JSONObject(response);
			winesJSON = myJSON.getJSONArray("comments");
			//Log.i("TRY","made it past myJSON");
			//Log.i("DEBUG",winesJSON.toString());
			for(int i = 0; i < winesJSON.length(); i++){
				JSONObject currentWine = winesJSON.getJSONObject(i);

				//URLDecoder.decode(s)
				
				Comment newComment;
				//Log.e("DEBUG",currentWine.getString("comment"));
				try {
					newComment = new Comment(
							//currentWine.getString(TAG_AVIN),
							currentWine.getString("winecode"),
							currentWine.getString("userid"),
							URLDecoder.decode(currentWine.getString("comment"),"UTF-8"),
							currentWine.getString("date")
							//currentWine.getString("id")
							);
				} catch (UnsupportedEncodingException e) {
					newComment = new Comment(
							//currentWine.getString(TAG_AVIN),
							currentWine.getString("winecode"),
							currentWine.getString("userid"),
							currentWine.getString("comment"),
							currentWine.getString("date")
							//currentWine.getString("id")
							);
					e.printStackTrace();
				}

				// Log.i("DEBUG",newWine.toString());
				thisComments.add(newComment);
			}
		} catch (JSONException e) {e.printStackTrace();}
		return thisComments;
	}


}
