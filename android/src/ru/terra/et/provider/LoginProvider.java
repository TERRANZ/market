package ru.terra.et.provider;

import org.apache.http.message.BasicNameValuePair;

import ru.terra.et.core.constants.Constants;
import ru.terra.et.core.constants.URLConstants;
import ru.terra.et.core.network.JsonAbstractProvider;
import ru.terra.et.core.network.dto.LoginDTO;
import ru.terra.et.util.Logger;
import ru.terra.et.util.SettingsUtil;
import android.app.Activity;

import com.google.gson.Gson;

public class LoginProvider extends JsonAbstractProvider
{
	private final static String TAG = "LoginProvider";

	public LoginProvider(Activity c)
	{
		super(c);
	}

	public boolean login(String user, String pass)
	{
		String json = httpReqHelper.runJsonRequest(URLConstants.DoJson.Login.LOGIN_DO_LOGIN_JSON, new BasicNameValuePair(
				URLConstants.DoJson.Login.LOGIN_PARAM_USER, user), new BasicNameValuePair(URLConstants.DoJson.Login.LOGIN_PARAM_PASS, pass));
		Logger.i("login", json);
		LoginDTO dto = new Gson().fromJson(json, LoginDTO.class);
		if (dto.logged)
		{
			SettingsUtil.saveSetting(cntxActivity, Constants.CONFIG_SESSION, dto.session);
			Logger.i("login", dto.session);
			json = httpReqHelper.runSimpleJsonRequest(URLConstants.DoJson.Login.LOGIN_DO_GET_MY_ID);
			dto = new Gson().fromJson(json, LoginDTO.class);
			SettingsUtil.saveSetting(cntxActivity, Constants.CONFIG_UID, dto.id.toString());
			return true;
		}
		else
			return false;
	}

	public boolean register(String user, String pass)
	{
		String json = httpReqHelper.runJsonRequest(URLConstants.DoJson.Login.LOGIN_DO_REGISTER_JSON, new BasicNameValuePair(
				URLConstants.DoJson.Login.LOGIN_PARAM_USER, user), new BasicNameValuePair(URLConstants.DoJson.Login.LOGIN_PARAM_PASS, pass));
		LoginDTO dto = new Gson().fromJson(json, LoginDTO.class);
		if (dto.logged)
		{
			login(user, pass);
			return true;
		}
		else
			return false;
	}

	public void doStoredLogin()
	{

	}

}
