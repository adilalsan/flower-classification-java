package com.ece.projectflower;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NeuralNetwork {

    private Neuron N1 = new Neuron();
    private Neuron N2 = new Neuron();   // noron nesnelerini olusturduk
    private Neuron N3 = new Neuron();

    NeuralNetwork (){
    }
    private void Egitim (FlowerData flower, float lambda) {
        Neuron expectedN = null;
                                                // ilgili norona 1 diger noronlara 0 atayacak ideal paterni olusturduk
        int maxexpN = 0 ;
        String tur = flower.getTur();

        if(tur.equals("Iris-setosa")){
            expectedN = N1;
            maxexpN = 1;
        }
        if(tur.equals("Iris-versicolor")){
            expectedN = N2;
            maxexpN = 2;
        }
        if (tur.equals("Iris-virginica")){
            expectedN = N3;
            maxexpN = 3;
        }
        List<Float> inputs = new ArrayList<Float>();            // sinifimizin icerisinde inputs listesi olusturuyoruz
        float cyg = flower.getCanakyaprakgenisligi()/10;
        float tyg = flower.getTacyaprakgenisligi()/10;      // degerlerimizi 0,1 araliginda olmasi icin olceklendiriyoruz
        float cyu = flower.getCanakyaprakuzunlugu()/10;
        float tyu = flower.getTacyaprakuzunlugu()/10;
        inputs.add(cyg);
        inputs.add(tyg);            // degerlerimizi listeye ekliyoruz
        inputs.add(cyu);
        inputs.add(tyu);

        float realN1 = N1.hesapla(inputs);
        float realN2 = N2.hesapla(inputs);      // noronlarin esas degerlerini hesapliyoruz
        float realN3 = N3.hesapla(inputs);

        List<Float> listN = new ArrayList<>();  // esas degerleri tutmak icin liste olusturup elemanlari ekliyoruz
        listN.add(realN1);
        listN.add(realN2);
        listN.add(realN3);

        float maxrealN = Collections.<Float>max(listN);     // listedeki maximum degeri buluyoruz
        int maxrealNindex = listN.indexOf(maxrealN)+1;      // karsilastirmak icin sirasini belirliyoruz

        if (maxexpN != maxrealNindex){
            AdjustUp(expectedN,lambda,inputs);      // farkli olma durumundaki adjustmentlarimizi yapiyoruz
            if (maxrealN == realN1) {
                AdjustDown(N1,lambda,inputs);
            }
            else if  (maxrealN == realN2) {
                AdjustDown(N2,lambda,inputs);
            }
            else if (maxrealN == realN3) {
                AdjustDown(N3,lambda,inputs);
            }
        }
    }

    private void  AdjustUp (Neuron neuron,float lambda,List<Float> inputs) {
        for  (int i = 0; i < neuron.weights.size(); i++)  {
           float w = neuron.weights.get(i);
           float x = inputs.get(i);         // beklenenden az olmasi durumundaki hesaplama
           w = w + (lambda * x);
           neuron.weights.set(i,w);
        }
    }

    private void AdjustDown (Neuron neuron,float lambda,List<Float> inputs) {
        for  (int i = 0; i < neuron.weights.size(); i++) {
            float w = neuron.weights.get(i);
            float x = inputs.get(i);
            w = w - (lambda * x);           //  beklenenden fazla olmasi durumundaki hesaplama
            neuron.weights.set(i, w);
        }
    }


    public void train (List<FlowerData> list,float lambda) {
        for (FlowerData data : list) {
            Egitim(data,lambda);            // datalari parametre olarak verip egitim metodumuzu olusturuyoruz
        }
    }

    public float test (List<FlowerData> list) {
        float dogruluksayisi = 0;                   // egittigimiz agin dogrulugunu test ediyoruz
        for (int i=0; i<list.size(); i++){
            List<Float> inputs = new ArrayList<Float>();
            FlowerData flower = list.get(i);
            float cyg = flower.getCanakyaprakgenisligi()/10;
            float tyg = flower.getTacyaprakgenisligi()/10;
            float cyu = flower.getCanakyaprakuzunlugu()/10;
            float tyu = flower.getTacyaprakuzunlugu()/10;
            inputs.add(cyg);
            inputs.add(tyg);
            inputs.add(cyu);
            inputs.add(tyu);

            float resultN1 = N1.hesapla(inputs);
            float resultN2 = N2.hesapla(inputs);
            float resultN3 = N3.hesapla(inputs);

            int maxexpN = 0 ;
            String tur = flower.getTur();

            if(tur.equals("Iris-setosa")){
                maxexpN = 1;
            }
            if(tur.equals("Iris-versicolor")){
                maxexpN = 2;
            }
            if (tur.equals("Iris-virginica")){
                maxexpN = 3;
            }

            List<Float> listN = new ArrayList<>();
            listN.add(resultN1);
            listN.add(resultN2);
            listN.add(resultN3);
            float maxrealN = Collections.<Float>max(listN);
            int maxrealNindex = listN.indexOf(maxrealN)+1;

            if (maxexpN == maxrealNindex) {
                dogruluksayisi++;
            }

        }
        float accuracy = dogruluksayisi / list.size();

        return accuracy;
    }

}
