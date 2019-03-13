package test.android.com.hexad.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import test.android.com.hexad.R;
import test.android.com.hexad.interfaces.RatingChangeListener;
import test.android.com.hexad.model.FavoriteItem;

public class ListFavoriteItemsAdapter extends RecyclerView.Adapter<ListFavoriteItemsAdapter.FavoritesViewHolder> {

    private List<FavoriteItem> mFavoriteItems;

    private RatingChangeListener mRatingChangeListener;

    private final String TAG = ListFavoriteItemsAdapter.class.getSimpleName();

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder implements RatingBar.OnRatingBarChangeListener {

        ConstraintLayout listItemLayout;

        TextView name;

        TextView description;

        RatingBar ratingBar;

        WeakReference<ListFavoriteItemsAdapter> mWeakReference;

        public FavoritesViewHolder(View view, ListFavoriteItemsAdapter listFavoriteItemsAdapter) {
            super(view);
            listItemLayout = (ConstraintLayout) view.findViewById(R.id.listitem);
            name = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.details);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            ratingBar.setOnRatingBarChangeListener(this);
            mWeakReference = new WeakReference<ListFavoriteItemsAdapter>(listFavoriteItemsAdapter);
        }

        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            if (fromUser) {
                int position = getAdapterPosition();
                mWeakReference.get().mRatingChangeListener.onRatingChanged(position, (int) rating);
            }
        }
    }

    public ListFavoriteItemsAdapter(List<FavoriteItem> aTrendingRepositories, RatingChangeListener ratingChangeListener) {
        this.mFavoriteItems = aTrendingRepositories;
        mRatingChangeListener = ratingChangeListener;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new FavoritesViewHolder(view, this);
    }


    @Override
    public void onBindViewHolder(final FavoritesViewHolder holder, final int position) {
        holder.name.setText(mFavoriteItems.get(position).getName());
        holder.description.setText(mFavoriteItems.get(position).getDescription());
        holder.ratingBar.setRating(mFavoriteItems.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return mFavoriteItems.size();
    }

    public void setDataItems(List<FavoriteItem> aTrendingRepositories) {
        this.mFavoriteItems = aTrendingRepositories;
    }
}