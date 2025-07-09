package kr.co.wikibook.gallery.cart;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.wikibook.gallery.account.etc.AccountConstants;
import kr.co.wikibook.gallery.cart.model.*;
import kr.co.wikibook.gallery.common.util.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> save(HttpServletRequest httpReq, @RequestBody CartPostReq req) {
        int loggedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        req.setMemberId(loggedMemberId);
        int result = cartService.save(req);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<?> findAll (HttpServletRequest httpReq) {
        int loggedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        List<CartGetRes> result = cartService.findAll(loggedMemberId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<?> remove (HttpServletRequest httpReq, @RequestParam int itemId) {
        int loggedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        CartDeleteReq req = new CartDeleteReq(loggedMemberId, itemId);
        int result = cartService.remove(req);
        return ResponseEntity.ok(result);
    }
}
