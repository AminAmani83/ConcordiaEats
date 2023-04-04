package ca.concordia.eats.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SearchHistory {
    private int searchHistoryId;
    private String phrase;
    private Timestamp timeStamp;

    public int getSearchHistoryId() {
        return searchHistoryId;
    }

    public void setSearchHistoryId(int searchHistoryId) {
        this.searchHistoryId = searchHistoryId;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

}
