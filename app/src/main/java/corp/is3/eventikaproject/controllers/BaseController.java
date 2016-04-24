package corp.is3.eventikaproject.controllers;

import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;

public abstract class BaseController {

    protected Resources res;
    private Context context;
    private ViewGroup content;

    public BaseController(Context context, ViewGroup content) {
        this.content = content;
        this.context = context;
        res = content.getResources();
    }

    public ViewGroup getContent() {
        return content;
    }

    public Context getContext() {
        return context;
    }

    public abstract void refresh();
}
