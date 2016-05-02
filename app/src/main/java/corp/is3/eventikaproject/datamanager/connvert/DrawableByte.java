package corp.is3.eventikaproject.datamanager.connvert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

public class DrawableByte {

    public static byte[] drawableToBytes(Drawable drawable) {
        try {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
            return stream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    @Deprecated
    public static Drawable bytesToDrawable(byte[] bytes) {
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return new BitmapDrawable(bitmap);
        } catch (Exception e) {
            return null;
        }
    }
}
