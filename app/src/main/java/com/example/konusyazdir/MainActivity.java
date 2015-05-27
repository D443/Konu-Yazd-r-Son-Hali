package com.example.konusyazdir;

import android.support.v7.app.ActionBarActivity;
import java.util.ArrayList;


import android.app.Activity; 
import android.content.ActivityNotFoundException; 
import android.content.Intent; 
import android.os.Bundle; 
import android.speech.RecognizerIntent; 
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener; 
import android.widget.ImageButton; 
import android.widget.TextView; 
import android.widget.Toast;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	protected static final int RESULT_SPEECH = 1;//konusma sonucuna varsayilan olarak 1 atanarak deger atamalari gerceklestirilir
	public TextView txtYazi;
	private ImageButton btnKonus;
//tanimlamalar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//Android in calısma methodu
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
        btnKonus=(ImageButton)findViewById(R.id.btnKonus);
        txtYazi=(TextView)findViewById(R.id.txtYazi);
        //buton ve metin kutusu tanimlamalari
        
      //butonumuzu ve yazi alanimizi tanimladik. simdi butona tıklayinca gerceklesecek kodlar...
        
        btnKonus.setOnClickListener(new View.OnClickListener() {
         
        public void onClick(View v) {
         //listener bekleyici click olduğunda yapılacaklar konusma butonuna tıklandığı zaman
        // TODO Auto-generated method stub
         
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            //intent sayfalar arası geçiş yapıyoruz konusma sayfasina geciyoruz
         
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "tr");
            //baska bir fonksiyon arası gecis yapiyoruz  dilin ayarlamasini yapiyoruz
        //yapilacak islemler icin gerekli intent tanimlamasi ve kodu
         
        try {
         
        startActivityForResult(intent, RESULT_SPEECH);
         //startActivity fonksiyonuna kayit ettigimiz sesi gonderiyoruz
        //startActivityForResult metodu ile donen degeri aldiracagiz.
         
        txtYazi.setText("");
            //yaziyi en basta bos getirir
         
        } catch (ActivityNotFoundException a) {
        //hata verirse calisacak kisim
        Toast t = Toast.makeText(getApplicationContext(),
         //ekrana mesaj yazdirma c# da ki mesaj box gibi
        "Aygitiniz bu uygulamayi desteklemiyor",
         
        Toast.LENGTH_SHORT);
            //mesajin uzunlugu kadar gosterim suresi ayarlamasi yapiliyor
         
        t.show();
         
        }
         
        }
         
        });
        
        //mail
        //mail butonu click oldugu zaman olacak islemler
        ((Button) findViewById(R.id.send_mail)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

              final String mesaj = txtYazi.getText().toString();  ////txt ye girilen deger okunur

               Intent intent = new Intent(Intent.ACTION_SEND);// gonder methoduna gidiyoruz
               intent.setType("text/html");//yolladigimiz veri tipini tanimliyoruz
               intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });//gonderecegimiz mail adresi programdan girilmis
               intent.putExtra(Intent.EXTRA_SUBJECT, "");// mail de konu olarak secilecek deger
               intent.putExtra(Intent.EXTRA_TEXT, mesaj);//gonderilecek maildeki mesaj
               startActivity(Intent.createChooser(intent, "Send Email"));//mail gonderilme islemi yapiliyor

               /* Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.putExtra("sms_body","Ja")*/

            }
         });
        
        //
        
        
        //sms atma begin
        ((Button) findViewById(R.id.send_sms)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               String smsNumber = "";//programdan gonderilecek numarayi giriyoruz
              
               final String mesaj = txtYazi.getText().toString();  ////txt ye girilen deger okunur
               
               Uri uri = Uri.parse("smsto:" + smsNumber); //girilen telefon numarasini kullanmak icin yapilan islem
               Intent intent = new Intent(Intent.ACTION_SENDTO, uri);//sms gonderme methodu
               intent.putExtra("sms_body", mesaj);//sms body alanina mesaj girilir
               startActivity(intent);// start activity baslatilir
            }
         });
        //sms atma son,
      
        
     }
        

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
		// TODO Auto-generated method stub
		 
		super.onActivityResult(requestCode, resultCode, data);
		 
		//Burdan sonraki bolumler konusulani yaziya aktarmak icin...
		 
		switch (requestCode) {
		 
		case RESULT_SPEECH: {
		 
		if (resultCode == RESULT_OK && null != data) {
			
			//konusma gerceklestirilirse o konusma mesaja txt ye donusturulur gerceklestirilmezse donusum gerceklestirmez
			ArrayList<String> text = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

		      txtYazi.setText(text.get(0));
		 
		}
		 
		break;
		 
					}
		 
				}
		 
		}
		 
	


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
