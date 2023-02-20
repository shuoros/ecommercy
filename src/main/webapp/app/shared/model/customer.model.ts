import { IUser } from '@/shared/model/user.model';
import { IBasket } from '@/shared/model/basket.model';
import { IComment } from '@/shared/model/comment.model';
import { IAddress } from '@/shared/model/address.model';
import { ICoupon } from '@/shared/model/coupon.model';

export interface ICustomer {
  id?: number;
  score?: number | null;
  user?: IUser | null;
  baskets?: IBasket[] | null;
  comments?: IComment[] | null;
  addresses?: IAddress[] | null;
  coupons?: ICoupon[] | null;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public score?: number | null,
    public user?: IUser | null,
    public baskets?: IBasket[] | null,
    public comments?: IComment[] | null,
    public addresses?: IAddress[] | null,
    public coupons?: ICoupon[] | null
  ) {}
}
