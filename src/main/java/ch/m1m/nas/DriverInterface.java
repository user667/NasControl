package ch.m1m.nas;

public interface DriverInterface {

    public enum NasStatus {SUCCESS, ERROR, UNKNOWN}

    public NasStatus getStatus();

    public void shutdown();
}
