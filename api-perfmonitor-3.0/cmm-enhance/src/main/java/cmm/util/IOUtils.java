package cmm.util;

import java.io.*;
import java.util.zip.ZipFile;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/8/21 16:08
 */
public class IOUtils {
    private IOUtils() {
    }

    public static String toString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int length;
        while((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return result.toString("UTF-8");
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];

        int len;
        while((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }

    }

    public static byte[] getBytes(InputStream input) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        copy(input, result);
        result.close();
        return result.toByteArray();
    }

    public static IOException close(InputStream input) {
        return close((Closeable)input);
    }

    public static IOException close(OutputStream output) {
        return close((Closeable)output);
    }

    public static IOException close(Reader input) {
        return close((Closeable)input);
    }

    public static IOException close(Writer output) {
        return close((Closeable)output);
    }

    public static IOException close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }

            return null;
        } catch (IOException var2) {
            return var2;
        }
    }

    public static IOException close(ZipFile zip) {
        try {
            if (zip != null) {
                zip.close();
            }

            return null;
        } catch (IOException var2) {
            return var2;
        }
    }
}
