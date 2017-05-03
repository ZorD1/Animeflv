package knf.animeflv.Suggestions;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import knf.animeflv.ColorsRes;
import knf.animeflv.R;
import knf.animeflv.Random.AnimeObject;
import knf.animeflv.Utils.CacheManager;
import knf.animeflv.Utils.ThemeUtils;
import knf.animeflv.info.Helper.InfoHelper;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.ViewHolder> {

    private Activity activity;
    private List<AnimeObject> animes = new ArrayList<>();

    public SuggestionsAdapter(Activity activity, List<AnimeObject> list) {
        this.activity = activity;
        this.animes = list;
    }

    @Override
    public SuggestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(activity).
                inflate(R.layout.item_suggestion, parent, false);
        return new SuggestionsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SuggestionsAdapter.ViewHolder holder, final int position) {
        holder.card.setBackgroundColor(ThemeUtils.isAmoled(activity) ? ColorsRes.Prim(activity) : ColorsRes.Blanco(activity));
        holder.tv_tit.setTextColor(ThemeUtils.isAmoled(activity) ? ColorsRes.SecondaryTextDark(activity) : ColorsRes.SecondaryTextLight(activity));
        holder.tv_tipo.setTextColor(ThemeUtils.getAcentColor(activity));
        new CacheManager().mini(activity, animes.get(getPosition(holder, position)).aid, holder.iv_rel);
        holder.tv_tit.setText(animes.get(getPosition(holder, position)).title);
        holder.tv_tipo.setText(animes.get(getPosition(holder, position)).tid);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoHelper.open(
                        activity,
                        new InfoHelper.SharedItem(holder.iv_rel, "img"),
                        new InfoHelper.BundleItem("title", animes.get(getPosition(holder, position)).title),
                        new InfoHelper.BundleItem("aid", animes.get(getPosition(holder, position)).aid)
                );
            }
        });
    }

    private int getPosition(ViewHolder holder, int position) {
        return holder.getAdapterPosition() == -1 ? position : holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return animes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_rel;
        public TextView tv_tit;
        public TextView tv_tipo;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            this.iv_rel = (ImageView) itemView.findViewById(R.id.imgCardInfoRel);
            this.tv_tit = (TextView) itemView.findViewById(R.id.tv_info_rel_tit);
            this.tv_tipo = (TextView) itemView.findViewById(R.id.tv_info_rel_tipo);
            this.card = (CardView) itemView.findViewById(R.id.cardRel);
        }
    }

}