package com.lb.io.inputstream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by lb on 2014/11/13.
 */
public class ByteArray {

    public static void main(String[] ages){
        byte[] b = new byte[]{-1,-128,1,2,4,7,90,112,-1,0};
        ByteArrayInputStream in  = new ByteArrayInputStream(b, 1,2);
        int i = in.read();
        while(i != -1){
            System.out.println(i);
            i = in.read();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
