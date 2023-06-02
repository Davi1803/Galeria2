package salles.cardoso.davi.galeria2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Criando uma toolbar
        Toolbar toolbar = findViewById(R.id.tbMain);
        //Indicando que a toolbar é uma ActionBar
        setSupportActionBar(toolbar);
    }
}