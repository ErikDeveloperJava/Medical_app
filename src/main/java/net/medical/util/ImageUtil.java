package net.medical.util;

import lombok.Cleanup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ImageUtil {

    private static final Logger LOGGER = LogManager.getLogger();

    private  Environment environment;

    private final String[] IMAGE_FORMATS;

    private final String ROOT_PATH;

    @Autowired
    public ImageUtil(Environment environment) {
        IMAGE_FORMATS = new String[]{"image/jpeg","image/png"};
        ROOT_PATH = environment.getProperty("root.path");
        File file = new File(ROOT_PATH);
        if(!file.exists()){
            if(!file.mkdirs()){
                LOGGER.warn("{} can not created",ROOT_PATH);
            }
        }
    }

    public boolean isValidData(String format){
        for (String imgFormat : IMAGE_FORMATS) {
            if(imgFormat.equals(format)){
                return true;
            }
        }
        return false;
    }

    public void save(String parent,String img,byte[] bytes){
        File file = new File(ROOT_PATH,parent);
        if(!file.exists()){
            if(!file.mkdir()){
                LOGGER.warn("{} can not created",file);
                throw new RuntimeException();
            }
        }
        try {
            @Cleanup FileOutputStream out = new FileOutputStream(new File(file,img));
            out.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public void delete(String fileName){
        File file = new File(ROOT_PATH,fileName);
        if(file.exists()){
            delete(file);
        }
    }

    public void delete(File file){
        if(file.isDirectory()){
            for (File f : file.listFiles()) {
                delete(f);
            }
        }else {
            file.delete();
        }
    }

    public byte[] getBytes(String fileName){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[80000];
        int length;
        try {
            @Cleanup FileInputStream input = new FileInputStream(new File(ROOT_PATH,fileName));
            while ((length = input.read(buff)) != -1){
                byteArrayOutputStream.write(buff,0,length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
