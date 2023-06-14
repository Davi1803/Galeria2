package salles.cardoso.davi.galeria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

public class PhotoActivity extends AppCompatActivity {

    String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //Criando uma toolbar
        Toolbar toolbar = findViewById(R.id.tbPhoto);
        //Indicando que a toolbar é uma ActionBar
        setSupportActionBar(toolbar);

        //Obtendo a ActionBar padrão
        ActionBar actionBar = getSupportActionBar();
        //Habilitando o botão voltar na ActionBar
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Obtendo o caminho da foto que foi enviada via Intent de criação
        Intent i = getIntent();
        photoPath = i.getStringExtra("photo_path");

        //Carregando a foto em um Bitmap
        Bitmap bitmap = Utils.getBitmap(photoPath);
        //Setando o Bitmap no ImageView
        ImageView imPhoto = findViewById(R.id.imPhoto);
        imPhoto.setImageBitmap(bitmap);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //Criando um inflador de menu
        MenuInflater inflater = getMenuInflater();
        //adicionando no menu da activity
        inflater.inflate(R.menu.main_activity_tb, menu);
        return true;
    }
    public boolean onOptionsItemSelected (@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opShare:
                sharePhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}