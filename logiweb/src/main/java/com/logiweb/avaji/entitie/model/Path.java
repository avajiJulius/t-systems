package com.logiweb.avaji.entitie.model;

import lombok.Data;

import java.util.List;

@Data
public class Path {

    private List<Long> path;
    private double distance;

    public Path(List<Long> path, double distance) {
        this.path = path;
        this.distance = distance;
    }

    public Path() {
    }

    public Path(List<Long> path, long start, double distance) {
        this.path = path;
        this.path.add(start);
        this.distance = distance;
    }

    public Path(List<Long> path, long start, long goal, double distance) {
        this.path = path;
        this.path.add(start);
        this.path.add(goal);
        this.distance = distance;
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }


    public long getLast() {
        return path.get(path.size() - 1);
    }

    public void add(long cityCode, double distance) {
        this.path.add(cityCode);
        this.distance += distance;
    }

    public long getFirst() {
        if(path.isEmpty())
            return -1;
        return path.get(0);
    }

    public long removeFirst() {
        if(path.isEmpty())
            return -1;
        return path.remove(0);
    }

    public static class Builder {
        private Path newPath;

        public Builder() {
            this.newPath = new Path();
        }

        public Builder withPath(List<Long> path) {
            newPath.path = path;
            return this;
        }
        public Builder add(long code) {
            newPath.path.add(code);
            return this;
        }

        public Builder withDistance(double distance) {
            newPath.distance = distance;
            return this;
        }

        public Path build() {
            return newPath;
        }
    }
}
