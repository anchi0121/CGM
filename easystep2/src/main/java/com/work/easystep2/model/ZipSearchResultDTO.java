package com.work.easystep2.model;

import lombok.Getter;

@Getter
public class ZipSearchResultDTO extends SearchResult<Zip> {
    private String zipno;
    private String zipcity;
    private String zipdist;

    public ZipSearchResultDTO(Zip data) {
        super(data);
        this.zipno = data.getZipno();
        this.zipcity = data.getCity();
        this.zipdist = data.getDist();
    }

    @Override
    public String toString() {
        return "[" + zipno + ", " + zipcity + ", " + zipdist + "]";
    }
}