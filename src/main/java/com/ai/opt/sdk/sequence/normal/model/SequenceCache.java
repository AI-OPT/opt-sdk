package com.ai.opt.sdk.sequence.normal.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SequenceCache {
    private final Lock lock = new ReentrantLock();

    private long start = 0;

    private long end = 0;

    private long current = 0;

    private boolean over = false;

    public SequenceCache(long start, long end) {
        this.start = start;
        this.end = end;
        current = this.start - 1;
    }

    public long nextValue() {
        try {
            lock.lock();
            current++;
            long nextValue = current;
            if (nextValue > end) {
                over = true;
                nextValue = -1;
            }
            return nextValue;
        } finally {
            lock.unlock();
        }

    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public boolean isOver() {
        return over;
    }

    public long getCurrent() {
        return current;
    }

}
