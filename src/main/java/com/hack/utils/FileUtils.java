package com.hack.utils;

import android.content.Context;

import com.hack.Features;
import com.hack.Slog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtils {
    private static final boolean DEBUG = Features.DEBUG;
    private static final int FILE_BYTE_BUFFER = 4096;
    private static final String TAG = FileUtils.class.getSimpleName();

    public static int copy(InputStream in, OutputStream out) throws IOException {
        int total = 0;
        byte[] buffer = new byte[FILE_BYTE_BUFFER];
        int c;
        while ((c = in.read(buffer)) != -1) {
            total += c;
            out.write(buffer, 0, c);
        }
        return total;
    }

    public static int copyAndClose(InputStream in, OutputStream out) throws IOException {
        try {
            int total = 0;
            byte[] buffer = new byte[FILE_BYTE_BUFFER];
            int c;
            while ((c = in.read(buffer)) != -1) {
                total += c;
                out.write(buffer, 0, c);
            }
            return total;
        } finally {
            closeQuietly(in);
            closeQuietly(out);
        }

    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }

    public static void extractAsset(Context context, String name, File target) throws IOException {
        target.getParentFile().mkdirs();
        copyAndClose(context.getAssets().open(name), new FileOutputStream(target));
    }

    public static void extractFile(File file, String dir, File output) throws IOException {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            String DIR = output.getCanonicalPath();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().startsWith(dir)) {
                    if (entry.isDirectory()) {
                        continue;
                    }
                    File target = new File(output, entry.getName());
                    String canonicalPath = target.getCanonicalPath();
                    if (!canonicalPath.startsWith(DIR)) {
                        throw new IOException("security path " + entry.getName());
                    }
                    target.getParentFile().mkdirs();
                    copyAndClose(zipFile.getInputStream(entry), new FileOutputStream(target));
                }
            }
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public static boolean deleteQuietly(File scratch) {
        try {
            if (!scratch.isFile()) {
                File[] files = scratch.listFiles();
                if (files != null) {
                    for (File file : files) {
                        deleteQuietly(file);
                    }
                }
            }
            return scratch.delete();
        } catch (Exception e) {

        }
        return false;

    }

    public static String readString(File file) {
        return readString(file, StandardCharsets.UTF_8);
    }

    public static String readString(File file, Charset charset) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            String result = readString(inputStream, charset);
            inputStream.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readString(InputStream inputStream, Charset charset) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            FileUtils.copy(inputStream, os);
            if (charset != null) {
                return new String(os.toByteArray(), charset);
            }
            return os.toString();
        } catch (Throwable e) {
            //ignore
        }
        return null;

    }

    public static void writeString(File file, String json) throws IOException {
        FileOutputStream outputStream = null;
        try {
            file.getParentFile().mkdirs();
            outputStream = new FileOutputStream(file);
            outputStream.write(json.getBytes());
        } finally {
            closeQuietly(outputStream);
        }
    }
}
