package main.thread;

import main.Main;
import main.file.WriteGameInfoToFile;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GameInfoRunnable implements Runnable {
    private Element gameDiv;
    private WriteGameInfoToFile writeGameInfoToFile;


    public GameInfoRunnable(Element gameDiv, WriteGameInfoToFile writeGameInfoToFile) {
        this.gameDiv = gameDiv;
        this.writeGameInfoToFile = writeGameInfoToFile;
    }

    @Override
    public void run() {
        Elements priceElements = gameDiv.getElementsByClass(Main.PRICE_GAME_CLASS);
        String gamePrice = priceElements.get(0).text();

        Elements titleElements = gameDiv.getElementsByClass(Main.TITLE_CLASS);
        String gameTitle = titleElements.get(0).text();
        String console = gameTitle.split(" ")[0];

        String fileContent = "Console: " + console + ", title: " + gameTitle + ", price: " + gamePrice + "\n";

        writeGameInfoToFile.writeToFile(fileContent);

        System.out.println("Thread: " + Thread.currentThread() + " finished writing to file!");
    }
}
