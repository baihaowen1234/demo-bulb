package com.serotonin.modbus4j.messaging;

import com.serotonin.util.queue.ByteQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteeUtil {




    public static Short[] bytte(ByteQueue byteQueue){
        String mmm = byteQueue.toString().substring(9, byteQueue.toString().length() - 1);

//        System.out.println(mmm);

        List<String> result = Arrays.asList(mmm.split(","));

        List<String> mk = new ArrayList<>();

        List<Short> shot = new ArrayList<>();

        for (String pp : result) {

            mk.add(pp);

        }

        int l = 0;


        while (l < mk.size()) {

            String qq = "";

            for (int nn = 0; nn < 2; nn++) {

                qq += (mk.get(l));

                l++;

            }

            int i = Integer.parseInt(qq, 16);

            Short io = (short) i;

            shot.add(io);



        }

        Short[] ss = new Short[shot.size()];

        int ii = 0;

        for (Short sr:shot) {

            ss[ii] = sr;

            ii++;
        }


        return ss;
    }
}
