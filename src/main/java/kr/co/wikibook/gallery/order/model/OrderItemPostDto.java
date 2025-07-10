package kr.co.wikibook.gallery.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderItemPostDto {
    private int order_id;
    private List<Integer> itemIds;
}
