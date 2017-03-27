package com.qz.app.utils;

import android.content.Context;

import com.qz.app.App;
import com.qz.app.entity.Userinfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManagers {

	/**
	 * 我的信息保存文件
	 */
	private static final String MYDATA = "mylogindata.lw";
	/**
	 * token文件保存文件
	 */
	private static final String TOKENFILE = "tokenofuser.lw";
	private static final String TOKENFILEIMG = "tokenofuserimg.lw";

	/**
	 * 用户位置文件
	 */
	private static final String USER_POS = "userpos.lw";

	/**
	 * 加入库里的item
	 */
	private static final String COMPARE_LIST = "COMPARE_LIST.lw";

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @throws Exception
	 */
	public static void saveMyData(Userinfo user) throws Exception {
		saveDatas(App.mContext, user, MYDATA);
	}

	/**
	 * 读取用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Userinfo getMyData() throws Exception {
		return (Userinfo) getDatas(App.mContext, MYDATA);
	}

	/**
	 * 获取token
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getToken() throws Exception {
		return (String) getDatas(App.mContext, TOKENFILE);

	}

	public static String getImgToken() throws Exception {
		return (String) getDatas(App.mContext, TOKENFILEIMG);

	}

	/**
	 * 保存token
	 * 
	 * @param token
	 * @throws Exception
	 */
	public static void saveToken(String token) throws Exception {
		saveDatas(App.mContext, token, TOKENFILE);
	}


	/**
	 * 保存token
	 *
	 * @param token
	 * @throws Exception
	 */
	public static void saveImgToken(String token) throws Exception {
		saveDatas(App.mContext, token, TOKENFILEIMG);
	}



//	public static void saveLocation(LocationData datas) throws Exception {
//		saveDatas(App.mContext, datas, USER_POS);
//	}

//	public static LocationData getLocationData() {
//		return (LocationData) getDatas(App.getContext(), USER_POS);
//	}

	public static void saveDatas(Context context, Object obj, String fileName)
			throws Exception {
		// File rootFile = Environment.getExternalStorageDirectory();
		// File pathfile = new File(rootFile,path);
		// if(!pathfile.exists())
		// pathfile.mkdirs();
		// File file = new File(rootFile,fileName);
		// if(!file.exists())
		// file.createNewFile();
		// FileOutputStream fout = new FileOutputStream(file);
		FileOutputStream fos = context.openFileOutput(fileName,
				Context.MODE_PRIVATE);
		ObjectOutputStream objOutSteam = new ObjectOutputStream(fos);
		objOutSteam.writeObject(obj);
		fos.close();
		objOutSteam.close();

	}

	public static Object getDatas(Context context, String fileName) {
		try {
			// File rootFile = Environment.getExternalStorageDirectory();
			// File pathfile = new File(rootFile,path);
			// if(!pathfile.exists())
			// return null;
			// File file = new File(rootFile,fileName);
			// FileInputStream fout = new FileInputStream(file);
			FileInputStream in = context.openFileInput(fileName);
			if (null == in)
				return null;
			ObjectInputStream objOutSteam = new ObjectInputStream(in);

			Object obj = objOutSteam.readObject();
			in.close();
			objOutSteam.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			L.v("fileutils", "getDatas", "getDatas fail");
		}
		return null;
	}
}
