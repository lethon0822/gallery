package kr.co.wikibook.gallery.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class AccountLoginReq {
    private int id;
    @JsonIgnore
    private String loginPw;
}
