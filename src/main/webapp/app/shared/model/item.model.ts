import { IProduct } from '@/shared/model/product.model';
import { IBasket } from '@/shared/model/basket.model';

export interface IItem {
  id?: number;
  quantity?: number | null;
  price?: number | null;
  product?: IProduct | null;
  basket?: IBasket | null;
}

export class Item implements IItem {
  constructor(
    public id?: number,
    public quantity?: number | null,
    public price?: number | null,
    public product?: IProduct | null,
    public basket?: IBasket | null
  ) {}
}
