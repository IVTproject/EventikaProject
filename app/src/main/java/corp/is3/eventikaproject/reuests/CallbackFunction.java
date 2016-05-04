package corp.is3.eventikaproject.reuests;

/*Реализаци передается в QueryManager для возвращения ответа сервера.*/
public interface CallbackFunction {

    public void callable(String response);
}
