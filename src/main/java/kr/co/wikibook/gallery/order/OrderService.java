package kr.co.wikibook.gallery.order;

import kr.co.wikibook.gallery.cart.CartMapper;
import kr.co.wikibook.gallery.item.ItemMapper;
import kr.co.wikibook.gallery.item.model.ItemGetRes;
import kr.co.wikibook.gallery.order.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ItemMapper itemMapper;
    private final CartMapper cartMapper;

    @Transactional
    public int saveOrder (OrderPostReq req, int logginedId){
        List<ItemGetRes> itemList = itemMapper.findAllByIdIn(req.getItemIds());

        long amount = 0;
        for(ItemGetRes i : itemList) {
            amount += i.getPrice() - (i.getPrice() * i.getDiscountPer()) / 100;
        }
        log.info("sum: {}", amount);


        OrderPostDto orderPostDto = new OrderPostDto();

        orderPostDto.setMemberId(logginedId);
        orderPostDto.setName(req.getName());
        orderPostDto.setAddress(req.getAddress());
        orderPostDto.setPayment(req.getPayment());
        orderPostDto.setCardNumber(req.getCardNumber());
        orderPostDto.setAmount(amount);

        // log.info("before : {}", orderPostDto);
        orderMapper.save(orderPostDto);

        OrderItemPostDto dto = new OrderItemPostDto(orderPostDto.getOrderId(), req.getItemIds());
        // log.info("after : {}", dto.getOrderId());

        orderItemMapper.save(dto);

        cartMapper.deleteByMemberId(logginedId);
        return 1;
    }

    public List<OrderGetRes> findAllOrder (int memberId){
        return orderMapper.findAllByMemberIdOrderByIdDesc(memberId);
    }

    public OrderDetailGetRes findDetail(OrderDetailGetReq req) {
        OrderDetailGetRes result = orderMapper.findByOrderIdAndMemberId(req);
        List<OrderDetailDto> items = orderItemMapper.findAllByOrderId(req.getOrderId());
        result.setItems(items);
        log.info("result={}", result);
        return result;
    }

}
