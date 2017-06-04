package com.example.haut_client;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	// 登录处理类
	ClientAction loginaction = new ClientAction();

	// 用户名密码
	String username = null;
	String password = null;
	String ac_id = null;
	// 登录和注销
	Button button1 = null;
	Button button2 = null;

	//程序运行开头
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//下面是改变系统机制，让主线程可以使用网络访问
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		/*if (Build.VERSION.SDK_INT >= 11) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
			}*/
		//StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		//StrictMode.setThreadPolicy(policy);

		
		
		// 获取button
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
			
		

		// 提示用户使用教程	
		//System.out.println(loginaction.getUserInfo());
		if (true) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("使用说明")
					// 设置对话框标题

					.setMessage("（此客户端为精简版，只提供登录和注销功能）\n使用教程：\n 1.路由器使用方法：宿舍网线插到路由器wan口，并将路由器wan口设置成动态获取ip（不会的自行百度），然后手机连上路由器WiFi，打开客户端，输入账号和密码，acid一栏通常是2或者1，点击登录，如果出现INFO failed,BAS……，" +
							"则更换acid值，然后重新登录。\n2.校园内haut无线热点使用方法：手机连上校园无线热点，然后登录，方法同上。\n3.注意事项：如果未连接路由器或者路由器没插上网线或者没连接校园无线，请不要点击登录，否则程序会卡死。\n4.注销，输入账号点击注销即可\n" +
							"5.程序只是娱乐写下，优化不够，还请大神勿喷哈哈^-^ 如有问题还请联系")
					// 设置显示的内容

					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {// 添加确定按钮

								@Override
								public void onClick(DialogInterface dialog,
										int which) {// 确定按钮的响应事件

									// TODO Auto-generated method stub
									
									//button1.setClickable(false);//将登录按钮不可点击
									//button2.setClickable(true);//将注销按钮可点击

								}

							})
					.setNegativeButton("退出",
							new DialogInterface.OnClickListener() {// 添加返回按钮

								@Override
								public void onClick(DialogInterface dialog,
										int which) {// 响应事件

									// TODO Auto-generated method stub
									finish();
									// Log.i("alertdialog", " 请保存数据！");

								}

							}).show();// 在按键响应事件中显示此对话框
		}

		// 添加登录监听事件
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/*String username2 = ((EditText) findViewById(R.id.editText1))
						.getText().toString();

				Log.v("debug", "2");
				System.out.println("您点击了Button1");
				System.out.println(username2);*/

				// 获取控件值
				username = ((EditText) findViewById(R.id.editText1)).getText()
						.toString();
				password = ((EditText) findViewById(R.id.editText2)).getText()
						.toString();
				ac_id = ((EditText) findViewById(R.id.editText3)).getText()
						.toString();

				// 用于接收登录的返回结果
				String result;

				try {
					
					
					
					//判断是否登录
					if(isLogin())
					{
						return;//如果登录了就返回，不再往下执行
					}			
					
					

					result = loginaction.Login(username,password.toCharArray(), ac_id);// 登录
					if (result.equals("success"))// 登录成功
					{
						new AlertDialog.Builder(MainActivity.this)
								.setTitle("系统提示-登录")
								// 设置对话框标题

								.setMessage("登录成功！")
								// 设置显示的内容

								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {// 添加确定按钮

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {// 确定按钮的响应事件

												// TODO Auto-generated method
												// stub
												//button1.setClickable(false);//将登录按钮不可点击
												button2.setClickable(true);//将注销按钮可用
												//finish();

											}

										})
								.setNegativeButton("退出",
										new DialogInterface.OnClickListener() {// 添加返回按钮

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {// 响应事件

												// TODO Auto-generated method
												// stub
												finish();
												// Log.i("alertdialog",
												// " 请保存数据！");

											}

										}).show();// 在按键响应事件中显示此对话框
					} else// 登录失败
					{
						new AlertDialog.Builder(MainActivity.this)
								.setTitle("系统提示-登录")
								// 设置对话框标题

								.setMessage("登录失败！失败信息为：" + result)
								// 设置显示的内容

								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {// 添加确定按钮

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {// 确定按钮的响应事件

												// TODO Auto-generated method
												// stub
												button1.setClickable(true);//将登录按钮可点击
												//button2.setClickable(false);//将注销按钮bu可用
												//finish();

											}

										})
								.setNegativeButton("退出",
										new DialogInterface.OnClickListener() {// 添加返回按钮

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {// 响应事件

												// TODO Auto-generated method
												// stub
												finish();
												// Log.i("alertdialog",
												// " 请保存数据！");

											}

										}).show();// 在按键响应事件中显示此对话框
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("登录总异常");
					finish();
				}
			}
		});

		
		// 添加注销监听事件
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//String username2 = ((EditText) findViewById(R.id.editText1)).getText().toString();

				//Log.v("debug", "2");
				//System.out.println("您点击了Button1");
				//System.out.println(username2);

				// 获取控件值
				username = ((EditText) findViewById(R.id.editText1)).getText()
						.toString();
				/*password = ((EditText) findViewById(R.id.editText2)).getText()
						.toString();
				ac_id = ((EditText) findViewById(R.id.editText3)).getText()
						.toString();*/

				// 用于接收注销的返回结果
				String result;

				
				try {
				
					result = loginaction.disconnect(username);//注销
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("系统提示-注销")
					// 设置对话框标题

					.setMessage("注销结果：" + result)
					// 设置显示的内容

					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {// 添加确定按钮

								@Override
								public void onClick(
										DialogInterface dialog,
										int which) {// 确定按钮的响应事件

									// TODO Auto-generated method
									// stub
									button1.setClickable(true);//将登录按钮可用
									//button2.setClickable(false);//将注销按钮不可用
									//finish();

								}

							})
					.setNegativeButton("退出",
							new DialogInterface.OnClickListener() {// 添加返回按钮

								@Override
								public void onClick(
										DialogInterface dialog,
										int which) {// 响应事件

									// TODO Auto-generated method
									// stub
									finish();
									// Log.i("alertdialog",
									// " 请保存数据！");

								}

							}).show();// 在按键响应事件中显示此对话框
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					finish();
				}
			}
		});
		

	}
	
	
	//检测是否登录过
    public boolean isLogin()
    {
    	if (loginaction.getUserInfo().equals("logined")) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("系统提示")
					// 设置对话框标题

					.setMessage("已经登录！")
					// 设置显示的内容

					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {// 添加确定按钮

								@Override
								public void onClick(DialogInterface dialog,
										int which) {// 确定按钮的响应事件

									// TODO Auto-generated method stub
									
									//button1.setClickable(false);//将登录按钮不可点击
									button2.setClickable(true);//将注销按钮可点击
								}

							})
					.setNegativeButton("退出",
							new DialogInterface.OnClickListener() {// 添加返回按钮

								@Override
								public void onClick(DialogInterface dialog,
										int which) {// 响应事件

									// TODO Auto-generated method stub
									finish();
									// Log.i("alertdialog", " 请保存数据！");

								}

							}).show();// 在按键响应事件中显示此对话框
			return true;
		}
    	else
    	{
    		return false;
    	}
    	

    }
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
