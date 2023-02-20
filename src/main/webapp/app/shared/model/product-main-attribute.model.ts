import { IAttribute } from '@/shared/model/attribute.model';
import { IProduct } from '@/shared/model/product.model';

export interface IProductMainAttribute {
  id?: number;
  price?: number | null;
  discount?: number | null;
  attribute?: IAttribute | null;
  product?: IProduct | null;
}

export class ProductMainAttribute implements IProductMainAttribute {
  constructor(
    public id?: number,
    public price?: number | null,
    public discount?: number | null,
    public attribute?: IAttribute | null,
    public product?: IProduct | null
  ) {}
}
