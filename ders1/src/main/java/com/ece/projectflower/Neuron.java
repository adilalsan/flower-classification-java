package com.ece.projectflower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {
    public List<Float> weights = new ArrayList();   // agirlik listesi olusturup random agirliklar atiyoruz

    Neuron() {
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            float randweight = rand.nextFloat();
            weights.add(randweight);
        }
    }

    public float hesapla(List<Float> inputs) {
        float toplam = 0;            // inputs listemizle random agirliklari kullanarak gerekli hesaplamayi yapiyoruz
        for (int i = 0; i < inputs.size(); i++) {
            toplam += inputs.get(i) * weights.get(i);
        }
        return toplam;
    }











}
