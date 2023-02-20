import { IProductMainAttribute } from '@/shared/model/product-main-attribute.model';
import { IProduct } from '@/shared/model/product.model';

export interface IAttribute {
  id?: number;
  key?: string | null;
  value?: string | null;
  productMainAttribute?: IProductMainAttribute | null;
  products?: IProduct[] | null;
}

export class Attribute implements IAttribute {
  constructor(
    public id?: number,
    public key?: string | null,
    public value?: string | null,
    public productMainAttribute?: IProductMainAttribute | null,
    public products?: IProduct[] | null
  ) {}
}
