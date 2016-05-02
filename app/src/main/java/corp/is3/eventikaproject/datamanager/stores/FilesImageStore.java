package corp.is3.eventikaproject.datamanager.stores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import corp.is3.eventikaproject.R;

public class FilesImageStore implements Store<Drawable> {

    private final String FILE_EXTENSION;

    private String dir;

    public FilesImageStore(Context context) {
        FILE_EXTENSION = context.getResources().getString(R.string.app_name);
        dir = context.getCacheDir().getAbsolutePath();
    }

    @Override
    public boolean setData(String name, Drawable data) {
        try {
            File file = new File(dir, name + "." + FILE_EXTENSION);
            FileOutputStream fOut = new FileOutputStream(file);

            Bitmap bitmap = ((BitmapDrawable) data).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Drawable getData(String name) {
        try {
            return Drawable.createFromPath(dir + "/" + name + "." + FILE_EXTENSION);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean remove(String key) {
        return false;
    }
}
