package salles.cardoso.davi.galeria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PhotoActivity extends AppCompatActivity {

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