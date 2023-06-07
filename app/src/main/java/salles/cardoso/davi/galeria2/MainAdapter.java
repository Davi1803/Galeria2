package salles.cardoso.davi.galeria2;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity;
    List<String> photos;

    public MainAdapter(MainActivity mainActivity, List<String> photos) {
        this.mainActivity = mainActivity;
        this.photos = photos;
    }
    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder holder, final int position) {
        ImageView imPhoto = holder.itemView.findViewById(R.id.imItem);
        //Obtendo as dimensões que a imagem vai ter na lista
        int w = (int)
        mainActivity.getResources().getDimension(R.dimen.itemWidth);
        int h = (int)
        mainActivity.getResources().getDimension(R.dimen.itemHeight);
        //Botando a foto no tipo Bitmap e com as dimensões já definidas
        Bitmap bitmap = Utils.getBitmap(photos.get(position) , w, h);
        //Setando o Bitmap no ImageView
        imPhoto.setImageBitmap(bitmap);
        //Orientando a aplicação após o clique do usuário
        imPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.startPhotoActivity(photos.get(position));
            }
        });
    }
}
