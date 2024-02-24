package com.example.beltekodev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.beltekodev.databinding.ActivityAddNoteBinding;

import java.util.ArrayList;

public class AddNoteActivity extends AppCompatActivity {
    ActivityAddNoteBinding binding;
    String note;
    int noteCounter=0;
    SharedPreferences sharedPreferences;
    ArrayList<String> arrayList;
    Intent getIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding başlatıldı
        binding=ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //shared preferences başladı
        sharedPreferences=this.getSharedPreferences("com.example.beltekodev",MODE_PRIVATE);

        //intet  getIntent() nesnesi olarak başladırdı
        getIntent =getIntent();

        //güncellenmiş notCounter aşağıda kullanılmak üzere çağırıldı
        noteCounter=sharedPreferences.getInt("counter",0);

        binding.kaydetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // editText içerisindeki ifadeyi bir değişkene çektik..
                note=binding.addNotePageEdt.getText().toString();

                //MainActivity sayfasından gelen ListArray yapısını aldık..
                ArrayList<String> arrayList= getIntent.getStringArrayListExtra("arrayList");

                //ListArray yapısına ve shared preferences text edit içerisindeki metni ekledik..
                arrayList.add(note);
                sharedPreferences.edit().putString("note"+noteCounter,note).apply();

                // listeyi güncellediğimizde bu liste sadece bu sınıf bazında güncellenir güncellenen listeyi
                // diğer sayfada da kullanmak istiyorsak güncellenen yeni listeyi diğer sayfaya aktarmamız gerekir.


                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("updatedList", arrayList);

                setResult(RESULT_OK, resultIntent);
                //setResult(RESULT_OK, resultIntent);:
                // Bu satır, AddNoteActivity'nin sonlandığında geri dönüş kodunu
                // ve içerdiği veriyi belirler. RESULT_OK, bir aktivitenin başarıyla tamamlandığını
                // ifade eden bir sabittir. resultIntent ise içinde taşıdığı veriyle
                // birlikte dönüş olarak ayarlanır.
                // MainActivity, onActivityResult metodunu kullanarak bu dönüşü ele alabilir.

                Toast.makeText(AddNoteActivity.this, String.valueOf(noteCounter), Toast.LENGTH_SHORT).show();

                //bir sonraki eklenen notun index numarasını burada shared preferences a kaydederiz
                noteCounter+=1;
                sharedPreferences.edit().putInt("counter",noteCounter).apply();
                finish();

            }
        });

    }


}