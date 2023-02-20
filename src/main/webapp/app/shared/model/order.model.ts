import { IAddress } from '@/shared/model/address.model';
import { ICoupon } from '@/shared/model/coupon.model';
import { IBasket } from '@/shared/model/basket.model';

import { OrderStatus } from '@/shared/model/enumerations/order-status.model';
export interface IOrder {
  id?: number;
  status?: OrderStatus | null;
  coupon?: string | null;
  receive?: Date | null;
  address?: IAddress | null;
  coupon?: ICoupon | null;
  basket?: IBasket | null;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public status?: OrderStatus | null,
    public coupon?: string | null,
    public receive?: Date | null,
    public address?: IAddress | null,
    public coupon?: ICoupon | null,
    public basket?: IBasket | null
  ) {}
}
