package com.example.beltekodev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


import com.example.beltekodev.databinding.ActivityReadBinding;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {
    ActivityReadBinding binding;
    Intent intent;
    SharedPreferences sharedPreferences;
    int kaydet=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =ActivityReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent=getIntent();
        String data=intent.getStringExtra("text");

        sharedPreferences=this.getSharedPreferences("com.example.beltekodev",MODE_PRIVATE);

        binding.readPage.setText(data);

        binding.readPage.setEnabled(false);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.readPage.setEnabled(true);
                binding.editBtn.setText("Kaydet");
                kaydet++;
                if(kaydet==2){
                    String newNote=binding.readPage.getText().toString();
                    sharedPreferences.edit().putString("note"+intent.getIntExtra("position",0),newNote).apply();

                    ArrayList<String> arrayList=intent.getStringArrayListExtra("arrayList");

                    //ListArray yapısının belirtilen indexteki yapaısını güncelledik.
                    //güncellenmiş arrayı yeni bir intent sınıfı ile main activity sayfasına gönderdik.
                    int index=intent.getIntExtra("position",0);
                    arrayList.set(index,newNote);

                    //güncellenmiş list array yapısındaki eleman sayısını shared preferences'taki counter key ine atar.
                    Intent resultIntent = new Intent();
                    resultIntent.putStringArrayListExtra("updatedList", arrayList);
                    setResult(RESULT_OK, resultIntent);

                    finish();
                }
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //list array ögesine basıldığında mu sayfaya aktarılan pozizyon bilgisini alır ve index değişkenine atar
                int index=intent.getIntExtra("position",0);
                //bu sayfaya aktarılan list array yapısnı çeker ve list array değişkenine atar.
                ArrayList<String> arrayList=intent.getStringArrayListExtra("arrayList");

                //bahsi geçen indexteki shared preferences ve listarray içindeki yapıları kaldırır
                //sharedPreferences.edit().remove("note"+index).apply();
                arrayList.remove(index);

//                for (int i=0;i<arrayList.size()-1;i++){
//                    sharedPreferences.edit().putString("note"+i,arrayList.get(i)).apply();
//                }

                //güncellenmiş list array yapısındaki eleman sayısını shared preferences'taki counter key ine atar.
                int newCounter=arrayList.size();
                sharedPreferences.edit().putInt("counter",newCounter).apply();

                //güncellenmiş yeni list array yapısını main sayfasına döndürür.
                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("updatedList", arrayList);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });



    }
}