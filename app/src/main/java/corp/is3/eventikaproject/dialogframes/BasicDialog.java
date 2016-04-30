package corp.is3.eventikaproject.dialogframes;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;

public abstract class BasicDialog extends DialogFragment {

    private Runnable action;

    public void setActionPositiveButton(Runnable action) {
        this.action = action;
    }
}
