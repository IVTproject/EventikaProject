package corp.is3.eventikaproject.services;

import corp.is3.eventikaproject.contentmanager.ContentManager;
import corp.is3.eventikaproject.controllers.ControllerNavigationView;
import corp.is3.eventikaproject.datamanager.DataManager;
import corp.is3.eventikaproject.datamanager.DrawableData;

/* Классы которые нужны в общем доступе. Обязательная инициализация всех полей в onCreate MainActivity*/
public class Services {

    public static ControllerNavigationView controllerNavigationView;
    public static ContentManager contentManager;
    public static DataManager dataManager;

    private Services(){}
}
