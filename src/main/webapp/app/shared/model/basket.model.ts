import { IOrder } from '@/shared/model/order.model';
import { IItem } from '@/shared/model/item.model';
import { ICustomer } from '@/shared/model/customer.model';

export interface IBasket {
  id?: number;
  score?: number | null;
  price?: number | null;
  order?: IOrder | null;
  items?: IItem[] | null;
  customer?: ICustomer | null;
}

export class Basket implements IBasket {
  constructor(
    public id?: number,
    public score?: number | null,
    public price?: number | null,
    public order?: IOrder | null,
    public items?: IItem[] | null,
    public customer?: ICustomer | null
  ) {}
}
