package com.example.beltekodev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.beltekodev.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayAdapter arrayAdapter;
    ArrayList<String>arrayList;
    Intent intent;
    Intent intent1;
    Intent getIntent;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        arrayList=new ArrayList<>();

        //ArrayList ve Array Adaptörü birbirine bağladık
        arrayAdapter=new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,android.R.id.text1, arrayList);
        binding.notListview.setAdapter(arrayAdapter);

        //shared preferences başlatıldı.
        sharedPreferences=getSharedPreferences("com.example.beltekodev",MODE_PRIVATE);

        getIntent=getIntent();

        //liste içine kaydedilen not sayısını counter key'i ile kaydettik ve bu değeri çağırdık
        int count=sharedPreferences.getInt("counter",0);

        for(int i=0;i<count;i++){
            //shared preferences içindeki kaydedilmiş notları çağırdık
            String note=sharedPreferences.getString("note"+i,"");
            //çağrılan her notu listarraye ekledik ve güncellenci bildirimi yaptık
            arrayList.add(note);
            arrayAdapter.notifyDataSetChanged();
        }

        binding.ekleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //not ekle butonuna basıldığına not ekleme sayfasını açan satırlar
                intent=new Intent(MainActivity.this,AddNoteActivity.class);
                //arrayList nesnesi gönderildi
                intent.putStringArrayListExtra("arrayList",arrayList);
                startActivityForResult(intent,1);

            }
        });


        //ListView içerisindeki her bir öğe için aktivite sayfası oluşturan blok
        binding.notListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String data=arrayList.get(position);
                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

                // sonraki sayfaki edit text içerisinde pozisyon bililerini kullanmak için bu bilgileri aktaran satırlar.
                intent1=new Intent(MainActivity.this, ReadActivity.class);
                intent1.putExtra("position",position);
                intent1.putExtra("text",data);
                intent1.putStringArrayListExtra("arrayList",arrayList);
                startActivityForResult(intent1,2);
            }
        });

    }



    // Bu metod, bir aktiviteden başka bir aktiviteye geçiş sonrasında elde edilen sonuçları işlemek için kullanılır.
    // requestCode, başlatılan aktivitenin isteğinin bir benzersiz tanımlayıcısıdır.
    // resultCode, başlatılan aktivitenin sonuç durumunu belirtir (örneğin, başarıyla tamamlandı ya
    // da kullanıcı tarafından iptal edildi). data, başlatılan aktivitenin dönüş verisini içerir.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Bu if koşulu, başlatılan aktivitenin isteğinin 1
        // (bu durumda AddNoteActivity tarafından başlatıldığını belirtir)
        // ve sonuç durumunun RESULT_OK (aktivite başarıyla tamamlandı) olup olmadığını kontrol eder.
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // AddNoteActivity'den dönen veriyi alma
            ArrayList<String> updatedList = data.getStringArrayListExtra("updatedList");

            //gelen liste null değilse çalışacak kodbloğu...
            if (updatedList != null) {
                // MainActivity içindeki listeyi güncelleme
                arrayList.clear();
                arrayList.addAll(updatedList);
                arrayAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            // AddNoteActivity'den dönen veriyi alma
            ArrayList<String> updatedList = data.getStringArrayListExtra("updatedList");

            //gelen liste null değilse çalışacak kodbloğu...
            if (updatedList != null) {
                // MainActivity içindeki listeyi güncelleme
                arrayList.clear();
                arrayList.addAll(updatedList);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }


}

