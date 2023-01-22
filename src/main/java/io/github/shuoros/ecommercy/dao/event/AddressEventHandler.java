package io.github.shuoros.ecommercy.dao.event;

import io.github.shuoros.ecommercy.dao.Address;
import io.github.shuoros.ecommercy.exception.PayloadException;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RepositoryEventHandler(Address.class)
@Component
public class AddressEventHandler {

    @HandleBeforeCreate
    public void handleBeforeCreate(final Address address) {
        if (address.getAddressLine1() == null || address.getBuilding() == null || address.getZipCode() == null ||//
                address.getHouseNumber() == null || address.getCity() == null || address.getState() == null ||//
                address.getCountry() == null) {
            throw new PayloadException("Missing address line1, building, zip code, house number, city, state or country!"//
                    , HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
