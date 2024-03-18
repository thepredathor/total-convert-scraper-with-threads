package main;

import main.file.WriteGameInfoToFile;
import main.thread.PageRunnable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String TOTAL_CONVERT_JOC_URL = "https://www.totalconvert.ro/joc?";
    public static final String PAGE_BUTTON_CLASS = "btn btn-default";
    public static final String GAME_WRAPPER_CLASS = "caption";
    public static final String PRICE_GAME_CLASS = "price";
    public static final String TITLE_CLASS = "title";



    public static void main(String[] args) {
        List<Thread> threadPool = new ArrayList<>();
        Document doc;
        int numberOfPages = 0;

        try {
            doc = Jsoup.connect(TOTAL_CONVERT_JOC_URL).get();
            Elements pageButtonsAElements = doc.getElementsByClass(PAGE_BUTTON_CLASS);
            numberOfPages = Integer.parseInt(pageButtonsAElements.get(pageButtonsAElements.size() - 2).text());

            WriteGameInfoToFile writeGameInfoToFile = new WriteGameInfoToFile();

            for(int i = 1; i <= numberOfPages; i++) {
                int pageNo = i;
                Thread t = new Thread(new PageRunnable(pageNo, writeGameInfoToFile));
                threadPool.add(t);

                t.start();
            }

            for(Thread t : threadPool) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException("main.Main thread got interrupted");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}
