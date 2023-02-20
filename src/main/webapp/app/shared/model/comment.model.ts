import { IProduct } from '@/shared/model/product.model';
import { ICustomer } from '@/shared/model/customer.model';

export interface IComment {
  id?: number;
  text?: string | null;
  product?: IProduct | null;
  customer?: ICustomer | null;
}

export class Comment implements IComment {
  constructor(public id?: number, public text?: string | null, public product?: IProduct | null, public customer?: ICustomer | null) {}
}
