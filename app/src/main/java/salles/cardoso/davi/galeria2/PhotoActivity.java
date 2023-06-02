package salles.cardoso.davi.galeria2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

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
}