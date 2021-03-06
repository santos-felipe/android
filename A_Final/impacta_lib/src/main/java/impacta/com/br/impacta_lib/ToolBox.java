package impacta.com.br.impacta_lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by nalmir on 26/11/2016.
 */
public class ToolBox {

    public static String comunicacao(String urlEn, String params) {
        StringBuilder sb = new StringBuilder();
        //
        URL url;
        HttpURLConnection conn = null;
        //
        try {

            url = new URL(urlEn);
            conn = (HttpURLConnection) url.openConnection();
            //
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //
            StringBuilder paramsFormatados = new StringBuilder();
            paramsFormatados.append(URLEncoder.encode("json", "UTF-8"));
            paramsFormatados.append("=");
            paramsFormatados.append(URLEncoder.encode(params, "UTF-8"));
            //
            // Envio dos Parametros
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(paramsFormatados.toString());
            writer.flush();
            writer.close();
            os.close();
            //
            sb.append(readStream(conn.getInputStream()));
        } catch (Exception e) {
            sb.append(e.toString());
        }

        return sb.toString();
    }

    // Ler a Resposta do Servidor (OBS Nem sempre vem dele)
    private static String readStream(InputStream inputStream) {
        Reader reader = null;
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        //
        try {

            reader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8")
            );
            //
            int n;
            //
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //
        return writer.toString();
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }




}
