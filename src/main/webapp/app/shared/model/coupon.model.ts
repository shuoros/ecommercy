import { ICustomer } from '@/shared/model/customer.model';
import { IOrder } from '@/shared/model/order.model';

import { CouponType } from '@/shared/model/enumerations/coupon-type.model';
export interface ICoupon {
  id?: number;
  code?: string | null;
  type?: CouponType | null;
  expiration?: Date | null;
  customers?: ICustomer[] | null;
  orders?: IOrder[] | null;
}

export class Coupon implements ICoupon {
  constructor(
    public id?: number,
    public code?: string | null,
    public type?: CouponType | null,
    public expiration?: Date | null,
    public customers?: ICustomer[] | null,
    public orders?: IOrder[] | null
  ) {}
}
