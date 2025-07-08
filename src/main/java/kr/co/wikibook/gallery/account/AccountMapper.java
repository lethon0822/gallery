package kr.co.wikibook.gallery.account;

import kr.co.wikibook.gallery.account.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    int save (AccountJoinReq req);
    AccountLoginRes findByLoginId(AccountLoginReq req);
}
