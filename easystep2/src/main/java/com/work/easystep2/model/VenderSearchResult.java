package com.work.easystep2.model;

import lombok.Getter;

@Getter
public class VenderSearchResult extends SearchResult<Vender> {
    private String acct;
    private String email;
    private String coname;

    public VenderSearchResult(Vender data) {
        super(data);
        this.acct = data.getAcct();
        this.email = data.getEmail();
        this.coname = data.getConame();
    }

    @Override
    public String toString() {
        return super.toString() + ", [acct=" + acct + ", email=" + email + ", coname=" + coname + "]";
    }
}