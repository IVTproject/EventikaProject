package corp.is3.eventikaproject.factory;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.contentmanager.ContentManager;
import corp.is3.eventikaproject.listeners.OnTouchClickListener;
import corp.is3.eventikaproject.structures.EventInfo;

/**
 * Created by Дмитрий on 22.04.2016.
 */
public class BlockFactory {

    private Context context;
    private ContentManager contentManager;

    public BlockFactory(Context context, ContentManager contentManager) {
        this.contentManager = contentManager;
        this.context = context;
    }

    public LinearLayout createEventCard(EventInfo eventInfo) {
        LinearLayout block = (LinearLayout) LinearLayout.inflate(context, R.layout.event_block, null);
        final CardView card = (CardView) block.findViewById(R.id.card_event);
        ((PorterShapeImageView) block.findViewById(R.id.image_event_card)).setImageDrawable(eventInfo.image);
        ((TextView) block.findViewById(R.id.name_event_card)).setText(eventInfo.name);
        ((TextView) block.findViewById(R.id.event_param_one)).setText(eventInfo.address);
        ((TextView) block.findViewById(R.id.event_param_two)).setText(eventInfo.beginDate);
        ((TextView) block.findViewById(R.id.event_param_three)).setText(eventInfo.endDate);
        ((ImageView) block.findViewById(R.id.favorite_button_event_card))
                .setOnTouchListener(new OnTouchClickListener(clickStarCardEvent(eventInfo.id)) {
                    @Override
                    public void select() {
                        super.select();
                        card.setCardBackgroundColor(Color.YELLOW);
                    }
                });
        block.setOnTouchListener(new OnTouchClickListener(clickCardEvent(eventInfo)) {
            @Override
            public void select() {
                super.select();
                card.setCardBackgroundColor(Color.LTGRAY);
            }

            @Override
            public void deSelect() {
                super.deSelect();
                card.setCardBackgroundColor(Color.WHITE);
            }
        });
        return block;
    }

    private Runnable clickStarCardEvent(final int id) {
        return new Runnable() {
            @Override
            public void run() {

            }
        };
    }

    private Runnable clickCardEvent(final EventInfo eventInfo) {
        return new Runnable() {
            @Override
            public void run() {
                contentManager.openEvent(eventInfo);
            }
        };
    }
}
