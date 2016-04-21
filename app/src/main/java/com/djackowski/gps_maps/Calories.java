package com.djackowski.gps_maps;


public class Calories {

    private float weight;
    private float distance;
    private float speed;
    private boolean isStop;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float Calculate(float weight, float distance, boolean isStop) {
        if (!isStop)
            return (weight * 2.2f * 0.53f * distance / 1000 * 0.62f);//dystans w km, dzielimy aby uzyskac w metrach
        else
            return 0.0f;


    }

}
