package corp.is3.eventikaproject.controllers;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

/* Родительский класс для всех созданных контроллеров*/
public abstract class BasicController {

    protected Resources res;
    private AppCompatActivity compatActivity;
    private ViewGroup content;

    public BasicController(AppCompatActivity compatActivity, ViewGroup content) {
        this.content = content;
        this.compatActivity = compatActivity;
        res = content.getResources();
    }

    public ViewGroup getContent() {
        return content;
    }

    public AppCompatActivity getAppCompatActivity() {
        return compatActivity;
    }

    public abstract void refresh();
}
