import { IProduct } from '@/shared/model/product.model';
import { IGroup } from '@/shared/model/group.model';

export interface ICategory {
  id?: number;
  name?: string | null;
  products?: IProduct[] | null;
  group?: IGroup | null;
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string | null, public products?: IProduct[] | null, public group?: IGroup | null) {}
}
