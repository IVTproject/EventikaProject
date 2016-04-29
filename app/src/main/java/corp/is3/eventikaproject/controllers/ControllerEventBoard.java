package corp.is3.eventikaproject.controllers;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import corp.is3.eventikaproject.R;
import corp.is3.eventikaproject.adapters.AdapterEventInfo;
import corp.is3.eventikaproject.recyclerviewadapter.AdapterEventBoard;
import corp.is3.eventikaproject.reuests.CallbackFunction;
import corp.is3.eventikaproject.reuests.QueryDesigner;
import corp.is3.eventikaproject.reuests.QueryManager;
import corp.is3.eventikaproject.structures.EventInfo;

public class ControllerEventBoard extends BaseController {

    private final int RECYCLER_VIEW_ID = R.id.list_event_card;
    private final int MARGIN = 8;

    private RecyclerView recyclerView;
    private LinearLayoutManager llm;

    public ControllerEventBoard(Context context, ViewGroup content) {
        super(context, content);
        recyclerView = (RecyclerView) content.findViewById(RECYCLER_VIEW_ID);
        llm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llm);
        ArrayList<EventInfo> l = new ArrayList<>();
        recyclerView.setAdapter(new AdapterEventBoard(l));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, MARGIN, 0, MARGIN);
            }
        });

        final SwipeRefreshLayout refreshLayout =
                (SwipeRefreshLayout) content.findViewById(R.id.swipe_refresh_event_board);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                refreshLayout.canChildScrollUp();
                refresh();
                refreshLayout.setRefreshing(false);
            }
        });
        loadBegin();
    }

    public void addEvents(ArrayList<EventInfo> events) {
        int start = recyclerView.getAdapter().getItemCount();
        for (EventInfo event : events) {
            ((AdapterEventBoard) recyclerView.getAdapter()).addItem(event);
        }
        recyclerView.getAdapter().notifyItemRangeInserted(start, events.size());
    }

    @Override
    public void refresh() {
        int count = recyclerView.getAdapter().getItemCount();
        ((AdapterEventBoard) recyclerView.getAdapter()).clear();
        recyclerView.getAdapter().notifyItemRangeRemoved(0, count);
        loadBegin();
    }

    private void loadBegin() {
        QueryManager qm = new QueryManager();
        QueryDesigner qd = new QueryDesigner();
        qd.setType(QueryDesigner.QUERY_TYPE.LIST_EVENT);
        qd.setLimit(0, 5);
        qm.query(qd.getURL(), new CallbackFunction() {

            @Override
            public void callable(String response) {
                AdapterEventInfo adapter = new AdapterEventInfo(response, getContext());
                ArrayList eventsCard = adapter.getResult();
                if (eventsCard != null)
                    addEvents(eventsCard);
                else
                    message(getContext().getResources().getString(R.string.error_connection));
            }
        });

    }

    private void message(String text) {
        Toast toast = Toast.makeText(getContext(), text,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, (int) getContext().getResources().getDimension(R.dimen.padding_high));
        toast.show();
    }
}
