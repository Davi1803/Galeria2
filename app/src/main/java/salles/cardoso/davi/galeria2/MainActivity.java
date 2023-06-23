package salles.cardoso.davi.galeria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static int RESULT_TAKE_PICTURE = 1;
    static int RESULT_REQUEST_PERMISSION = 2

    String currentPhotoPath;

    //criando um arquivo vazio dentro da pasta Pictures
    private void dispatchTakePictureIntent(){
        File f = null;
        try{
            f = createImageFile();
            //caso o arquivo não possa ser encontrado, exibirá uma mensagem para o usuário
        }catch (IOException e) {
            Toast.makeText(MainActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        //arquivo sendo salvo em currentPhotoPath
        currentPhotoPath = f.getAbsolutePath();

        if(f != null) {
            //gerando um endereço Uri para o arquivo de foto
            Uri fUri = FileProvider.getUriForFile(MainActivity.this, "trindade.daniel.galeria.fileprovider", f);
            //Intent para disparar a app de câmera
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Uri passado para a app de câmera
            i.putExtra(MediaStore.EXTRA_OUTPUT, fUri);
            //app de câmera iniciando
            startActivityForResult(i, RESULT_TAKE_PICTURE);
        }
    }

    //criando um arquivo que vai guardar a imagem
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);
        return f;
    }
    @Override
    //chamando onActivity depois que a app de câmera retorna para a aplicação
            protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_TAKE_PICTURE) {
            //caso a foto tenha sido tirada...
            if(resultCode == Activity.RESULT_OK) {
                //o local dela é adicionado na lista de fotos
                photos.add(currentPhotoPath);

                //mainAdapter é avisado, necessitando de atualizar o RecycleView
                mainAdapter.notifyItemInserted(photos.size()-1);
            }
            //caso a foto não tenha sido tirada...
            else{
                //o arquivo criado para conter a foto é excluído
                File f = new File (currentPhotoPath);
                f.delete();
            }
        }
    }
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
    private void checkForPermissions(List<String> permissions) {
        List<String> permissionsNotGranted = new ArrayList<>();

        for(String permission : permissions) {
            if( !hasPermission(permission)){
                permissionsNotGranted.add(permission);
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(permissionsNotGranted.size() > 0){
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]),RESULT_REQUEST_PERMISSION);
            }
        }
    }
    private boolean hasPermission(String permission) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    } @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final List<String> permissionsRejected = new ArrayList<>();
        if(requestCode == RESULT_REQUEST_PERMISSION) {
            for(String permission : permissions){
                if(!hasPermission(permission)){
                    permissionsRejected.add(permission);
                }
            }
        }
        if(permissionsRejected.size() > 0) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(permissionsRejected.get(0))){
                    new AlertDialog.Builder(MainActivity.this).setMessage("Para usar esse app é preciso conceder essas permissões").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                        }
                    }).create().show();
                }
            }
        }
    }
}