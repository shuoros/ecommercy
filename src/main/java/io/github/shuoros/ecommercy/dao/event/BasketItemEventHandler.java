package io.github.shuoros.ecommercy.dao.event;

import io.github.shuoros.ecommercy.dao.Basket;
import io.github.shuoros.ecommercy.dao.BasketItem;
import io.github.shuoros.ecommercy.dao.repository.BasketItemRepository;
import io.github.shuoros.ecommercy.dao.repository.BasketRepository;
import io.github.shuoros.ecommercy.exception.PayloadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RepositoryEventHandler(BasketItem.class)
@Component
public class BasketItemEventHandler {

    private final BasketItemRepository basketItemRepository;
    private final BasketRepository basketRepository;

    @Autowired
    public BasketItemEventHandler(BasketItemRepository basketItemRepository, BasketRepository basketRepository) {
        this.basketItemRepository = basketItemRepository;
        this.basketRepository = basketRepository;
    }

    @HandleBeforeCreate
    public void handleBeforeCreate(final BasketItem basketItem) {
        if (basketItem.getBasket() == null || basketItem.getProduct() == null || basketItem.getQuantity() == null)
            throw new PayloadException("Missing basket, product or quantity!", HttpStatus.UNPROCESSABLE_ENTITY);
        if (basketItemRepository.existsByBasketAndProduct(basketItem.getBasket(), basketItem.getProduct()))
            throw new PayloadException("Product already exists in basket! Try update the quantity", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @HandleAfterCreate
    public void handleAfterCreate(final BasketItem basketItem) {
        updateTotalPrice(basketItem);
    }

    @HandleAfterSave
    public void handleBeforeSave(final BasketItem basketItem) {
        updateTotalPrice(basketItem);
    }

    @HandleAfterDelete
    public void handleAfterDelete(final BasketItem basketItem) {
        updateTotalPrice(basketItem);
    }

    private void updateTotalPrice(BasketItem basketItem) {
        Basket basket = basketRepository.getById(basketItem.getBasket().getId());
        float totalPrice = 0.0F;
        float totalDiscount = 0.0F;
        for (BasketItem item : basket.getItems()) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
            totalDiscount += item.getProduct().getDiscount() * item.getQuantity();
        }
        basket.setTotalPrice(totalPrice);
        basket.setDiscount(totalDiscount);
        basketRepository.save(basketItem.getBasket());
    }

}
