package corp.is3.eventikaproject.dialogframes;

/*Реализация данного интерфейса передается в диалоговые окна выбора для обработки результата*/
public interface SelectItemsListener {

    public void ok(boolean[] condition);

    public void cancel(boolean[] condition);
}
