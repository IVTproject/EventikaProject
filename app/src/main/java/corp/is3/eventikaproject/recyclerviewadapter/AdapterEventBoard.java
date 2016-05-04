package corp.is3.eventikaproject.recyclerviewadapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.datamanager.UserData;
import corp.is3.eventikaproject.listeners.ImageLoadingListener;
import corp.is3.eventikaproject.listeners.OnTouchClickListenerBase;
import corp.is3.eventikaproject.services.Services;
import corp.is3.eventikaproject.structures.EventInfo;

/* Адаптер для виджета RecyclerView, для экрана доски мероприятий и избранного. Поведение прописано тут*/
public class AdapterEventBoard extends RecyclerView.Adapter<AdapterEventBoard.EventViewHolder>{

    private ArrayList<EventInfo> listEvent;

    public AdapterEventBoard() {
        listEvent = new ArrayList<>();
    }

    public void addItem(EventInfo event) {
        listEvent.add(event);
    }

    public void clear() {
        if(listEvent != null)
            listEvent.clear();
    }

    protected static class EventViewHolder extends RecyclerView.ViewHolder {

        private AdapterEventBoard inst;
        int position;
        EventInfo info;
        CardView cv;
        PorterShapeImageView imageEventCard;
        TextView nameEventCard;
        TextView paramOne;
        TextView paramTwo;
        TextView paramThree;
        ImageView favoriteIcon;

        EventViewHolder(View itemView, AdapterEventBoard inst) {
            super(itemView);
            this.inst = inst;
            cv = (CardView) itemView.findViewById(R.id.card_event);
            imageEventCard = (PorterShapeImageView) itemView.findViewById(R.id.image_event_card);
            nameEventCard = (TextView) itemView.findViewById(R.id.name_event_card);
            paramOne = (TextView) itemView.findViewById(R.id.event_param_one);
            paramTwo = (TextView) itemView.findViewById(R.id.event_param_two);
            paramThree = (TextView) itemView.findViewById(R.id.event_param_three);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.favorite_button_event_card);
            clickCard(cv);
            clickFavorite(favoriteIcon);
        }

        void clickCard(final CardView cv) {
            cv.setOnTouchListener(new OnTouchClickListenerBase() {
                @Override
                public void select(View v) {
                    cv.setCardBackgroundColor(Color.LTGRAY);
                }

                @Override
                public void deSelect(View v) {
                    cv.setCardBackgroundColor(Color.WHITE);
                }

                @Override
                public void click(View v, boolean longClick) {
                    Services.controllerNavigationView.visitedEvent(info);
                }
            });
        }

        void clickFavorite(View v) {
            v.setOnTouchListener(new OnTouchClickListenerBase() {
                @Override
                public void select(View v) {
                    v.setScaleX(1.3f);
                    v.setScaleY(1.3f);
                }

                @Override
                public void deSelect(View v) {
                    v.setScaleX(1.0f);
                    v.setScaleY(1.0f);
                }

                @Override
                public void click(View v, boolean longClick) {
                    UserData ud = Services.dataManager.getUserData();
                    EventInfo ei = inst.listEvent.get(position);
                    if (ei.isFavorite()) {
                        favoriteIcon.setImageResource(R.drawable.ic_star_not_fill24dp);
                        ud.removeFavoriteEvent(ei.getId());
                    } else {
                        favoriteIcon.setImageResource(R.drawable.ic_star_24dp);
                        ud.addFavoriteEvent(ei.getId(), ei);
                    }
                    inst.listEvent.get(position).setIsFavorite(!ei.isFavorite());
                }
            });
        }
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout card = (LinearLayout) LinearLayout.inflate(parent.getContext(), R.layout.event_block, null);
        return new EventViewHolder(card, this);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        EventInfo event = listEvent.get(position);
        holder.nameEventCard.setText(event.getName());
        holder.position = position;
        holder.paramOne.setText(String.format("%s %s", event.getCity(), event.getAddress()));
        holder.paramTwo.setText(event.getBeginDate());
        holder.paramThree.setText(event.getEndDate());
        holder.imageEventCard.setImageResource(R.drawable.default_img);
        if(event.getImage() == null)
            ImageLoader.getInstance().displayImage(event.getUrlImage(), holder.imageEventCard, new ImageLoadingListener(event));
        else
            holder.imageEventCard.setImageDrawable(event.getImage());
        holder.info = event;
    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }
}