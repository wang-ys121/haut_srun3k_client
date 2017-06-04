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

	// ��¼������
	ClientAction loginaction = new ClientAction();

	// �û�������
	String username = null;
	String password = null;
	String ac_id = null;
	// ��¼��ע��
	Button button1 = null;
	Button button2 = null;

	//�������п�ͷ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//�����Ǹı�ϵͳ���ƣ������߳̿���ʹ���������
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		/*if (Build.VERSION.SDK_INT >= 11) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
			}*/
		//StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		//StrictMode.setThreadPolicy(policy);

		
		
		// ��ȡbutton
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
			
		

		// ��ʾ�û�ʹ�ý̳�	
		//System.out.println(loginaction.getUserInfo());
		if (true) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("ʹ��˵��")
					// ���öԻ������

					.setMessage("���˿ͻ���Ϊ����棬ֻ�ṩ��¼��ע�����ܣ�\nʹ�ý̳̣�\n 1.·����ʹ�÷������������߲嵽·����wan�ڣ�����·����wan�����óɶ�̬��ȡip����������аٶȣ���Ȼ���ֻ�����·����WiFi���򿪿ͻ��ˣ������˺ź����룬acidһ��ͨ����2����1�������¼���������INFO failed,BAS������" +
							"�����acidֵ��Ȼ�����µ�¼��\n2.У԰��haut�����ȵ�ʹ�÷������ֻ�����У԰�����ȵ㣬Ȼ���¼������ͬ�ϡ�\n3.ע��������δ����·��������·����û�������߻���û����У԰���ߣ��벻Ҫ�����¼���������Ῠ����\n4.ע���������˺ŵ��ע������\n" +
							"5.����ֻ������д�£��Ż���������������������^-^ �������⻹����ϵ")
					// ������ʾ������

					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {// ���ȷ����ť

								@Override
								public void onClick(DialogInterface dialog,
										int which) {// ȷ����ť����Ӧ�¼�

									// TODO Auto-generated method stub
									
									//button1.setClickable(false);//����¼��ť���ɵ��
									//button2.setClickable(true);//��ע����ť�ɵ��

								}

							})
					.setNegativeButton("�˳�",
							new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

								@Override
								public void onClick(DialogInterface dialog,
										int which) {// ��Ӧ�¼�

									// TODO Auto-generated method stub
									finish();
									// Log.i("alertdialog", " �뱣�����ݣ�");

								}

							}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���
		}

		// ��ӵ�¼�����¼�
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/*String username2 = ((EditText) findViewById(R.id.editText1))
						.getText().toString();

				Log.v("debug", "2");
				System.out.println("�������Button1");
				System.out.println(username2);*/

				// ��ȡ�ؼ�ֵ
				username = ((EditText) findViewById(R.id.editText1)).getText()
						.toString();
				password = ((EditText) findViewById(R.id.editText2)).getText()
						.toString();
				ac_id = ((EditText) findViewById(R.id.editText3)).getText()
						.toString();

				// ���ڽ��յ�¼�ķ��ؽ��
				String result;

				try {
					
					
					
					//�ж��Ƿ��¼
					if(isLogin())
					{
						return;//�����¼�˾ͷ��أ���������ִ��
					}			
					
					

					result = loginaction.Login(username,password.toCharArray(), ac_id);// ��¼
					if (result.equals("success"))// ��¼�ɹ�
					{
						new AlertDialog.Builder(MainActivity.this)
								.setTitle("ϵͳ��ʾ-��¼")
								// ���öԻ������

								.setMessage("��¼�ɹ���")
								// ������ʾ������

								.setPositiveButton("ȷ��",
										new DialogInterface.OnClickListener() {// ���ȷ����ť

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {// ȷ����ť����Ӧ�¼�

												// TODO Auto-generated method
												// stub
												//button1.setClickable(false);//����¼��ť���ɵ��
												button2.setClickable(true);//��ע����ť����
												//finish();

											}

										})
								.setNegativeButton("�˳�",
										new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {// ��Ӧ�¼�

												// TODO Auto-generated method
												// stub
												finish();
												// Log.i("alertdialog",
												// " �뱣�����ݣ�");

											}

										}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���
					} else// ��¼ʧ��
					{
						new AlertDialog.Builder(MainActivity.this)
								.setTitle("ϵͳ��ʾ-��¼")
								// ���öԻ������

								.setMessage("��¼ʧ�ܣ�ʧ����ϢΪ��" + result)
								// ������ʾ������

								.setPositiveButton("ȷ��",
										new DialogInterface.OnClickListener() {// ���ȷ����ť

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {// ȷ����ť����Ӧ�¼�

												// TODO Auto-generated method
												// stub
												button1.setClickable(true);//����¼��ť�ɵ��
												//button2.setClickable(false);//��ע����ťbu����
												//finish();

											}

										})
								.setNegativeButton("�˳�",
										new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {// ��Ӧ�¼�

												// TODO Auto-generated method
												// stub
												finish();
												// Log.i("alertdialog",
												// " �뱣�����ݣ�");

											}

										}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("��¼���쳣");
					finish();
				}
			}
		});

		
		// ���ע�������¼�
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//String username2 = ((EditText) findViewById(R.id.editText1)).getText().toString();

				//Log.v("debug", "2");
				//System.out.println("�������Button1");
				//System.out.println(username2);

				// ��ȡ�ؼ�ֵ
				username = ((EditText) findViewById(R.id.editText1)).getText()
						.toString();
				/*password = ((EditText) findViewById(R.id.editText2)).getText()
						.toString();
				ac_id = ((EditText) findViewById(R.id.editText3)).getText()
						.toString();*/

				// ���ڽ���ע���ķ��ؽ��
				String result;

				
				try {
				
					result = loginaction.disconnect(username);//ע��
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("ϵͳ��ʾ-ע��")
					// ���öԻ������

					.setMessage("ע�������" + result)
					// ������ʾ������

					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {// ���ȷ����ť

								@Override
								public void onClick(
										DialogInterface dialog,
										int which) {// ȷ����ť����Ӧ�¼�

									// TODO Auto-generated method
									// stub
									button1.setClickable(true);//����¼��ť����
									//button2.setClickable(false);//��ע����ť������
									//finish();

								}

							})
					.setNegativeButton("�˳�",
							new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

								@Override
								public void onClick(
										DialogInterface dialog,
										int which) {// ��Ӧ�¼�

									// TODO Auto-generated method
									// stub
									finish();
									// Log.i("alertdialog",
									// " �뱣�����ݣ�");

								}

							}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					finish();
				}
			}
		});
		

	}
	
	
	//����Ƿ��¼��
    public boolean isLogin()
    {
    	if (loginaction.getUserInfo().equals("logined")) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("ϵͳ��ʾ")
					// ���öԻ������

					.setMessage("�Ѿ���¼��")
					// ������ʾ������

					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {// ���ȷ����ť

								@Override
								public void onClick(DialogInterface dialog,
										int which) {// ȷ����ť����Ӧ�¼�

									// TODO Auto-generated method stub
									
									//button1.setClickable(false);//����¼��ť���ɵ��
									button2.setClickable(true);//��ע����ť�ɵ��
								}

							})
					.setNegativeButton("�˳�",
							new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

								@Override
								public void onClick(DialogInterface dialog,
										int which) {// ��Ӧ�¼�

									// TODO Auto-generated method stub
									finish();
									// Log.i("alertdialog", " �뱣�����ݣ�");

								}

							}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���
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
