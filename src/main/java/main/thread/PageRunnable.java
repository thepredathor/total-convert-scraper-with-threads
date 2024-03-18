package main.thread;

import main.Main;
import main.file.WriteGameInfoToFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageRunnable implements Runnable {
    private int pageNo;
    private WriteGameInfoToFile writeGameInfoToFile;

    public PageRunnable(int pageNo, WriteGameInfoToFile writeGameInfoToFile) {
        this.pageNo = pageNo;
        this.writeGameInfoToFile = writeGameInfoToFile;
    }

    @Override
    public void run() {
        List<Thread> threadPool = new ArrayList<>();

        String TOTAL_CONVERT_JOC_PAGINA_URL = Main.TOTAL_CONVERT_JOC_URL + "page=" + pageNo;
        try {
            Document document = Jsoup.connect(TOTAL_CONVERT_JOC_PAGINA_URL).get();

            Elements gamesDivs = document.getElementsByClass(Main.GAME_WRAPPER_CLASS);
            for(Element gameDiv : gamesDivs) {
                Thread t = new Thread(new GameInfoRunnable(gameDiv, writeGameInfoToFile));
                threadPool.add(t);
                t.start();
            }

            for(Thread t : threadPool) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Thread: " + Thread.currentThread() + " was interrupted", e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("");
    }
}
