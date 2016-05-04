package corp.is3.eventikaproject.dialogframes;

import android.support.v4.app.DialogFragment;

/*Класс от которого наследуются все диалоговвые окна.
Необходимо переделать, перенести больше кода сюда, разгрузить остальные классы!!!*/
@Deprecated
public abstract class BasicDialog extends DialogFragment {

    private Runnable action;

    public void setActionPositiveButton(Runnable action) {
        this.action = action;
    }
}
