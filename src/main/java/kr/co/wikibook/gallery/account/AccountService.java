package kr.co.wikibook.gallery.account;

import kr.co.wikibook.gallery.account.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountMapper accountMapper;

    public int join (AccountJoinReq req) {
        String hashedPw = BCrypt.hashpw(req.getLoginPw(), BCrypt.gensalt());
        AccountJoinReq changeReq = new AccountJoinReq(req.getName(), req.getLoginId(), hashedPw);
        return accountMapper.save(changeReq);
    }

    public AccountLoginRes login(AccountLoginReq req) {
        AccountLoginRes res = accountMapper.findByLoginId(req);

        // 비밀번호 체크
        if(!BCrypt.checkpw(req.getLoginPw(), res.getLoginPw())) {
            return null;    // 비밀번호가 다르면 null 처리
        }

        // 세션 처리


        return res;
    }

}
