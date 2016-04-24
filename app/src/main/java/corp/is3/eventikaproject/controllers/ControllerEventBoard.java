package corp.is3.eventikaproject.controllers;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.recyclerviewadapter.AdapterEventBoard;
import corp.is3.eventikaproject.structures.EventInfo;

public class ControllerEventBoard extends BaseController {

    private final int RECYCLER_VIEW_ID = R.id.list_event_card;

    private RecyclerView recyclerView;

    public ControllerEventBoard(Context context, ViewGroup content) {
        super(context, content);
        recyclerView = (RecyclerView) content.findViewById(RECYCLER_VIEW_ID);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llm);
        ArrayList<EventInfo> l = new ArrayList<>();
        recyclerView.setAdapter(new AdapterEventBoard(l));
        recyclerView.setHasFixedSize(true);
    }

    AdapterEventBoard e;

    public void addEvents(ArrayList<EventInfo> events) {
        for(EventInfo event : events)
            ((AdapterEventBoard)recyclerView.getAdapter()).addItem(event);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void refresh() {

    }
}
