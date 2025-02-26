package com.example.solvehanoi;
import java.util.Queue;


public class HanoiGame {
    private Peg[] pegs;


    public HanoiGame(Peg[] pegs) {
        this.pegs = pegs;
    }

    HelloController cntr = new HelloController();

    public boolean moveDisk(int from, int to) {
        Peg source = pegs[from];
        Peg destination = pegs[to];

        if (source.isEmpty()) return false;
        if (!destination.isEmpty() && source.peekDisk().getSize() > destination.peekDisk().getSize())
            return false;

        Disk disk = source.popDisk();
        destination.pushDisk(disk);

        return true;
    }

    public void solve(int n, int from, int to, int aux, Queue<int[]> moveQueue) {
        if (n == 1) {
            moveQueue.add(new int[]{from, to});
            return;
        }
        solve(n - 1, from, aux, to, moveQueue);
        moveQueue.add(new int[]{from, to});
        solve(n - 1, aux, to, from, moveQueue);
    }
}

