package com.ece.projectflower;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Program {

    public static void main(String[] args) throws IOException {

        FileInputStream fstream = new FileInputStream("iris.data");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        ArrayList<FlowerData> fd = new ArrayList<FlowerData>();

        while ((strLine = br.readLine()) != null)   {
            List<String> ozellikler= Arrays.asList(strLine.split(","));  // virgulle parselleyerek datamizi degiskenlere atiyoruz
            String canakyaprakuzunlugu = ozellikler.get(0);
            String canakyaprakgenisligi = ozellikler.get(1);
            String tacyaprakuzunlugu = ozellikler.get(2);
            String tacyaprakgenisligi = ozellikler.get(3);
            String tur = ozellikler.get(4);

            FlowerData data = new  FlowerData();
            float cyu = Float.parseFloat(canakyaprakuzunlugu);
            data.setCanakyaprakuzunlugu(cyu);
            float cyg = Float.parseFloat(canakyaprakgenisligi);
            data.setCanakyaprakgenisligi(cyg);
            float tyu = Float.parseFloat(tacyaprakuzunlugu);
            data.setTacyaprakuzunlugu(tyu);
            float tyg = Float.parseFloat(tacyaprakgenisligi);
            data.setTacyaprakgenisligi(tyg);
            data.setTur(tur);
            fd.add(data);

        }
        for (int i=0; i<3; i++) {
            accuarcycalculator(fd, 20, 0.005f);
            accuarcycalculator(fd, 20, 0.01f);
            accuarcycalculator(fd, 20, 0.025f);
            accuarcycalculator(fd, 50, 0.005f);
            accuarcycalculator(fd, 50, 0.01f);        // uc tur dogruluk fonksiyonumuzu calistiriyoruz
            accuarcycalculator(fd, 50, 0.025f);
            accuarcycalculator(fd, 100, 0.005f);
            accuarcycalculator(fd, 100, 0.01f);
            accuarcycalculator(fd, 100, 0.025f);
        }

    }
    public static void accuarcycalculator (List<FlowerData> fd,int epoch,float lambda){

        NeuralNetwork nn = new NeuralNetwork();

        for (int i=0; i<epoch; i++){
            nn.train(fd,lambda);
        }
        float acc = nn.test(fd);
        System.out.println("epoch:"+epoch+" lambda:"+lambda+" accuracy:"+acc);  // sonuclarimizi goruntuluyoruz

    }
}
