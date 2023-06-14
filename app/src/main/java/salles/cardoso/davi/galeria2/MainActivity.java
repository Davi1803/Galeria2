package salles.cardoso.davi.galeria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> photos = new ArrayList<>();

    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Criando uma toolbar
        Toolbar toolbar = findViewById(R.id.tbMain);
        //Indicando que a toolbar é uma ActionBar
        setSupportActionBar(toolbar);

        //Acessando o diretório "pictures"
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //Lendo lista de fotos já salvas
        File[] files = dir.listFiles();
        //Adicionando na lista de fotos
        for (int i = 0; i < files.length; i++) {
            photos.add(files[i].getAbsolutePath());
        }
        //Criando mainAdapter e setando no RecyclerView
        mainAdapter = new MainAdapter(MainActivity.this, photos);
        RecyclerView rvGallery = findViewById(R.id.rvGallery);
        rvGallery.setAdapter(mainAdapter);

        //Calculando quantas colunas de fotos cabem no celular
        float w = getResources().getDimension(R.dimen.itemWidth);
        int numberOfColumns = Utils.calculateNoOfColumns(MainActivity.this, w);
        //Configurando o RecyclerView para exibir as fotos em GRID
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns);
        rvGallery.setLayoutManager(gridLayoutManager);
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
            case R.id.opCamera:
                dispatchTakePictureIntent ();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void startPhotoActivity(String photoPath) {
        Intent i = new Intent(MainActivity.this, PhotoActivity.class);
        //Passando o caminho para a foto
        i.putExtra("photo_path", photoPath);
        startActivity(i);
    }
}